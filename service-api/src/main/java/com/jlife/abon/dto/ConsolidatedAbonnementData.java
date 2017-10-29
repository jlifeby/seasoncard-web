package com.jlife.abon.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public class ConsolidatedAbonnementData implements Serializable {

    private String productId;
    private ProductData product;

    private AbonnementData lastArchive;

    private AbonnementData lastActive;

    private AbonnementData lastActual;

    private List<AbonnementData> allAbonnements;

    public ConsolidatedAbonnementData() {
        this.allAbonnements = new ArrayList<>();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public AbonnementData fetchLast() {
        if (getLastActual() != null) {
            return getLastActual();
        }
        if (getLastActive() != null) {
            return getLastActive();
        }
        if (getLastArchive() != null) {
            return getLastArchive();
        }
        return null;
    }

    public AbonnementData getLastArchive() {
        return lastArchive;
    }

    public void setLastArchive(AbonnementData lastArchive) {
        this.lastArchive = lastArchive;
    }

    public AbonnementData getLastActive() {
        return lastActive;
    }

    public void setLastActive(AbonnementData lastActive) {
        this.lastActive = lastActive;
    }

    public List<AbonnementData> getAllAbonnements() {
        return allAbonnements;
    }

    public AbonnementData getLastActual() {
        return lastActual;
    }

    public void setLastActual(AbonnementData lastActual) {
        this.lastActual = lastActual;
    }

    public void setAllAbonnements(List<AbonnementData> allAbonnements) {
        this.allAbonnements = allAbonnements;
    }

    public ProductData getProduct() {
        return product;
    }

    public void setProduct(ProductData product) {
        this.product = product;
    }
}
