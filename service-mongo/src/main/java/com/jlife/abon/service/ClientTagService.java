package com.jlife.abon.service;

import com.jlife.abon.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface ClientTagService {
    List<String> findAllClientTags(String companyId);

    Page<Client> findClientsByTag(String tag, String companyId, Pageable pageRequest);
}
