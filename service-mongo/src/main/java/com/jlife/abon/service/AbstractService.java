package com.jlife.abon.service;

import com.jlife.abon.mapper.DataMapper;
import com.jlife.abon.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author Dzmitry Misiuk
 */

abstract public class AbstractService {


    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected final String DEFAULT_SMS_SERVICE = "unionSmsService";

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected AbonnementRepository abonnementRepository;

    @Autowired
    protected CardRepository cardRepository;

    @Autowired
    protected CompanyRepository companyRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ClientRepository clientRepository;

    @Autowired
    protected SingleAttendanceRepository singleAttendanceRepository;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected AbonnementService abonnementService;

    @Autowired
    protected PromotionService promotionService;

    @Autowired
    protected CardService cardService;

    @Autowired
    protected DataMapper dataMapper;

    @Autowired
    protected AuditorAware<String> auditor;

}
