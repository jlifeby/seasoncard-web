package com.jlife.abon.service;

import com.jlife.abon.entity.Product;
import com.jlife.abon.enumeration.ProductSort;
import com.jlife.abon.error.ApiErrorCode;
import com.jlife.abon.error.NotAllowedException;
import com.jlife.abon.error.ResourceNotFoundException;
import com.jlife.abon.repository.EntityRepository;
import com.jlife.abon.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dzmitry Misiuk
 */
@Service
public class ProductService extends AbstractService implements EntityAbstractService<Product> {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(String productId) {
        Product product = productRepository.findOne(productId);
        if (product == null) {
            throw new ResourceNotFoundException(ApiErrorCode.PRODUCT_NOT_FOUND, productId);
        }
        return product;
    }

    public List<Product> getAllActivePublishedProducts() {
        Collection<String> publishedCompanyIds = companyService.getAllPublishedCompanyIds();
        return productRepository.findByActiveAndPublishedAndCompanyIdIn(true, true, publishedCompanyIds);
    }

    public List<Product> getActivePublishedProductsForCompany(String companyId) {
        return productRepository.findByActiveAndPublishedAndCompanyIdIn(true, true, Arrays.asList(companyId));
    }

    public List<Product> getAllProductsForCompany(String companyId) {
        return productRepository.findByCompanyId(companyId);
    }


    public Page<Product> getAllActivePublishedProducts(Pageable pageRequest) {
        Collection<String> publishedCompanyIds = companyService.getAllPublishedCompanyIds();
        return productRepository.findByActiveAndPublishedAndCompanyIdIn(true, true, publishedCompanyIds, pageRequest);
    }

    public Page<Product> getActiveProductsForCompany(String companyId, Pageable pageRequest) {
        return productRepository.findByCompanyIdAndActive(companyId, true, pageRequest);
    }

    public List<Product> getArchiveProducts(String companyId) {
        return productRepository.findByCompanyIdAndActive(companyId, false);
    }

    public Product archiveProduct(String productId, String companyId) {
        return changeActiveForProduct(productId, false, companyId);
    }

    public Product restoreProduct(String productId, String companyId) {
        return changeActiveForProduct(productId, true, companyId);
    }

    public Product changeActiveForProduct(String productId, boolean active, String companyId) {
        Product product = getProductForModifyingAsCompany(productId, companyId);
        product.setActive(active);
        Product updatedProduct = productRepository.save(product);
        return updatedProduct;
    }

    public Product getProductForModifyingAsCompany(String productId, String companyId) {
        Product product = getProduct(productId);
        if (!product.getCompanyId().equals(companyId)) {
            throw new NotAllowedException(ApiErrorCode.COMPANY_IS_NOT_OWNER_OF_PRODUCT,
                    companyId, productId);
        }
        return product;
    }

    public List<Product> getActiveProductsForCompany(String companyId) {
        return productRepository.findByCompanyIdAndActive(companyId, true);
    }

    public long increaseSelling(String productId) {
        Product product = getProduct(productId);
        long selling = product.getSelling();
        long newSelling = selling + 1;
        product.setSelling(newSelling);
        productRepository.save(product);
        return newSelling;
    }

    public List<Product> getMostSellingPublishedProductsForEachCompany(int count) {
        // todo optimize. What
        // aggregation
        // db.product.aggregate([{$sort: {selling: -1}}, {$group: {_id: "$companyId", "productId": {$first: "$_id"}}}, {$limit: 3}])
        Collection<String> publishedCompanyIds = companyService.getAllPublishedCompanyIds();
        Sort sort = new Sort(Sort.Direction.DESC, ProductSort.POPULAR.getFieldName());
        List<Product> mostSellingForEachCompanyProducts = new ArrayList<>();
        for (String companyId : publishedCompanyIds) {
            Product mostSelling = productRepository.findOneByCompanyIdAndPublishedAndActive(companyId, true, true, sort);
            if (mostSelling != null) {
                mostSellingForEachCompanyProducts.add(mostSelling);
            }
        }
        List<Product> sortedLimitedMostSellingProducts = mostSellingForEachCompanyProducts
                .stream()
                // o2 - o1 because DESC sorting
                .sorted((o1, o2) -> (int) (o2.getSelling() - o1.getSelling()))
                .limit(count)
                .collect(Collectors.toList());

        return sortedLimitedMostSellingProducts;
    }

    @Override
    public EntityRepository<Product> getBaseRepository() {
        return this.productRepository;
    }

    @Override
    public String getBaseEntityName() {
        return Product.class.getSimpleName();
    }

    public int countAllProducts(String companyId) {
        return  productRepository.countByCompanyId(companyId);
    }
}
