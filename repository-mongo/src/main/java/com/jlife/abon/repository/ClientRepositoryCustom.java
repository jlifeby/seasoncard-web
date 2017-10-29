package com.jlife.abon.repository;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface ClientRepositoryCustom {

    List<String> findAllClientTags(String companyId);
}
