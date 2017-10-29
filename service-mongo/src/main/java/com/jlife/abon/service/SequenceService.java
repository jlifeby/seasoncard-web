package com.jlife.abon.service;

import com.jlife.abon.entity.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class SequenceService {

    public static final String CONTRACT_ID_KEY = "contractId";

    @Autowired
    private MongoTemplate mongoTemplate;

    public int getNextContractId() {
        return getNextSequenceId(CONTRACT_ID_KEY);
    }

    private int getNextSequenceId(String key) {
        Query query = new Query(Criteria.where("name").is(key));
        Update update = new Update();
        update.inc("seq", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        Sequence seqId = mongoTemplate.findAndModify(query, update, options, Sequence.class);
        return seqId.getSeq();
    }
}
