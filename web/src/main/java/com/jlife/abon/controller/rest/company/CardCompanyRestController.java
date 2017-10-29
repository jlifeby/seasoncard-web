package com.jlife.abon.controller.rest.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.CardData;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.facade.CardFacade;
import com.jlife.abon.facade.EnrollFacade;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@Controller
@RequestMapping("/api/company")
@RestController
public class CardCompanyRestController extends BaseController {

    @Autowired
    private CardFacade cardFacade;

    @Autowired
    private EnrollFacade enrollFacade;

    @RequestMapping(value = "/card/enrollProduct", method = RequestMethod.POST)
    public AbonnementData enrollProduct(@RequestParam("cardUUID") Long cardUUID,
                                        @RequestParam("productId") String productId) {
        return cardFacade.enrollProduct(cardUUID, productId, getSessionCompanyId());
    }

    @RequestMapping(value = {"/card/enroll-product", "/cards/enroll-product"}, method = RequestMethod.POST)
    public AbonnementData enrollProduct(@RequestParam("cardUUID") Long cardUUID,
                                        @RequestParam("productId") String productId,
                                        @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") DateTime startDate,
                                        @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") DateTime endDate,
                                        @RequestParam(value = "price", required = false) Double price,
                                        @RequestParam(value = "attendanceCount", required = false) Integer attendanceCount,
                                        @RequestParam(value = "unitCount", required = false) Integer unitCount,
                                        @RequestParam(value = "comment", required = false, defaultValue = "") String comment,
                                        @RequestParam(value = "promotionId", required = false, defaultValue = "") String promotionId) {
        // todo Check validation
        if (StringUtils.isNotEmpty(promotionId)) {
            return cardFacade.enrollProduct(cardUUID, productId, getSessionCompanyId(), startDate, endDate, attendanceCount, unitCount, promotionId, comment);
        }
        if (price == null && attendanceCount == null) {
            return cardFacade.enrollProduct(cardUUID, productId, getSessionCompanyId(), startDate, endDate, comment);
        } else if (price != null && (attendanceCount != null || unitCount != null)) {
            return cardFacade.enrollProduct(cardUUID, productId, getSessionCompanyId(), startDate, endDate, attendanceCount, unitCount, price, comment);
        }
        throw new NotAllowedException(ApiErrorCode.GENERAL_CODE, "This request is not allowed: setup price and count of attendance or unit simultaneously");

    }

    @PostMapping(value = "/cards/enroll-product", consumes = "application/json")
    public AbonnementData enrollAbonnement(@RequestBody AbonnementData abonnement) {
        return enrollFacade.enrollAbonnement(abonnement.getCardUUID(), getSessionCompanyId(), abonnement);
    }

    @RequestMapping(value = {"/card/{cardUUID}", "/cards/{cardUUID}"}, method = RequestMethod.GET)
    public CardData getClientCard(@PathVariable Long cardUUID) {
        String companyId = getSessionCompanyId();
        return cardFacade.getClientCard(companyId, cardUUID);
    }

    @RequestMapping(value = "/clientcards", method = RequestMethod.GET)
    public List<CardData> getClientCards() {
        String companyId = getSessionCompanyId();
        return cardFacade.getClientCards(companyId);
    }


    @RequestMapping(value = {"/card/bynfc/{nfcUUID}", "/cards/by-nfc/{nfcUUID}"}, method = RequestMethod.GET)
    public CardData getCardByNFCUUID(@PathVariable String nfcUUID) {
        CardData card = cardFacade.findByNfcUUID(nfcUUID);
        return card;
    }


    @RequestMapping(value = "/cards/next-free-virtual", method = RequestMethod.GET)
    public CardData getNexFreeVirtualCard() {
        return cardFacade.getNextFreeVirtualCard(getSessionCompanyId());
    }

}