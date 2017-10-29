package com.jlife.abon.dto;

import java.util.List;

/**
 * @author Aliaksei Pashkouski
 * @author Dzmitry Misiuk
 */
public class ClientGroupData extends BaseData {

    private String productId;
    private ProductData product;

    private int memberCount;
    private List<CardData> members;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ProductData getProduct() {
        return product;
    }

    public void setProduct(ProductData product) {
        this.product = product;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public List<CardData> getMembers() {
        return members;
    }

    public void setMembers(List<CardData> members) {
        this.members = members;
    }
}