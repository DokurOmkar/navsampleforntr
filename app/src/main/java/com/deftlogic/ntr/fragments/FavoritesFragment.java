package com.deftlogic.ntr.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.adapters.CartAdapter;
import com.deftlogic.ntr.adapters.FavoritesAdapter;
import com.deftlogic.ntr.backgroundTasks.GetCartAsyncTask;
import com.deftlogic.ntr.backgroundTasks.GetFavoritesAsyncTask;
import com.deftlogic.ntr.interfaces.FavoriteResultsCallback;
import com.deftlogic.ntr.models.ItemDetail;

import java.util.List;

/**
 * Created by omkardokur on 1/11/16.
 */
public class FavoritesFragment extends Fragment  implements FavoriteResultsCallback{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private boolean signIn = false;
    private RecyclerView mRecyclerView;
    private FavoritesAdapter favoritesAdapter;
    Context mContext;
    List<ItemDetail> itemDetailList;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        // Inflate the layout for this fragment
        (getActivity()).setTitle(Html.fromHtml("Favorites"));
        sharedPreferences = getActivity().getSharedPreferences("signin", Context.MODE_PRIVATE);
        signIn = sharedPreferences.getBoolean("status", signIn);
        View view;
        if(signIn) {
            view = inflater.inflate(R.layout.fragment_favorites_signedin, container, false);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.favoritesRecyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            getFavoritesList();
        }else{
            view = inflater.inflate(R.layout.fragment_favorites_no_signin, container, false);
        }

        return view;
    }

    private void getFavoritesList() {

        new GetFavoritesAsyncTask(this,getActivity()).execute();
    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onItemSuccess(List<ItemDetail> itemDetailList) {
         Log.d("FF", String.valueOf(itemDetailList));
        favoritesAdapter = new FavoritesAdapter(getActivity(), itemDetailList);
       mRecyclerView.setAdapter(favoritesAdapter);
        this.itemDetailList = itemDetailList;

    }


}
