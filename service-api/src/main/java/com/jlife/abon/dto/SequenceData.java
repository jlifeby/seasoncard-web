package com.jlife.abon.dto;

/**
 * @author Dzmitry Misiuk
 */
public class SequenceData extends BaseData {

    private String name;

    private int seq;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
