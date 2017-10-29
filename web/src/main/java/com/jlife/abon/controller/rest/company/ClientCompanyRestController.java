package com.jlife.abon.controller.rest.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.criteria.FullTextCriteria;
import com.jlife.abon.criteria.PotentialClientCriteria;
import com.jlife.abon.criteria.SearchCriteria;
import com.jlife.abon.criteria.TagCriteria;
import com.jlife.abon.dto.CardData;
import com.jlife.abon.dto.ClientData;
import com.jlife.abon.enumeration.PotentialClientSearchMode;
import com.jlife.abon.enumeration.TagCriteriaSearchMode;
import com.jlife.abon.facade.CompanyFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@RestController
@RequestMapping(value = "/api/company")
public class ClientCompanyRestController extends BaseController {

    @Autowired
    private CompanyFacade companyFacade;

    @RequestMapping(value = {"/customer/bindCard", "/clients/bind-card"},
            method = RequestMethod.POST, consumes = "application/json")
    public CardData bindCard(@RequestBody CardData card) {
        // todo validate card
        // todo check together card and user
        return companyFacade.bindCard(card, getSessionCompanyId());
    }

    @GetMapping(value = {"/clients"})
    public Page<ClientData> findClients(@RequestParam(name = "text", defaultValue = "") String text,
                                        @RequestParam(name = "tags", defaultValue = "") List<String> tags,
                                        @RequestParam(name = "tagSearchMode", defaultValue = "IGNORE") TagCriteriaSearchMode tagSearchMode,
                                        @RequestParam(name = "page", defaultValue = "0") Integer page,
                                        @RequestParam(name = "size", defaultValue = "20") Integer size,
                                        @RequestParam(value = "potentialMode", required = false, defaultValue = "REAL") PotentialClientSearchMode potentialClientSearchMode) {
        Pageable pageable = new PageRequest(page, size);
        final List<SearchCriteria> criterias = new ArrayList<>();
        criterias.add(new FullTextCriteria(text));
        criterias.add(new TagCriteria(tags, tagSearchMode));
        criterias.add(new PotentialClientCriteria(potentialClientSearchMode));
        return clientFacade.findClients(criterias, getSessionCompanyId(), pageable);
    }

    @PostMapping(value = {"/clients"}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ClientData createClient(@RequestBody ClientData clientData) {
        return companyFacade.createClient(clientData, getSessionCompanyId());
    }

    @RequestMapping(value = {"/customer/{userId}", "/clients/{userId}"},
            method = RequestMethod.PATCH, consumes = "application/json")
    public ClientData updateClient(@RequestBody ClientData clientData, @PathVariable("userId") String userId) {
        return companyFacade.updateClientByUserId(userId, clientData, getSessionCompanyId());
    }

    @RequestMapping(value = {"/clients/update/{cardUUID}"},
            method = RequestMethod.PATCH, consumes = "application/json")
    public ClientData updateClientByUUID(@RequestBody ClientData clientData, @PathVariable("cardUUID") Long cardUUID) {
        return companyFacade.updateClientByCardUUID(cardUUID, clientData, getSessionCompanyId());
    }
}
