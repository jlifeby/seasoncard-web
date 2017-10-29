package com.jlife.abon.facade;

import com.jlife.abon.dto.CardData;
import com.jlife.abon.dto.ClientData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class AddingCommentForClientsTest extends ServiceBaseTestCase {

    private static final String PARENT_COMMENT = "This client needs to be contacted via parents";

    @Autowired
    CompanyFacade companyFacade;

    @Test
    public void test_commentIsAdded_whenClientExists() {
        CardData clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);
        ClientData clientData = clientCard.getClient();
        clientData.setComment(PARENT_COMMENT);
        companyFacade.updateClientByCardUUID(DEFAULT_CARD_UUID, clientData, DEFAULT_COMPANY_ID);

        CardData storedCardData = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);
        ClientData updatedClientData = storedCardData.getClient();
        String comment = updatedClientData.getComment();

        assertThat(comment).isNotNull().isEqualTo(PARENT_COMMENT);
    }

    @Test
    public void test_commentAddingIsSkipped_whenClientCommentUpdateIsNull() {
        CardData clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);
        ClientData clientData = clientCard.getClient();
        clientData.setComment(PARENT_COMMENT);
        companyFacade.updateClientByCardUUID(DEFAULT_CARD_UUID, clientData, DEFAULT_COMPANY_ID);

        clientData.setComment(null);
        companyFacade.updateClientByCardUUID(DEFAULT_CARD_UUID, clientData, DEFAULT_COMPANY_ID);

        CardData storedCardData = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);
        ClientData updatedClientData = storedCardData.getClient();
        String comment = updatedClientData.getComment();

        assertThat(comment).isNotNull().isEqualTo(PARENT_COMMENT);
    }
}
