package com.deftlogic.ntr.interfaces;

import com.deftlogic.ntr.models.ItemDetail;

import java.util.List;

/**
 * Created by omkardokur on 2/4/16.
 */
public interface CartResultsCallback {

    public void onFailure(String errorMessage);
    public void onItemSuccess(List<ItemDetail> itemDetailList);
}
