package com.jlife.abon.repository;

import com.jlife.abon.entity.Client;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class ClientRepositoryImpl implements ClientRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<String> findAllClientTags(String companyId) {
        BasicDBObject query = new BasicDBObject();
        query.put("companyId", companyId);
        List tags = mongoTemplate.getCollection(
                mongoTemplate.getCollectionName(Client.class)
        ).distinct("tags", query);
        return tags;
    }
}
