package com.example.event_management.web.service.impl;

import com.example.event_management.admin.dto.GuestRequest;
import com.example.event_management.common.AppStatus;
import com.example.event_management.entity.*;
import com.example.event_management.repository.IWeddingMasterRepo;
import com.example.event_management.repository.IWeddingSideMasterRepo;
import com.example.event_management.util.FileStorageUtil;
import com.example.event_management.web.dto.response.CoPassengerResponse;
import com.example.event_management.web.dto.response.GuestListResponse;
import com.example.event_management.web.dto.response.JourneyResponse;
import com.example.event_management.web.repo.IWeddingGuestRepository;
import com.example.event_management.web.repo.IWeddingGuestUploadRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WeddingRegistrationService {

    private final IWeddingMasterRepo weddingMasterRepo;
    private final IWeddingGuestUploadRepo weddingGuestUploadRepo;
    private final IWeddingSideMasterRepo sideMasterRepo;
    private final IWeddingGuestRepository guestRepository;
    private final ObjectMapper objectMapper;
    private static final DataFormatter DATA_FORMATTER = new DataFormatter();


    public int bulkUploadGuests(Long weddingId, MultipartFile file) {

        WeddingMaster wedding = weddingMasterRepo
                .findById(weddingId)
                .orElseThrow(() -> new RuntimeException("Wedding not found"));

        List<WeddingGuestUpload> registrations = new ArrayList<>();
        List<String> invalidRows = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            if (!rows.hasNext()) {
                throw new RuntimeException("Excel file is empty");
            }

            Row headerRow = rows.next(); // skip header

            int rowCount = sheet.getPhysicalNumberOfRows() - 1;
            if (rowCount > 500) {
                throw new RuntimeException("Maximum 500 guests allowed");
            }

            while (rows.hasNext()) {
                Row row = rows.next();

                String guestName = getCellValue(row.getCell(0));
                String mobileNumber = getCellValue(row.getCell(1));
                String sideName = getCellValue(row.getCell(2));
                String countryCode = getCellValue(row.getCell(3));

                // Skip fully blank rows
                if (guestName.isBlank() && mobileNumber.isBlank()
                        && sideName.isBlank() && countryCode.isBlank()) {
                    continue;
                }

                // Mandatory validation
                if (guestName.isBlank() || mobileNumber.isBlank()
                        || sideName.isBlank() || countryCode.isBlank()) {
                    invalidRows.add("Row " + row.getRowNum());
                    continue;
                }

                WeddingSideMaster side = sideMasterRepo
                        .findBySideNameIgnoreCase(sideName)
                        .orElse(null);

                if (side == null) {
                    invalidRows.add("Row " + row.getRowNum() + " - Invalid side");
                    continue;
                }

                if (weddingGuestUploadRepo
                        .existsByWeddingMaster_IdAndMobileNumber(weddingId, mobileNumber)) {
                    continue;
                }

                WeddingGuestUpload registration = WeddingGuestUpload.builder()
                        .weddingMaster(wedding)
                        .side(side)
                        .guestName(guestName)
                        .mobileNumber(mobileNumber)
                        .countryCode(countryCode)
                        .status(AppStatus.RegistrationStatus.REGISTERED)
                        .build();

                registrations.add(registration);
            }

            weddingGuestUploadRepo.saveAll(registrations);

            if (!invalidRows.isEmpty()) {
                System.out.println("Invalid rows: " + invalidRows);
            }

            return registrations.size();

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload Excel file", e);
        }
    }
    @Transactional
    public Long saveGuest(
            String guestData,
            MultipartFile onwardTicketFile,
            MultipartFile returnTicketFile) throws Exception {

        GuestRequest req =
                objectMapper.readValue(guestData, GuestRequest.class);

        validateTicketFile(onwardTicketFile);
        validateTicketFile(returnTicketFile);

        String onwardTicketPath = FileStorageUtil.saveWeddingRegistrationFile(
                onwardTicketFile,
                req.getWeddingId(),
                "ONWARD"
        );

        String returnTicketPath = FileStorageUtil.saveWeddingRegistrationFile(
                returnTicketFile,
                req.getWeddingId(),
                "RETURN"
        );

        WeddingGuest guest = WeddingGuest.builder()
                .weddingId(req.getWeddingId())
                .fullName(req.getFullName())
                .mobileNumber(req.getMobileNumber())
                .email(req.getEmail())
                .gender(req.getGender())

                // ONWARD
                .onwardMode(req.getOnwardJourney().getMode())
                .onwardArrivalDate(req.getOnwardJourney().getArrivalDate())
                .onwardArrivalTime(req.getOnwardJourney().getArrivalTime())
                .onwardDepartureCity(req.getOnwardJourney().getDepartureCity())
                .onwardDestinationCity(req.getOnwardJourney().getDestinationCity())
                .onwardCarrierName(req.getOnwardJourney().getCarrierName())
                .onwardCarrierNumber(req.getOnwardJourney().getCarrierNumber())
                .onwardTicketPath(onwardTicketPath)

                // RETURN
                .returnMode(req.getReturnJourney().getMode())
                .returnDepartureDate(req.getReturnJourney().getDepartureDate())
                .returnDepartureTime(req.getReturnJourney().getDepartureTime())
                .returnDepartureCity(req.getReturnJourney().getDepartureCity())
                .returnDestinationCity(req.getReturnJourney().getDestinationCity())
                .returnCarrierName(req.getReturnJourney().getCarrierName())
                .returnCarrierNumber(req.getReturnJourney().getCarrierNumber())
                .returnTicketPath(returnTicketPath)
                .build();

        // Co-passengers
        if (req.getOnwardJourney().getCoPassengers() != null) {
            List<CoPassenger> cps =
                    req.getOnwardJourney().getCoPassengers()
                            .stream()
                            .map(cp -> CoPassenger.builder()
                                    .name(cp.getName())
                                    .age(cp.getAge())
                                    .relationship(cp.getRelationship())
                                    .guest(guest)
                                    .build())
                            .toList();
            guest.setCoPassengers(cps);
        }

        return guestRepository.save(guest).getId();
    }

    private void validateTicketFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Ticket file is required");
        }

        // Allowed content types
        List<String> allowedTypes = List.of("application/pdf", "image/jpeg", "image/png");

        if (!allowedTypes.contains(file.getContentType())) {
            throw new IllegalArgumentException("Invalid ticket file type. Only PDF, JPEG, PNG allowed.");
        }

        // Max size 5 MB
        long maxSize = 5 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException("File too large. Maximum size is 5MB.");
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        return DATA_FORMATTER.formatCellValue(cell).trim();

    }

    @Transactional(readOnly = true)
    public List<GuestListResponse> listGuestsByWedding(Long weddingId) {

        List<WeddingGuest> guests =
                guestRepository.findByWeddingId(weddingId);

        if (guests.isEmpty()) {
            return Collections.emptyList();
        }

        return guests.stream().map(guest ->

                GuestListResponse.builder()
                        .guestId(guest.getId())
                        .weddingId(guest.getWeddingId())
                        .fullName(guest.getFullName())
                        .mobileNumber(guest.getMobileNumber())
                        .email(guest.getEmail())
                        .gender(guest.getGender())

                        // ONWARD
                        .onwardJourney(
                                JourneyResponse.builder()
                                        .mode(guest.getOnwardMode())
                                        .arrivalDate(toDate(guest.getOnwardArrivalDate()))
                                        .arrivalTime(toTime(guest.getOnwardArrivalTime()))
                                        .departureCity(guest.getOnwardDepartureCity())
                                        .destinationCity(guest.getOnwardDestinationCity())
                                        .carrierName(guest.getOnwardCarrierName())
                                        .carrierNumber(guest.getOnwardCarrierNumber())
                                        .ticketPath(guest.getOnwardTicketPath())
                                        .build()
                        )

                        // RETURN
                        .returnJourney(
                                JourneyResponse.builder()
                                        .mode(guest.getReturnMode())
                                        .departureDate(toDate(guest.getReturnDepartureDate()))
                                        .departureTime(toTime(guest.getReturnDepartureTime()))
                                        .departureCity(guest.getReturnDepartureCity())
                                        .destinationCity(guest.getReturnDestinationCity())
                                        .carrierName(guest.getReturnCarrierName())
                                        .carrierNumber(guest.getReturnCarrierNumber())
                                        .ticketPath(guest.getReturnTicketPath())
                                        .build()
                        )

                        // CO-PASSENGERS
                        .coPassengers(
                                guest.getCoPassengers() == null
                                        ? List.of()
                                        : guest.getCoPassengers().stream()
                                        .map(cp -> CoPassengerResponse.builder()
                                                .name(cp.getName())
                                                .age(cp.getAge())
                                                .relationship(cp.getRelationship())
                                                .build())
                                        .toList()
                        )
                        .build()

        ).toList();
    }

    private String toDate(LocalDate date) {
        return date != null ? date.toString() : null; // yyyy-MM-dd
    }

    private String toTime(LocalTime time) {
        return time != null ? time.toString() : null; // HH:mm
    }
}

