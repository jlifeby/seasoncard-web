package com.jlife.abon.api;

/**
 * @author Dzmitry Misiuk
 */
public class NewPriceResponse extends ApiResponse {

    private Double newPrice;

    public Double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Double newPrice) {
        this.newPrice = newPrice;
    }
}
