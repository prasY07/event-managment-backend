package com.example.event_management.web.service.impl;

import com.example.event_management.common.helpers.UrlHelper;
import com.example.event_management.entity.WeddingMaster;
import com.example.event_management.repository.IWeddingMasterRepo;
import com.example.event_management.web.dto.response.WeddingShortResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WebWeddingService {
    @Autowired
    IWeddingMasterRepo iWeddingMasterRepo;

    @Autowired
    UrlHelper urlHelper;


    public WeddingShortResponse getWeddingCard(String id)
    {
        WeddingMaster wed = iWeddingMasterRepo.getSingleWedding(id);

        if(wed == null)
        {
            throw new EntityNotFoundException("Wedding Not found");
        }

        String cUrl = (wed.getWeddingCard() != null && !wed.getWeddingCard().isEmpty())
                ? urlHelper.getBaseUrlWithForwardSlash() + wed.getWeddingCard()
                : null;


        WeddingShortResponse res =   new WeddingShortResponse();
        res.setCardUrl(cUrl);
        res.setStartDate(wed.getRegistrationStartDate());
        res.setEndDate(wed.getRegistrationEndDate());

        return res;

    }
}
