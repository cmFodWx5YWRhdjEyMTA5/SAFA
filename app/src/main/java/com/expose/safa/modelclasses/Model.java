package com.expose.safa.modelclasses;

import android.graphics.Bitmap;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vineesh on 30/03/2017.
 */

public class Model {

    @SerializedName("GetProductCategoryNamesResult")
    private List<Model> GetProductCategoryNamesResult;

    @SerializedName("GetBrandNamesResult")
    private List<Model> GetBrandNamesResult;


    @SerializedName("GetProductsResult")
    private List<Model> ProductsResult;



    @SerializedName("ItemCategory_cName")
    private String ItemCategory_cName;

    @SerializedName("ItemCategory_nId")
    private int ItemCategory_nId;



    @SerializedName("Brand_cName")
    private String Brand_cName;

    @SerializedName("Brand_nId")
    private int Brand_nId;




    @SerializedName("Category_nId")
    private int Category_nId;

    @SerializedName("Details")
    private String Details;

    @SerializedName("Imageurl")
    private String Imageurl;

    @SerializedName("Unit")
    private String Unit;

    @SerializedName("Item_cName")
    private String Item_cName;

    @SerializedName("Item_nDefaultPrice")
    private double item_price;

    @SerializedName("Item_nId")
    private int Item_nId;

    @SerializedName("ImagePath")
    private String ImagePath;



    public String getItem_cName() {
        return Item_cName;
    }

    public void setItem_cName(String item_cName) {
        Item_cName = item_cName;
    }

    public List<Model> getGetProductCategoryNamesResult() {
        return GetProductCategoryNamesResult;
    }

    public String getItemCategory_cName() {
        return ItemCategory_cName;
    }

    public int getItemCategory_nId() {
        return ItemCategory_nId;
    }


    public List<Model> getGetBrandNamesResult() {
        return GetBrandNamesResult;
    }

    public String getBrand_cName() {
        return Brand_cName;
    }




    public int getBrand_nId() {
        return Brand_nId;
    }

    public void setGetProductCategoryNamesResult(List<Model> getProductCategoryNamesResult) {
        GetProductCategoryNamesResult = getProductCategoryNamesResult;
    }

    public void setItemCategory_cName(String itemCategory_cName) {
        ItemCategory_cName = itemCategory_cName;
    }

    public void setItemCategory_nId(int itemCategory_nId) {
        ItemCategory_nId = itemCategory_nId;
    }

    public void setGetBrandNamesResult(List<Model> getBrandNamesResult) {
        GetBrandNamesResult = getBrandNamesResult;
    }

    public void setBrand_cName(String brand_cName) {
        Brand_cName = brand_cName;
    }


    public void setBrand_nId(int brand_nId) {
        Brand_nId = brand_nId;
    }


    public List<Model> getProductsResult() {
        return ProductsResult;
    }


    public void setProductsResult(List<Model> productsResult) {
        ProductsResult = productsResult;
    }


    public int getCategory_nId() {
        return Category_nId;
    }


    public void setCategory_nId(int category_nId) {
        Category_nId = category_nId;
    }


    public String getDetails() {
        return Details;
    }


    public void setDetails(String details) {
        Details = details;
    }


    public String getImageurl() {
        return Imageurl;
    }


    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }


    public String getUnit() {
        return Unit;
    }


    public void setUnit(String unit) {
        Unit = unit;
    }


    public double getItem_price() {
        return item_price;
    }


    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }


    public int getItem_nId() {
        return Item_nId;
    }


    public void setItem_nId(int item_nId) {
        Item_nId = item_nId;
    }

}
