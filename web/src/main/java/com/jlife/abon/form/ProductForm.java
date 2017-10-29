package com.jlife.abon.form;

import com.jlife.abon.dto.ProductData;
import com.jlife.abon.enumeration.AbonnementType;
import com.jlife.abon.enumeration.PreferredStartingDate;
import com.jlife.abon.utility.DefaultValues;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Dzmitry Misiuk
 */
public class ProductForm implements Serializable {

    private String id;

    @NotBlank
    private String name;
    @NotBlank
    private String description;

    private String htmlContent;
    @NotNull
    private Integer countOfAttendances;
    private Integer countOfUnits;
    @NotNull
    private Double price;

    @NotBlank
    private String logoPath;

    private String logoMediaId;

    private boolean published;

    private AbonnementType abonnementType;
    private PreferredStartingDate preferredStartingDate;
    private String unitName;

    public ProductForm() {
        this.logoPath = DefaultValues.DEFAULT_PRODUCT_IMAGE_PATH;
        this.abonnementType = AbonnementType.BY_ATTENDANCE;
        this.preferredStartingDate = PreferredStartingDate.PURCHASE_DATE;
        this.published = true;
        this.unitName = "Минуты";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Integer getCountOfAttendances() {
        return countOfAttendances;
    }

    public void setCountOfAttendances(Integer countOfAttendances) {
        this.countOfAttendances = countOfAttendances;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getLogoMediaId() {
        return logoMediaId;
    }

    public void setLogoMediaId(String logoMediaId) {
        this.logoMediaId = logoMediaId;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public AbonnementType getAbonnementType() {
        return abonnementType;
    }

    public void setAbonnementType(AbonnementType abonnementType) {
        this.abonnementType = abonnementType;
    }

    public ProductData toProduct() {
        ProductData product = new ProductData();
        product.setId(getId());
        product.setName(getName());
        product.setDescription(getDescription());
        product.setHtmlContent(getHtmlContent());
        product.setCountOfAttendances(getCountOfAttendances());
        if(getAbonnementType() == AbonnementType.BY_UNIT){
            product.setCountOfUnits(getCountOfUnits());
            product.setUnitName(getUnitName());
        }
        product.setPrice(getPrice());
        product.setLogoPath(getLogoPath());
        product.setLogoMediaId(getLogoMediaId());
        product.setAbonnementType(getAbonnementType());
        product.setPreferredStartingDate(getPreferredStartingDate());
        product.setPublished(isPublished());
        return product;
    }

    public PreferredStartingDate getPreferredStartingDate() {
        return preferredStartingDate;
    }

    public void setPreferredStartingDate(PreferredStartingDate preferredStartingDate) {
        this.preferredStartingDate = preferredStartingDate;
    }

    public boolean checkIsNew() {
        return id == null;
    }

    public Integer getCountOfUnits() {
        return countOfUnits;
    }

    public void setCountOfUnits(Integer countOfUnits) {
        this.countOfUnits = countOfUnits;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}

