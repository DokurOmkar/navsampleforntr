package com.deftlogic.ntr.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by omkardokur on 1/15/16.
 */
public class CategoryItem implements Serializable{
    private int categoryId;
    private String categoryName;
    private String categoryCount;
    private ArrayList<ItemDetail> itemDetails;



    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(String categoryCount) {
        this.categoryCount = categoryCount;
    }

    public ArrayList<ItemDetail> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(ArrayList<ItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
