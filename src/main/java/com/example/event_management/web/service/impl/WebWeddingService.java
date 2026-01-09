package com.example.event_management.web.service.impl;

import com.example.event_management.common.helpers.UrlHelper;
import com.example.event_management.repository.IWeddingMasterRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebWeddingService {
    @Autowired
    IWeddingMasterRepo iWeddingMasterRepo;

    @Autowired
    UrlHelper urlHelper;


    public String getWeddingCard(String id)
    {

        if(!iWeddingMasterRepo.existsByWeddingId(id))
        {
            throw new EntityNotFoundException("Wedding Not found");
        }

        String card = iWeddingMasterRepo.getWeddingCard(id);

        return (card != null && !card.isEmpty())
                ? urlHelper.getBaseUrlWithForwardSlash() + card
                : null;

    }
}
