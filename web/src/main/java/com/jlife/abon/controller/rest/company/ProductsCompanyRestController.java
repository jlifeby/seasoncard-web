package com.jlife.abon.controller.rest.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.ProductData;
import com.jlife.abon.dto.ProductStatData;
import com.jlife.abon.enumeration.ProductSort;
import com.jlife.abon.facade.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@RestController
@RequestMapping("/api/company")
public class ProductsCompanyRestController extends BaseController {

    @Autowired
    ProductFacade productFacade;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductData> getActiveProducts(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                               @RequestParam(value = "size", required = false, defaultValue = "1000") int size,
                                               @RequestParam(value = "direction", required = false, defaultValue = "DESC") Sort.Direction direction) {
        Sort sorting = new Sort(new Sort.Order(direction, ProductSort.POPULAR.getFieldName()));
        Pageable pageRequest = new PageRequest(page, size, sorting);
        String companyId = getSessionCompanyId();
        return productFacade.getActiveProductsForCompany(companyId, pageRequest).getContent();
    }

    @RequestMapping(value = "/products/archive", method = RequestMethod.GET)
    public List<ProductData> getArchiveProducts() {
        String companyId = getSessionCompanyId();
        return productFacade.getArchiveProducts(companyId);
    }

    @RequestMapping(value = "/products/archive", method = RequestMethod.POST)
    public ProductData archiveProduct(@RequestParam("productId") String productId) {
        return productFacade.archiveProduct(productId, getSessionCompanyId());
    }

    @RequestMapping(value = "/products/restore", method = RequestMethod.POST)
    public ProductData restoreProduct(@RequestParam("productId") String productId) {
        return productFacade.restoreProduct(productId, getSessionCompanyId());
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.GET)
    public ProductData getProduct(@PathVariable("productId") String productId) {
        // todo should we check that company is owner of product?
        return productFacade.getProduct(productId);
    }

    @RequestMapping(value = {"/productstat/{productId}", "/productstats/{productId}"}, method = RequestMethod.GET)
    public ProductStatData getProductStat(@PathVariable("productId") String productId) {
        return productFacade.getProductStat(productId, getSessionCompanyId());
    }

    @RequestMapping(value = "/products/new", method = RequestMethod.POST)
    public ProductData createProduct(@RequestBody ProductData product) {
        product.setId(null);
        product.setActive(true);
        product.setCompanyId(getSessionCompanyId());
        return productFacade.createProduct(product, getSessionCompanyId());
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductData updateProduct(@RequestBody ProductData product, @PathVariable("productId") String productId) {
        return productFacade.update(productId, product, getSessionCompanyId());
    }
}
