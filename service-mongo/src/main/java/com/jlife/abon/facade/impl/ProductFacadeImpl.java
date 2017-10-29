package com.jlife.abon.facade.impl;

import com.jlife.abon.dto.AbonnementData;
import com.jlife.abon.dto.ProductData;
import com.jlife.abon.dto.ProductStatData;
import com.jlife.abon.entity.Abonnement;
import com.jlife.abon.entity.Company;
import com.jlife.abon.entity.Product;
import com.jlife.abon.enumeration.AbonnementType;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.facade.ProductFacade;
import com.jlife.abon.repository.AbonnementRepository;
import com.jlife.abon.repository.CompanyRepository;
import com.jlife.abon.repository.ProductRepository;
import com.jlife.abon.service.AbonnementService;
import com.jlife.abon.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dzmitry Misiuk
 */
@Component
public class ProductFacadeImpl extends AbstractFacade implements ProductFacade {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AbonnementRepository abonnementRepository;

    @Autowired
    AbonnementService abonnementService;


    @Autowired
    private StatisticService statisticService;

    @Override
    public ProductData getProductWithCompany(String productId) {
        Product product = productService.getProduct(productId);
        populateCompany(product);
        ProductData productData = dataMapper.toProductData(product);
        return productData;
    }


    @Override
    public List<ProductData> getActiveProductsForCompany(String companyId) {
        List<Product> products = productRepository.findByCompanyIdAndActive(companyId, true);
        return dataMapper.toProductDataList(products);
    }

    @Override
    public Page<ProductData> getActiveProductsForCompany(String companyId, Pageable pageRequest) {
        Page<Product> productPage = productService.getActiveProductsForCompany(companyId, pageRequest);
        return productPage.map(source -> dataMapper.toProductData(source));
    }

    @Override
    public List<ProductData> getAllProductsForCompany(String companyId) {
        List<Product> products = productService.getAllProductsForCompany(companyId);
        return dataMapper.toProductDataList(products);
    }

    @Override
    public List<ProductData> getActiveProducts() {
        List<Product> products = productRepository.findByActive(true);
        return dataMapper.toProductDataList(products);
    }

    @Override
    public List<ProductData> getActiveProductsWithCompanies() {
        List<Product> products = productRepository.findByActive(true);
        Map<String, Company> idCompanyMap = new HashMap<>();
        for (Product product : products) {
            String companyId = product.getCompanyId();
            if (!idCompanyMap.containsKey(companyId)) {
                Company company = companyRepository.findOne(companyId);
                idCompanyMap.put(companyId, company);
            }
            product.setCompany(idCompanyMap.get(companyId));
        }
        return dataMapper.toProductDataList(products);
    }

    @Override
    public void removeProduct(ProductData product) {
        // todo check existed abonnement
        productRepository.delete(dataMapper.fromProductData(product));
    }

    @Override
    public ProductData getProduct(String productId) {

        Product product = productRepository.findOne(productId);
        if (product == null) {
            throw new ResourceNotFoundException(ApiErrorCode.PRODUCT_NOT_FOUND,
                    productId);
        }
        return dataMapper.toProductData(product);
    }

    @Override
    public AbonnementData buyProduct(String userId, String productId) {
        Abonnement abonnement = abonnementService.buyAbonnement(userId, productId);
        return dataMapper.toAbonnementData(abonnement);
    }

    @Override
    public ProductData createProduct(ProductData product, String companyId) {
        // todo move to product service
        Product newProduct = productRepository.save(dataMapper.fromProductData(product));
        return dataMapper.toProductData(newProduct);
    }

    @Override
    public ProductStatData getProductStat(String productId, String companyId) {
        return statisticService.getProductStat(productId, companyId);
    }

    @Override
    public ProductData update(String productId, ProductData product, String companyId) {
        // todo move to product service
        Product existedProduct = productService.getProductForModifyingAsCompany(productId, companyId);
        existedProduct.setName(product.getName());
        existedProduct.setPreferredStartingDate(product.getPreferredStartingDate());
        existedProduct.setDescription(product.getDescription());
        existedProduct.setLogoPath(product.getLogoPath());
        existedProduct.setLogoMediaId(product.getLogoMediaId());
        existedProduct.setPrice(product.getPrice());
        existedProduct.setHtmlContent(product.getHtmlContent());
        existedProduct.setPublished(product.isPublished());
        if (existedProduct.getAbonnementType() == AbonnementType.BY_ATTENDANCE) {
            existedProduct.setCountOfAttendances(product.getCountOfAttendances());
        }
        if (existedProduct.getAbonnementType() == AbonnementType.BY_UNIT) {
            existedProduct.setCountOfUnits(product.getCountOfUnits());
            existedProduct.setUnitName(product.getUnitName());
        }
        Product updatedProduct = productRepository.save(existedProduct);
        return dataMapper.toProductData(updatedProduct);
    }

    @Override
    public Page<ProductData> getActivePublishedProductsWithCompanies(Pageable pageRequest) {
        Page<Product> productsPage = productService.getAllActivePublishedProducts(pageRequest);
        populateCompanies(productsPage.getContent());
        return productsPage.map(product -> dataMapper.toProductData(product));
    }

    @Override
    public ProductData archiveProduct(String productId, String companyId) {
        Product product = productService.archiveProduct(productId, companyId);
        return dataMapper.toProductData(product);
    }

    @Override
    public ProductData restoreProduct(String productId, String companyId) {
        Product product = productService.restoreProduct(productId, companyId);
        return dataMapper.toProductData(product);
    }

    @Override
    public List<ProductData> getArchiveProducts(String companyId) {
        List<Product> archiveProducts = productService.getArchiveProducts(companyId);
        return dataMapper.toProductDataList(archiveProducts);
    }

    @Override
    public List<ProductData> getMostSellingPublishedProductsForEachCompany(int count) {
        List<Product> popularProducts = productService.getMostSellingPublishedProductsForEachCompany(count);
        populateCompanies(popularProducts);
        return dataMapper.toProductDataList(popularProducts);
    }

    @Override
    public ProductData save(ProductData rowProduct) {
        Product product = productRepository.save(dataMapper.fromProductData(rowProduct));
        return dataMapper.toProductData(product);
    }
}
