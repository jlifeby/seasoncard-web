package com.jlife.abon.entity;

import com.jlife.abon.utility.CropProperty;

/**
 * @author Dzmitry Misiuk
 */
public class Media extends Entity {

    private String originFilename;
    private String filename;
    private String relativePath;

    private CropProperty cropProperty;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getOriginFilename() {
        return originFilename;
    }

    public void setOriginFilename(String originFilename) {
        this.originFilename = originFilename;
    }

    public CropProperty getCropProperty() {
        return cropProperty;
    }

    public void setCropProperty(CropProperty cropProperty) {
        this.cropProperty = cropProperty;
    }
}
