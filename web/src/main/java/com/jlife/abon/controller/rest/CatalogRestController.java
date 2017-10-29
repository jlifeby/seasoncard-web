package com.jlife.abon.controller.rest;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.CompanyData;
import com.jlife.abon.dto.ProductData;
import com.jlife.abon.enumeration.ProductSort;
import com.jlife.abon.facade.CatalogFacade;
import com.jlife.abon.facade.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Dzmitry Misiuk
 */
@RestController
@Validated
public class CatalogRestController extends BaseController {

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private CatalogFacade catalogFacade;

    @RequestMapping(value = "/api/catalog/products", method = RequestMethod.GET)
    public List<ProductData> getProducts(@RequestParam(value = "sort", required = false, defaultValue = "POPULAR") ProductSort productSort,
                                         @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                         @RequestParam(value = "size", required = false, defaultValue = "1000") int size,
                                         @RequestParam(value = "direction", required = false) Sort.Direction direction) {
        if (direction == null) {
            if (ProductSort.POPULAR == productSort) {
                direction = Sort.Direction.DESC;
            } else {
                direction = Sort.Direction.ASC;
            }
        }
        Sort sorting = new Sort(new Sort.Order(direction, productSort.getFieldName()));
        Pageable pageRequest = new PageRequest(page, size, sorting);
        // todo add pagination for android
        return productFacade.getActivePublishedProductsWithCompanies(pageRequest).getContent();
    }


    @RequestMapping(value = "/api/catalog/companies", method = RequestMethod.GET)
    List<CompanyData> getCompanies() {
        // todo add pagination
        return catalogFacade.getPublishedCompanies();
    }
}
