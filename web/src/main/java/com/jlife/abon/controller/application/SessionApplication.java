package com.jlife.abon.controller.application;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Dzmitry Misiuk
 */
@Component()
@Scope("session")
public class SessionApplication implements java.io.Serializable {

    private String companyId;
    private Set<String> cartProducts;

    public SessionApplication() {
        cartProducts = new HashSet<>();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Set<String> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(Set<String> cartProducts) {
        this.cartProducts = cartProducts;
    }
}