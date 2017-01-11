package com.deftlogic.ntr.interfaces;

import com.deftlogic.ntr.models.CategoryItem;
import com.deftlogic.ntr.models.ItemDetail;

import java.util.List;

/**
 * Created by omkardokur on 1/15/16.
 */
public interface ResultsCallback {
    public void onSuccess(List<CategoryItem> categoryItemList);
    public void onFailure(String errorMessage);
    public void onItemSuccess(List<ItemDetail> itemDetailList);
}
