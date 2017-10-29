package com.jlife.abon.facade;

import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.ProductData;
import com.jlife.abon.dto.ProductStatData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Copyright © 2016 JLife. All rights reserved.
 *
 * @author Dzmitry Misiuk
 */
public interface ProductFacade {
    ProductData getProductWithCompany(String productId);

    List<ProductData> getActiveProductsForCompany(String companyId);

    Page<ProductData> getActiveProductsForCompany(String companyId, Pageable pageRequest);

    List<ProductData> getAllProductsForCompany(String companyId);

    List<ProductData> getActiveProducts();

    List<ProductData> getActiveProductsWithCompanies();

    void removeProduct(ProductData product);

    ProductData getProduct(String productId);

    AbonnementData buyProduct(String userId, String productId);

    ProductData createProduct(ProductData product, String companyId);

    ProductStatData getProductStat(String productId, String companyId);

    ProductData update(String productId, ProductData product, String companyId);

    Page<ProductData> getActivePublishedProductsWithCompanies(Pageable pageRequest);

    ProductData archiveProduct(String productId, String companyId);

    ProductData restoreProduct(String productId, String companyId);

    List<ProductData> getArchiveProducts(String companyId);

    List<ProductData> getMostSellingPublishedProductsForEachCompany(int count);

    ProductData save(ProductData rowProduct);
}
