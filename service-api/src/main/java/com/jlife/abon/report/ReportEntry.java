package com.jlife.abon.report;

import java.util.LinkedHashMap;

/**
 * @author Dzmitry Misiuk
 */
public class ReportEntry {

    LinkedHashMap<Object, Object> pairs;

    public ReportEntry() {
        this.pairs = new LinkedHashMap<>();
    }


    public void addPair(Object key, Object value) {
        this.pairs.put(key, value);
    }


    public Object getValue(Object key) {
        Object value = this.pairs.get(key);
        if (value == null) {
            throw new IllegalArgumentException("You try to get not-existed value for key: " + key);
        }
        return value;
    }


    public LinkedHashMap<Object, Object> getPairs() {
        return pairs;
    }
}
