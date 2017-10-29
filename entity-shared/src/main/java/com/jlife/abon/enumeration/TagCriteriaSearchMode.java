package com.jlife.abon.enumeration;

import java.io.Serializable;

public enum TagCriteriaSearchMode implements Serializable {
    IGNORE,
    EXACT,  // [a, b, c] == [c, a, b] - intersection equals union
    ANY,    // [a, b, c] == [d, e, a] - intersection has at least one elem
    ALL     // [a, b, c] == [b, a]    - intersection consists of all search elems
}
