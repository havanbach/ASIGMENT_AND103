package com.example.asigment_and103.Model;

public class PRODUCT {
    private String namePRD,pricePRD,imagePRD;

    public PRODUCT(String namePRD, String pricePRD, String imagePRD) {
        this.namePRD = namePRD;
        this.pricePRD = pricePRD;
        this.imagePRD = imagePRD;
    }

    public PRODUCT() {
    }

    public String getNamePRD() {
        return namePRD;
    }

    public void setNamePRD(String namePRD) {
        this.namePRD = namePRD;
    }

    public String getPricePRD() {
        return pricePRD;
    }

    public void setPricePRD(String pricePRD) {
        this.pricePRD = pricePRD;
    }

    public String getImagePRD() {
        return imagePRD;
    }

    public void setImagePRD(String imagePRD) {
        this.imagePRD = imagePRD;
    }
}

