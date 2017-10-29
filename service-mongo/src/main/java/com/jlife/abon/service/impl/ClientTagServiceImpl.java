package com.jlife.abon.service.impl;

import com.jlife.abon.entity.Client;
import com.jlife.abon.repository.ClientRepository;
import com.jlife.abon.service.ClientTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
@Service
public class ClientTagServiceImpl implements ClientTagService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<String> findAllClientTags(String companyId) {
        return clientRepository.findAllClientTags(companyId);
    }

    @Override
    public Page<Client> findClientsByTag(String tag, String companyId, Pageable pageRequest) {
        return clientRepository.findByTagsAndCompanyId(tag, companyId, pageRequest);
    }
}
