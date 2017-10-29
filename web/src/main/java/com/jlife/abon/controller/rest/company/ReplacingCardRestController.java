package com.jlife.abon.controller.rest.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.CardData;
import com.jlife.abon.dto.ReplacingCardData;
import com.jlife.abon.facade.CardFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company/")
public class ReplacingCardRestController extends BaseController {

    @Autowired
    private CardFacade cardFacade;

    @PostMapping(value = "/replace-card", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CardData replaceCard(@RequestBody ReplacingCardData cardInfo) {
        String userId = cardInfo.getUserId();
        Long newCardUUID = cardInfo.getNewCardUUID();
        String companyId = getSessionCompanyId();
        return cardFacade.replaceWithNewCardAsCompany(userId, newCardUUID, companyId);
    }

}
