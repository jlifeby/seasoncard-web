package com.jlife.abon.dto;

/**
 * @author Dzmitry Misiuk
 */
public class ProductStatData extends IdData {

    private int sold;
    private int active;
    private int archived;
    private int attended;
    private int availableAttendances;

    private ProductData product;

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getArchived() {
        return archived;
    }

    public void setArchived(int archived) {
        this.archived = archived;
    }

    public int getAttended() {
        return attended;
    }

    public void setAttended(int attended) {
        this.attended = attended;
    }

    public int getAvailableAttendances() {
        return availableAttendances;
    }

    public void setAvailableAttendances(int availableAttendances) {
        this.availableAttendances = availableAttendances;
    }

    public ProductData getProduct() {
        return product;
    }

    public void setProduct(ProductData product) {
        this.product = product;
    }
}
