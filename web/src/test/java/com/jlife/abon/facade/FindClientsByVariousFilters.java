package com.jlife.abon.facade;

import com.jlife.abon.criteria.SearchCriteria;
import com.jlife.abon.criteria.SearchCriteriaBuilder;
import com.jlife.abon.dto.ClientData;
import com.jlife.abon.enumeration.TagCriteriaSearchMode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FindClientsByVariousFilters extends ServiceBaseTestCase {

    @Autowired
    private ClientFacade clientFacade;

    private final PageRequest defaultPageable = new PageRequest(0, Integer.MAX_VALUE);
    private final List<SearchCriteria> defaultCriteria = Collections.emptyList();
    private final Integer allClientsCount = 2;

    @Test
    public void test_findClients_whenNoSearchParamsAreSpecified() {
        final Page<ClientData> clients = clientFacade
                .findClients(defaultCriteria, DEFAULT_COMPANY_ID, defaultPageable);
        assertThat(clients).isNotNull().hasSize(allClientsCount);
        assertThat(clients.getSize()).isLessThanOrEqualTo(defaultPageable.getPageSize());
    }

    @Test
    public void test_findClients_whenTextSearchParamIsSpecified() {
        final SearchCriteriaBuilder criteriaBuilder = new SearchCriteriaBuilder();
        criteriaBuilder.text("junit");
        final Page<ClientData> clients1 = clientFacade.findClients(criteriaBuilder.build(), DEFAULT_COMPANY_ID, defaultPageable);
        assertThat(clients1).isNotNull().hasSize(allClientsCount);
        criteriaBuilder.text("user0");
        final Page<ClientData> clients2 = clientFacade.findClients(criteriaBuilder.build(), DEFAULT_COMPANY_ID, defaultPageable);
        assertThat(clients2).isNotNull().hasSize(1);
        criteriaBuilder.text("non-existing-user");
        final Page<ClientData> clients3 = clientFacade.findClients(criteriaBuilder.build(), DEFAULT_COMPANY_ID, defaultPageable);
        assertThat(clients3).isNotNull().isEmpty();
        criteriaBuilder.text(DEFAULT_CLIENT_PHONE);
        final Page<ClientData> tmpClients = clientFacade.findClients(criteriaBuilder.build(), DEFAULT_COMPANY_ID, defaultPageable);
        assertThat(tmpClients).isNotNull().isNotEmpty();
        final ClientData client = tmpClients.iterator().next();
        client.setInternalId(DEFAULT_CLIENT_INTERNAL_ID);
        companyFacade.save(client);
        criteriaBuilder.text(DEFAULT_CLIENT_INTERNAL_ID);
        final Page<ClientData> clients4 = clientFacade.findClients(criteriaBuilder.build(), DEFAULT_COMPANY_ID, defaultPageable);
        assertThat(clients4).isNotNull().hasSize(1);
    }

    @Test
    public void test_findClients_whenTagCriteriaIsSpecified() {
        final SearchCriteriaBuilder criteriaBuilder = new SearchCriteriaBuilder();
        criteriaBuilder.hasTags(Arrays.asList("tag1", "tag2", "TAG_THREE"));
        criteriaBuilder.tagsSearchMode(TagCriteriaSearchMode.IGNORE);
        final List<SearchCriteria> ignoreCriteria = criteriaBuilder.build();
        final Page<ClientData> clients1 = clientFacade.findClients(ignoreCriteria, DEFAULT_COMPANY_ID, defaultPageable);
        assertThat(clients1).isNotNull().isNotEmpty().hasSize(allClientsCount);

        criteriaBuilder.tagsSearchMode(TagCriteriaSearchMode.EXACT);
        final List<SearchCriteria> exactCriteria = criteriaBuilder.build();
        final Page<ClientData> clients2 = clientFacade.findClients(exactCriteria, DEFAULT_COMPANY_ID, defaultPageable);
        assertThat(clients2).isNotNull().isEmpty();

        criteriaBuilder.tagsSearchMode(TagCriteriaSearchMode.ANY);
        final List<SearchCriteria> anyCriteria = criteriaBuilder.build();
        final Page<ClientData> clients3 = clientFacade.findClients(anyCriteria, DEFAULT_COMPANY_ID, defaultPageable);
        assertThat(clients3).isNotNull().isNotEmpty().hasSize(allClientsCount);

        criteriaBuilder.tagsSearchMode(TagCriteriaSearchMode.ALL);
        final List<SearchCriteria> allCriteria = criteriaBuilder.build();
        final Page<ClientData> clients4 = clientFacade.findClients(allCriteria, DEFAULT_COMPANY_ID, defaultPageable);
        assertThat(clients4).isNotNull().isEmpty();
    }

    @Test
    public void test_findClients_whenTextAndTagCriteriaAreBothSpecified() {
        final SearchCriteriaBuilder criteriaBuilder = new SearchCriteriaBuilder();
        criteriaBuilder.hasTags(Arrays.asList("tag1", "TAG_THREE"));
        criteriaBuilder.tagsSearchMode(TagCriteriaSearchMode.ANY);
        criteriaBuilder.text("user0");
        final Page<ClientData> clients = clientFacade.findClients(criteriaBuilder.build(), DEFAULT_COMPANY_ID, defaultPageable);
        assertThat(clients).isNotNull().hasSize(1);
    }

}
