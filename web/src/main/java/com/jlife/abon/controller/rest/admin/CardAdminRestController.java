package com.jlife.abon.controller.rest.admin;

import com.jlife.abon.dto.CardData;
import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.BadRequestException;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.facade.CardFacade;
import com.jlife.abon.facade.CompanyFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Controller
@RequestMapping(value = {"/api/admin"})
public class CardAdminRestController {

    @Autowired
    private CardFacade cardFacade;

    @Autowired
    private CompanyFacade companyFacade;

    @RequestMapping(value = "/cards/add-to-pool", method = RequestMethod.POST)
    @ResponseBody
    public CardData addToPool(@RequestBody CardData card) {
        // todo move to systemuserfacade
        Long cardUUID = card.getCardUUID();
        String cardNfcUUID = card.getNfcUUID().toUpperCase();
        CardData existedCard = cardFacade.findOneByCardUUID(cardUUID);
        if (cardUUID == null || cardNfcUUID == null) {
            throw new BadRequestException(ApiErrorCode.GENERAL_CODE);
        }
        if (existedCard != null) {
            throw new NotAllowedException(ApiErrorCode.CARD_IS_ALREADY_IN_POOL_WITH_CARD_UUID,
                    String.valueOf(cardUUID));
        }
        existedCard = cardFacade.findOneByNfcUUID(cardNfcUUID);
        if (existedCard != null) {
            throw new NotAllowedException(ApiErrorCode.CARD_IS_ALREADY_IN_POOL_WITH_NFC_UUID,
                    cardNfcUUID);
        }
        CardData newCard = new CardData();
        newCard.setCardUUID(cardUUID);
        newCard.setNfcUUID(cardNfcUUID);
        newCard.setFree(true);
        newCard.setDeliveredToCompany(null);
        return cardFacade.save(newCard);
    }

    @RequestMapping(value = {"/cards/deliver-to-company"}, method = RequestMethod.POST)
    @ResponseBody
    public CardData deliverToCompany(@RequestParam("cardUUID") Long cardUUID,
                                     @RequestParam("companyId") String companyId) {
        // todo move to system facade
        CardData existedCard = cardFacade.findOneByCardUUID(cardUUID);
        if (existedCard == null) {
            throw new ResourceNotFoundException(ApiErrorCode.CARD_NOT_FOUND_WITH_CARD_UUID, String.valueOf(cardUUID));
        }
        if (!existedCard.isFree()) {
            throw new NotAllowedException(ApiErrorCode.CARD_IS_ALREADY_BUSY_WITH_CARD_UUID,
                    String.valueOf(cardUUID));
        }
        if (existedCard.getDeliveredToCompany() != null) {
            throw new NotAllowedException(ApiErrorCode.CARD_IS_ALREADY_DELIVERED_TO_COMPANY,
                    existedCard.getDeliveredToCompany());
        }
        CompanyData company = companyFacade.getCompany(companyId);
        if (company == null) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_DOES_NOT_EXIST_WITH_ID,
                    companyId);
        }
        existedCard.setDeliveredToCompany(companyId);
        return cardFacade.save(existedCard);
    }

    @RequestMapping(value = "/cards", method = RequestMethod.GET)
    @ResponseBody
    public List<CardData> getAllCards() {
        return cardFacade.getAllCards();
    }

    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    @ResponseBody
    public List<CompanyData> getAllCompanies() {
        return companyFacade.getAllCompanies();
    }
}
