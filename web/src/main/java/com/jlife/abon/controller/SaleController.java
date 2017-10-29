package com.jlife.abon.controller;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.ProductData;
import com.jlife.abon.dto.UserData;
import com.jlife.abon.facade.ProductFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Khralovich Dzmitry
 */
@Controller
@Secured("ROLE_USER")
public class SaleController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(SaleController.class);

    @Autowired
    private ProductFacade productFacade;

    @RequestMapping(value = "/products/{productId}/buy")
    public String buyProduct(@PathVariable("productId") String productId, ModelMap model) {

        getApplication().addProductToCart(productId);

        return "redirect:/cart";
    }

    @RequestMapping(value = "/cart")
    public String getCart(ModelMap model) {
        Set<String> productIds = getApplication().getCartProducts();
        List<ProductData> products = new ArrayList<>();
        for (String productId : productIds) {
            products.add(productFacade.getProduct(productId));
        }
        model.put("products", products);
        return "cart";
    }

    @RequestMapping(value = "/cart/remove/{productId}")
    public String getCart(@PathVariable("productId") String productId, ModelMap model) {

        getApplication().removeProductFromCart(productId);

        return "redirect:/cart";
    }

    @RequestMapping(value = "/cart/buy")
    public String buyCart(ModelMap model) {

        UserData user = getCurrentUser();
        Set<String> productIds = getApplication().getCartProducts();
        //TODO buying
        for (String productId : productIds) {
            productFacade.buyProduct(user.getId(), productId);
        }
        getApplication().clearCart();
        return "redirect:/personal";
    }

}
