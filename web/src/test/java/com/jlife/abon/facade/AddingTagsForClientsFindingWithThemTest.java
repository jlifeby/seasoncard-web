package com.jlife.abon.facade;

import com.jlife.abon.dto.AbstractUserData;
import com.jlife.abon.dto.CardData;
import com.jlife.abon.dto.ClientData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public class AddingTagsForClientsFindingWithThemTest extends ServiceBaseTestCase {

    public static final String KIDS_TAG = "kids";
    public static final String SUPER_TAG = "super";
    public static final String MY_TAG = "my";
    public static final String ANOTHER_TAG = "another";

    // todo find clients with specific tags supporting paging

    @Autowired
    ClientFacade clientFacade;

    @Test
    public void test_tagsAreStored_whenUpdateClientWithTags() {
        CardData clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);
        ClientData clientData = clientCard.getClient();
        final Integer startTagsCount = clientData.getTags().size();
        clientData.addTag(KIDS_TAG);
        companyFacade.updateClientByCardUUID(DEFAULT_CARD_UUID, clientData, DEFAULT_COMPANY_ID);

        CardData storedCardData = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);
        ClientData updatedClientData = storedCardData.getClient();
        List<String> tags = updatedClientData.getTags();

        assertThat(tags).isNotNull().hasSize(startTagsCount + 1).contains(KIDS_TAG);
    }

    @Test
    public void test_tagsAreStored_whenUpdateClientWithTagsUsingUpdateByUserId() {
        CardData clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);
        ClientData clientData = clientCard.getClient();
        final Integer startTagsCount = clientData.getTags().size();
        clientData.addTag(KIDS_TAG);
        companyFacade.updateClientByUserId(DEFAULT_USER_ID, clientData, DEFAULT_COMPANY_ID);

        CardData storedCardData = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);

        ClientData updatedClientData = storedCardData.getClient();
        List<String> tags = updatedClientData.getTags();

        assertThat(tags).isNotNull().hasSize(startTagsCount + 1).contains(KIDS_TAG);
    }

    @Test
    // old api-consumers doesn't now about tags and will send null. This should not clean existed tags
    public void test_tagsNoClear_whenWeSetupTagsEqualsNullOnDataLayer() {
        CardData clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);
        ClientData clientData = clientCard.getClient();
        final Integer startTagsCount = clientData.getTags().size();
        clientData.addTag(KIDS_TAG);
        companyFacade.updateClientByUserId(DEFAULT_USER_ID, clientData, DEFAULT_COMPANY_ID);

        CardData storedCardData = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);

        ClientData updatedClientData = storedCardData.getClient();
        updatedClientData.setTags(null);

        companyFacade.updateClientByCardUUID(DEFAULT_CARD_UUID, updatedClientData, DEFAULT_COMPANY_ID);

        CardData retainedTagsCardData = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);

        ClientData retainedTagsClientData = retainedTagsCardData.getClient();
        List<String> tags = retainedTagsClientData.getTags();
        assertThat(tags).isNotNull().hasSize(startTagsCount + 1).contains(KIDS_TAG);
    }

    @Test
    public void test_tagsIsEmpty_whenWeSetupTagsEqualsToEmptyList() {
        CardData clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);
        ClientData clientData = clientCard.getClient();
        clientData.addTag(KIDS_TAG);
        companyFacade.updateClientByUserId(DEFAULT_USER_ID, clientData, DEFAULT_COMPANY_ID);

        CardData storedCardData = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);

        ClientData updatedClientData = storedCardData.getClient();
        updatedClientData.setTags(Collections.emptyList());

        companyFacade.updateClientByCardUUID(DEFAULT_CARD_UUID, updatedClientData, DEFAULT_COMPANY_ID);

        CardData retainedTagsCardData = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);

        ClientData retainedTagsClientData = retainedTagsCardData.getClient();
        List<String> tags = retainedTagsClientData.getTags();
        assertThat(tags).isNotNull().hasSize(0);
    }

    @Test
    public void test_twoDifferentTags_whenWeHaveTwoUsersWithRepeatedSimilarTags() {
        CardData clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);
        ClientData clientData = clientCard.getClient();
        clientData.addTag(KIDS_TAG);
        clientData.addTag(SUPER_TAG);
        companyFacade.updateClientByCardUUID(DEFAULT_CARD_UUID, clientData, DEFAULT_COMPANY_ID);

        CardData anotherClientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, ANOTHER_CARD_UUID);
        ClientData anotherClientCardClient = anotherClientCard.getClient();
        anotherClientCardClient.addTag(MY_TAG);
        anotherClientCardClient.addTag(SUPER_TAG);
        companyFacade.updateClientByCardUUID(ANOTHER_CARD_UUID, anotherClientCardClient, DEFAULT_COMPANY_ID);

        Collection<String> tags = clientFacade.findAllClientTags(DEFAULT_COMPANY_ID);
        assertThat(tags).isNotNull().containsAll(Arrays.asList(MY_TAG, SUPER_TAG, KIDS_TAG));
    }

    @Test
    public void test_twoPage_whenFindClientsByTag() {
        CardData clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);
        ClientData clientData = clientCard.getClient();
        clientData.addTag(KIDS_TAG);
        clientData.addTag(SUPER_TAG);
        companyFacade.updateClientByCardUUID(DEFAULT_CARD_UUID, clientData, DEFAULT_COMPANY_ID);

        CardData anotherClientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, ANOTHER_CARD_UUID);
        ClientData anotherClientCardClient = anotherClientCard.getClient();
        anotherClientCardClient.addTag(MY_TAG);
        anotherClientCardClient.addTag(SUPER_TAG);
        companyFacade.updateClientByCardUUID(ANOTHER_CARD_UUID, anotherClientCardClient, DEFAULT_COMPANY_ID);

        Pageable pageRequest = new PageRequest(0, 1, new Sort(Sort.Direction.ASC, "cardUUID"));
        Page<ClientData> page = clientFacade.findClientsByTag(SUPER_TAG, DEFAULT_COMPANY_ID, pageRequest);
        assertThat(page).isNotNull();
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(page.getTotalPages()).isEqualTo(2);
        ClientData firstClient = page.getContent().get(0);
        assertThat(firstClient.getCardUUID()).isEqualTo(Math.min(ANOTHER_CARD_UUID, DEFAULT_CARD_UUID));
        assertThat(firstClient.getTags()).contains(SUPER_TAG);


    }

    @Test
    public void test_cardStoresClient_whenGettingClientInsideCard() {
        CardData clientCard = cardFacade.getClientCard(DEFAULT_COMPANY_ID, DEFAULT_CARD_UUID);
        assertThat(clientCard).isNotNull();
        AbstractUserData client = clientCard.getClient();
        assertThat(client).isNotNull();
        assertThat(client.getClass()).isEqualTo(ClientData.class);
    }

}
