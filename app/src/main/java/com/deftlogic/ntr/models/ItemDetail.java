package com.deftlogic.ntr.models;

import java.io.Serializable;

/**
 * Created by omkardokur on 1/18/16.
 */
public class ItemDetail implements Serializable{
    int itemId;
    String itemName;
    int itemCategoryId;
    String iteImageLink;
    String itemMake;
    String itemModel;
    int itemPrice_4_Hours;
    int itemPrice_1_Day;
    int itemPrice_1_Week;
    int itemPrice_1_Month;
    int itemCartCount;
    int itemFavoritesId;




    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(int itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public String getIteImageLink() {
        return iteImageLink;
    }

    public void setIteImageLink(String iteImageLink) {
        this.iteImageLink = "https://www.myrelirental.com/00/inventory/images/"+iteImageLink;
    }

    public String getItemMake() {
        return itemMake;
    }

    public void setItemMake(String itemMake) {
        this.itemMake = itemMake;
    }

    public String getItemModel() {
        return itemModel;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }

    public int getItemPrice_4_Hours() {
        return itemPrice_4_Hours;
    }

    public void setItemPrice_4_Hours(int itemPrice_4_Hours) {
        this.itemPrice_4_Hours = itemPrice_4_Hours;
    }

    public int getItemPrice_1_Day() {
        return itemPrice_1_Day;
    }

    public void setItemPrice_1_Day(int itemPrice_1_Day) {
        this.itemPrice_1_Day = itemPrice_1_Day;
    }

    public int getItemPrice_1_Week() {
        return itemPrice_1_Week;
    }

    public void setItemPrice_1_Week(int itemPrice_1_Week) {
        this.itemPrice_1_Week = itemPrice_1_Week;
    }

    public int getItemPrice_1_Month() {
        return itemPrice_1_Month;
    }

    public void setItemPrice_1_Month(int itemPrice_1_Month) {
        this.itemPrice_1_Month = itemPrice_1_Month;
    }

    public int getItemCartCount() {
        return itemCartCount;
    }

    public void setItemCartCount(int itemCartCount) {
        this.itemCartCount = itemCartCount;
    }

    public int getItemFavoritesId() {
        return itemFavoritesId;
    }

    public void setItemFavoritesId(int itemFavoritesId) {
        this.itemFavoritesId = itemFavoritesId;
    }


}
