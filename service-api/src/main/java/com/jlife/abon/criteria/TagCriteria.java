package com.jlife.abon.criteria;

import com.jlife.abon.enumeration.TagCriteriaSearchMode;

import java.util.List;

public class TagCriteria implements SearchCriteria {
    private List<String> tags;
    private TagCriteriaSearchMode searchMode;

    public TagCriteria(List<String> tags, TagCriteriaSearchMode searchMode) {
        this.tags = tags;
        this.searchMode = searchMode;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public TagCriteriaSearchMode getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(TagCriteriaSearchMode searchMode) {
        this.searchMode = searchMode;
    }
}
