package com.jlife.abon.facade;

import com.jlife.abon.criteria.SearchCriteria;
import com.jlife.abon.dto.ClientData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface ClientFacade {


    List<String> findAllClientTags(String companyId);

    /**
     * @param companyId
     * @return
     * @deprecated please use {@link #findClients(List, String, Pageable)}
     */
    Page<ClientData> findClientsByTag(String tag, String companyId, Pageable pageRequest);

    /**
     * @param criterias
     * @param companyId
     * @param pageable
     * @return
     */
    Page<ClientData> findClients(List<SearchCriteria> criterias, String companyId, Pageable pageable);

    Page<ClientData> findAllClients(Pageable pageable);

}
