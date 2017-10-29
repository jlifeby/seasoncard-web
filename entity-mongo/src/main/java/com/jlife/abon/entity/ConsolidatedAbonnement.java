package com.jlife.abon.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
public class ConsolidatedAbonnement implements Serializable {

    private String productId;
    private Product product;

    private Abonnement lastArchive;

    private Abonnement lastActive;

    private Abonnement lastActual;

    private List<Abonnement> allAbonnements;

    public ConsolidatedAbonnement() {
        this.allAbonnements = new ArrayList<>();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Abonnement fetchLast() {
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

    public Abonnement getLastArchive() {
        return lastArchive;
    }

    public void setLastArchive(Abonnement lastArchive) {
        this.lastArchive = lastArchive;
    }

    public Abonnement getLastActive() {
        return lastActive;
    }

    public void setLastActive(Abonnement lastActive) {
        this.lastActive = lastActive;
    }

    public List<Abonnement> getAllAbonnements() {
        return allAbonnements;
    }

    public Abonnement getLastActual() {
        return lastActual;
    }

    public void setLastActual(Abonnement lastActual) {
        this.lastActual = lastActual;
    }

    public void setAllAbonnements(List<Abonnement> allAbonnements) {
        this.allAbonnements = allAbonnements;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
