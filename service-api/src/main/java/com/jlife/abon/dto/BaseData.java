package com.jlife.abon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for entities that includes id, version, createdBy, modifiedBy, createdDate, lastModifiedDate properties.
 *
 * @author Khralovich Dzmitry
 * @author Dzmitry Misiuk
 */
public abstract class BaseData extends IdData {

    @JsonIgnore
    private Long version;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ssZ")
    private DateTime createdDate;

    @JsonIgnore
    private DateTime lastModifiedDate;

    @JsonIgnore
    private String createdBy;

    @JsonIgnore
    private String lastModifiedBy;

    protected boolean active;
    protected String companyId;

    protected BaseData() {
        this.active = true;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    public DateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(DateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public BaseData merge(BaseData newEntity) {
        try {
            BaseData oldEntity = this;
            Class clazz = oldEntity.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            Field[] superDeclaredFields = clazz.getSuperclass().getDeclaredFields();
            List<Field> allFields = new ArrayList<Field>();
            allFields.addAll(Arrays.asList(declaredFields));
            allFields.addAll(Arrays.asList(superDeclaredFields));
            for (Field field : allFields) {
                field.setAccessible(true);
                Object oldValue = field.get(oldEntity);
                Object newValue = field.get(newEntity);
                if (newValue != null
                        && !field.isAnnotationPresent(JsonIgnore.class)
                        && !"_id".equals(field.getName())) {
                    field.set(oldEntity, newValue);
                }
                field.setAccessible(false);
            }
            return this;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("bad merge implementation");
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
