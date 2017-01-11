package com.deftlogic.ntr.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.adapters.CategoryAdapter;
import com.deftlogic.ntr.backgroundTasks.SearchAsyncTask;
import com.deftlogic.ntr.connections.ConnectionDetector;
import com.deftlogic.ntr.connections.HttpPost;
import com.deftlogic.ntr.interfaces.ResultsCallback;
import com.deftlogic.ntr.models.CategoryItem;
import com.deftlogic.ntr.models.ItemDetail;

import java.util.List;

/**
 * Created by omkardokur on 1/11/16.
 */
public class SearchFragment extends Fragment implements ResultsCallback {
    private RecyclerView mRecyclerView;
    private CategoryAdapter adapter;
    EditText categorySearch;
    Context mContext;
    List<CategoryItem> categoryItemList;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        (getActivity()).setTitle(Html.fromHtml("Search"));
        // Inflate the layout for this fragment
        checkInventory();
        mContext = getActivity();
        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.category_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        categorySearch = (EditText) view.findViewById(R.id.categoryInputSearch);
        getCategoryList();
        enableSearch();
        return view;
    }

    private void enableSearch() {
        /**
         * Enabling Search Filter
         * */
        categorySearch.addTextChangedListener(new TextWatcher() {
           // ArrayAdapter<CategoryAdapter> arrayAdapter =

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                SearchFragment.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void getCategoryList() {
        new SearchAsyncTask(this,getActivity()).execute();
    }

    @Override
    public void onSuccess(List<CategoryItem> categoryItemList) {
        adapter = new CategoryAdapter(getActivity(), categoryItemList);
        mRecyclerView.setAdapter(adapter);
        this.categoryItemList = categoryItemList;
    }

    @Override
    public void onFailure(String errorMessage) {
        showAlertDialogue(errorMessage);

    }

    @Override
    public void onItemSuccess(List<ItemDetail> itemDetailList) {

    }

    private void showAlertDialogue(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setTitle(R.string.dialog_title);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(MainActivity.this, "You clicked yes button", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void checkInventory(){
    }

}
