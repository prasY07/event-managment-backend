package com.example.event_management.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkGuestUploadResponse {
    private int totalUploaded;
    private int totalSkipped;
    private List<String> invalidRows;

}