package com.jlife.abon.controller.company;

import com.jlife.abon.controller.application.BaseController;
import com.jlife.abon.dto.ProductData;
import com.jlife.abon.enumeration.AbonnementType;
import com.jlife.abon.enumeration.PreferredStartingDate;
import com.jlife.abon.enumeration.ProductSort;
import com.jlife.abon.error.AbonRuntimeException;
import com.jlife.abon.facade.ProductFacade;
import com.jlife.abon.form.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Khralovich Dzmitry
 * @author Dzmitry Misiuk
 */
@Controller
@RequestMapping("/company")
@Secured({"ROLE_COMPANY"})
public class ProductController extends BaseController {

    @Value("${uploaded.image.path}")
    protected String uploadedImagePath;
    @Autowired
    private ProductFacade productFacade;

    @RequestMapping(value = "/products")
    public String getCompanyAbons(@RequestParam(value = "sort", required = false, defaultValue = "POPULAR") ProductSort sort,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                  @RequestParam(value = "size", required = false, defaultValue = "1000") int size,
                                  @RequestParam(value = "direction", required = false) Sort.Direction direction,
                                  ModelMap model) {

        model.put("productSorts", ProductSort.values());
        model.put("selectedProductSort", sort);

        if (direction == null) {
            if (ProductSort.POPULAR == sort) {
                direction = Sort.Direction.DESC;
            } else {
                direction = Sort.Direction.ASC;
            }
        }
        Sort sorting = new Sort(new Sort.Order(direction, sort.getFieldName()));
        Pageable pageRequest = new PageRequest(page, size, sorting);

        try {
            model.put("products", productFacade.getActiveProductsForCompany(getSessionCompanyId(), pageRequest).getContent());
        } catch (AbonRuntimeException e) {
            LOG.error("Error getting product", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
        }

        return "company-products";
    }

    @RequestMapping(value = "/products/archive", method = RequestMethod.GET)
    public String getCompanyArchiveProducts(ModelMap model) {
        try {
            model.put("products", productFacade.getArchiveProducts(getSessionCompanyId()));
        } catch (AbonRuntimeException e) {
            LOG.error("Error on getting archive products", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
        }
        return "company-products-archive";
    }

    @RequestMapping(value = "/products/{productId}/preview")
    public String viewProduct(@PathVariable("productId") String productId,
                              ModelMap model) {

        model.put("product", productFacade.getProductWithCompany(productId));
        return "product-view";
    }

    @RequestMapping(value = "/products/new")
    public String newProduct(ModelMap model) {
        ProductForm productForm = new ProductForm();
        productForm.setLogoPath(getApplication().getCurrentCompany().getDefaultProductLogoPath());
        model.put("productForm", productForm);
        model.put("preferredStartingDateValues", PreferredStartingDate.values());
        model.put("abonnementTypeValues", AbonnementType.values());
        return "product-detail";
    }

    @RequestMapping(value = "/products/detail/save", method = RequestMethod.POST)
    public String saveProduct(Model model,
                              @Valid @ModelAttribute("productForm") ProductForm productForm,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("productForm", productForm);
            model.addAttribute("preferredStartingDateValues", PreferredStartingDate.values());
            model.addAttribute("abonnementTypeValues", AbonnementType.values());
            return "product-detail";
        }
        ProductData product = productForm.toProduct();
        try {
            String msg;
            if (product.getId() == null) {
                product.setCompanyId(getSessionCompanyId());
                product.setActive(true);
                productFacade.createProduct(product, getSessionCompanyId());
                msg = String.format("Продукт %s создан!", product.getName());
            } else {
                productFacade.update(product.getId(), product, getSessionCompanyId());
                msg = String.format("Продукт %s сохранен!", product.getName());
            }
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, msg);
            return "redirect:/company/products/";
        } catch (AbonRuntimeException e) {
            LOG.error("Error on saving product", e);
            model.addAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
            model.addAttribute("productForm", productForm);
            return "product-detail";
        }
    }

    @RequestMapping(value = "/products/{productId}/detail")
    public String getProductDescription(@PathVariable("productId") String productId, ModelMap model) {
        // todo change. For now implicit transform from model to form on jsp
        model.put("productForm", productFacade.getProduct(productId));
        model.put("preferredStartingDateValues", PreferredStartingDate.values());
        model.put("abonnementTypeValues", AbonnementType.values());

        return "product-detail";
    }

    @RequestMapping(value = "/products/archive", method = RequestMethod.POST)
    public String archiveProduct(@ModelAttribute("productId") String productId,
                                 RedirectAttributes redirectAttributes) {
        try {
            ProductData updatedProduct = productFacade.archiveProduct(productId, getSessionCompanyId());
            redirectAttributes.addFlashAttribute("flashMessage", "Абонемент " + updatedProduct.getName() + " перемещен в архив.");
        } catch (AbonRuntimeException e) {
            LOG.error("Error on archive product", e);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
        }
        return "redirect:/company/products";
    }

    @RequestMapping(value = "/products/{productId}/restore")
    public String restoreProduct(@PathVariable("productId") String productId,
                                 RedirectAttributes redirectAttributes) {

        try {
            ProductData updatedProduct = productFacade.restoreProduct(productId, getSessionCompanyId());
            redirectAttributes.addFlashAttribute("flashMessage", "Абонемент " + updatedProduct.getName() + " восстановлен.");
        } catch (AbonRuntimeException e) {
            LOG.error("Error on archive product", e);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getPrettyString(messageSource));
        }
        return "redirect:/company/products";
    }
}
