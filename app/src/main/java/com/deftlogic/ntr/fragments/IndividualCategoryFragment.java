package com.deftlogic.ntr.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.adapters.ItemAdapter;
import com.deftlogic.ntr.backgroundTasks.SearchItemAsyncTask;
import com.deftlogic.ntr.interfaces.ResultsCallback;
import com.deftlogic.ntr.models.CategoryItem;
import com.deftlogic.ntr.models.ItemDetail;

import java.util.List;

/**
 * Created by omkardokur on 1/16/16.
 */
public class IndividualCategoryFragment extends Fragment implements ResultsCallback {
    int categoryId;
    private RecyclerView mRecyclerView;
    private ItemAdapter adapter;
    Context mContext;
    String title;
    List<ItemDetail> itemDetailList;

    public IndividualCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        (getActivity()).setTitle(Html.fromHtml("<small>"+title+"</small>"));
        mContext = getActivity();
        View view =  inflater.inflate(R.layout.fragment_indivdual_category, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.individualCategoryRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getItemList();
        return view;
    }

    private void getItemList() {
        new SearchItemAsyncTask(this,getActivity(),categoryId).execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryId = getArguments().getInt("id");
        title = getArguments().getString("title");
        Log.d("ICF", String.valueOf(categoryId));

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


    @Override
    public void onSuccess(List<CategoryItem> categoryItemList) {

    }

    @Override
    public void onFailure(String errorMessage) {
        showAlertDialogue(errorMessage);
    }

    @Override
    public void onItemSuccess(List<ItemDetail> itemDetailList) {
        adapter = new ItemAdapter(getActivity(), itemDetailList,categoryId, title);
        mRecyclerView.setAdapter(adapter);
        this.itemDetailList = itemDetailList;

    }
}
