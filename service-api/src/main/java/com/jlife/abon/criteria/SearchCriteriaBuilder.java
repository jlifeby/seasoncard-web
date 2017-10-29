package com.jlife.abon.criteria;

import com.jlife.abon.enumeration.TagCriteriaSearchMode;

import java.util.*;

public class SearchCriteriaBuilder {
    private Map<String, SearchCriteria> state;

    public SearchCriteriaBuilder() {
        this.state = new HashMap<>();
    }

    public List<SearchCriteria> build() {
        return new ArrayList<>(state.values());
    }

    public SearchCriteriaBuilder clear() {
        state.clear();
        return this;
    }

    public SearchCriteriaBuilder daysLeft(Integer daysLeft) {
        final ExpiringAbonementCriteria criteria = getExpiringAbonementCriteria();
        criteria.setDaysLeft(daysLeft);
        return this;
    }

    public SearchCriteriaBuilder attendancesLeft(Integer attendancesLeft) {
        final ExpiringAbonementCriteria criteria = getExpiringAbonementCriteria();
        criteria.setAttendancesLeft(attendancesLeft);
        return this;
    }

    public SearchCriteriaBuilder unitsLeftRatio(Float ratio) {
        final ExpiringAbonementCriteria criteria = getExpiringAbonementCriteria();
        criteria.setUnitsLeftRatio(ratio);
        return this;
    }

    public SearchCriteriaBuilder text(String text) {
        final FullTextCriteria criteria = getFullTextCriteria();
        criteria.setText(text);
        return this;
    }



    public SearchCriteriaBuilder hasTags(List<String> tags) {
        final TagCriteria criteria = getTagCriteria();
        criteria.setTags(tags);
        return this;
    }

    public SearchCriteriaBuilder tagsSearchMode(TagCriteriaSearchMode mode) {
        final TagCriteria criteria = getTagCriteria();
        criteria.setSearchMode(mode);
        return this;
    }

    private String getCriteriaName(Class c) {
        if (c == null) {
            return null;
        } else {
            return c.getName();
        }
    }

    private ExpiringAbonementCriteria getExpiringAbonementCriteria() {
        final String criteriaName = getCriteriaName(ExpiringAbonementCriteria.class);
        ExpiringAbonementCriteria value = (ExpiringAbonementCriteria) state.get(criteriaName);
        if (value == null) {
            value = new ExpiringAbonementCriteria();
            state.put(criteriaName, value);
        }
        return value;
    }

    private FullTextCriteria getFullTextCriteria() {
        final String criteriaName = getCriteriaName(FullTextCriteria.class);
        FullTextCriteria value = (FullTextCriteria) state.get(criteriaName);
        if (value == null) {
            value = new FullTextCriteria("");
            state.put(criteriaName, value);
        }
        return value;
    }


    private TagCriteria getTagCriteria() {
        final String criteriaName = getCriteriaName(TagCriteria.class);
        TagCriteria value = (TagCriteria) state.get(criteriaName);
        if (value == null) {
            value = new TagCriteria(Collections.emptyList(), TagCriteriaSearchMode.IGNORE);
            state.put(criteriaName, value);
        }
        return value;
    }
}
