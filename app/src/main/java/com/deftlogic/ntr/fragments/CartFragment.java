package com.deftlogic.ntr.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.adapters.CartAdapter;
import com.deftlogic.ntr.adapters.ItemAdapter;
import com.deftlogic.ntr.backgroundTasks.GetCartAsyncTask;
import com.deftlogic.ntr.backgroundTasks.SearchItemAsyncTask;
import com.deftlogic.ntr.interfaces.CartResultsCallback;
import com.deftlogic.ntr.models.ItemDetail;

import java.util.List;

/**
 * Created by omkardokur on 1/11/16.
 */
public class CartFragment extends Fragment implements CartResultsCallback {
    private RecyclerView mRecyclerView;
    private CartAdapter adapter;
    Context mContext;
    List<ItemDetail> itemDetailList;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        (getActivity()).setTitle(Html.fromHtml("Cart"));
        View view =  inflater.inflate(R.layout.fragment_indivdual_category, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.individualCategoryRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getCartList();
        return view;
    }

    private void getCartList() {
        new GetCartAsyncTask(this,getActivity()).execute();

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onItemSuccess(List<ItemDetail> itemDetailList) {
        adapter = new CartAdapter(getActivity(), itemDetailList);
        mRecyclerView.setAdapter(adapter);
        this.itemDetailList = itemDetailList;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ( getActivity()).setTitle(Html.fromHtml("Cart"));
    }
}
