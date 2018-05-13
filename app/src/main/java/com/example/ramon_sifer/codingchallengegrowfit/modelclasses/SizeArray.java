package com.example.ramon_sifer.codingchallengegrowfit.modelclasses;

/**
 * Created by ramon_sifer on 13/5/18.
 */
public class SizeArray {
    private Boolean available;
    private String size;
    private String sku;

    public SizeArray(Boolean available, String size, String sku) {
        this.available = available;
        this.size = size;
        this.sku = sku;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
