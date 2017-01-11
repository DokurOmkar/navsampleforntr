package com.deftlogic.ntr.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.activities.MainActivity;
import com.deftlogic.ntr.adapters.FavoritesAdapter;
import com.deftlogic.ntr.backgroundTasks.AddToCartAsyncTask;
import com.deftlogic.ntr.backgroundTasks.AddToFavoritesAsyncTask;
import com.deftlogic.ntr.backgroundTasks.RemoveFromCartAsyncTask;
import com.deftlogic.ntr.backgroundTasks.RemoveFromFavoritesAsyncTask;
import com.deftlogic.ntr.backgroundTasks.UpdateCartAsyncTask;
import com.deftlogic.ntr.models.ItemDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by omkardokur on 2/2/16.
 */
public class IndividualDetailFragment extends Fragment {
    ItemDetail itemDetail;
    ImageView itemImageview;
    TextView itemNameText;
    TextView itemMakeText;
    TextView itemModelText;
    TextView itemNameTitleText;
    TextView itemFourHoursPriceText;
    TextView itemDailyPriceText;
    TextView itemWeeklyPriceText;
    TextView itemMonthlyPriceText;
    EditText itemQuantityEText;
    Button   itemAddToCartButton;
    Button   itemRemoveFromCartButton;
    Button itemUpdateCartButton;
    int itemId;
    int quantity;
    int categoryId;
    String fragSelection;
    String mTitle;
    int favoriteId;
    SharedPreferences sharedPreferences;
    private boolean signIn = false;
    public IndividualDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_individual_detail, container, false);
        itemImageview= (ImageView) view.findViewById(R.id.itemImage);
        itemNameText = (TextView) view.findViewById(R.id.itemName);
        itemMakeText = (TextView) view.findViewById(R.id.itemMake);
        itemModelText = (TextView) view.findViewById(R.id.itemModel);
        itemNameTitleText = (TextView) view.findViewById(R.id.itemNameTitle);
        itemFourHoursPriceText = (TextView)view.findViewById(R.id.itemfourHoursPrice);
        itemDailyPriceText = (TextView)view.findViewById(R.id.itemDailyPrice);
        itemWeeklyPriceText = (TextView)view.findViewById(R.id.itemWeeklyPrice);
        itemMonthlyPriceText = (TextView)view.findViewById(R.id.itemMonthlyPrice);
        itemQuantityEText = (EditText) view.findViewById(R.id.itemQuantity);
        itemAddToCartButton = (Button) view.findViewById(R.id.itemAddToCart);
        itemRemoveFromCartButton = (Button) view.findViewById(R.id.itemRemoveFromCart);
        itemUpdateCartButton = (Button) view.findViewById(R.id.itemUpdateCart);


        String imageUrl = itemDetail.getIteImageLink();
        String itemName = itemDetail.getItemName();
        String itemMake = itemDetail.getItemMake();
        String itemModel = itemDetail.getItemModel();
        String itemFourHoursPrice = "$"+String.valueOf(itemDetail.getItemPrice_4_Hours());
        String itemDailyPrice = "$"+String.valueOf(itemDetail.getItemPrice_1_Day());
        String itemWeeklyPrice = "$"+String.valueOf(itemDetail.getItemPrice_1_Week());
        String itemMonthlyPrice = "$"+String.valueOf(itemDetail.getItemPrice_1_Month());
        int itemCartCount = itemDetail.getItemCartCount();

        if(itemCartCount>0){
            itemQuantityEText.setText(String.valueOf(itemCartCount));
            itemQuantityEText.setText(String.valueOf(itemCartCount));
        }else{
            itemUpdateCartButton.setVisibility(View.GONE);
            itemRemoveFromCartButton.setVisibility(View.GONE);
            itemQuantityEText.setText(String.valueOf("1"));
        }

        Picasso.with(getActivity()).load(imageUrl).into(itemImageview);
        itemNameText.setText(itemName);
        itemMakeText.setText(itemMake);
        itemModelText.setText(itemModel);
        itemNameTitleText.setText(itemName);
        itemFourHoursPriceText.setText(itemFourHoursPrice);
        itemDailyPriceText.setText(itemDailyPrice);
        itemWeeklyPriceText.setText(itemWeeklyPrice);
        itemMonthlyPriceText.setText(itemMonthlyPrice);

        sharedPreferences = getActivity().getSharedPreferences("signin", Context.MODE_PRIVATE);
        signIn = sharedPreferences.getBoolean("status", signIn);
        addtoCart();
        updateCart();
        removeCart();
        return view;
    }


    private void addtoCart() {
        itemAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantity = Integer.parseInt(itemQuantityEText.getText().toString());
                itemId = itemDetail.getItemId();
                Log.e("Add", "Clicked on AddtoCart" + String.valueOf(quantity));
                ArrayList<Integer> addData = new ArrayList<Integer>();
                addData.add(itemId);
                addData.add(quantity);
                new AddToCartAsyncTask(getActivity()).execute(addData);

                if(itemDetail.getItemId()!= favoriteId && signIn) {
                    new AddToFavoritesAsyncTask(getActivity(), itemDetail.getItemId()).execute();
                }
                if(fragSelection == "CartFragment"){
                    gotoCartList();
                }else if  (fragSelection =="IndividualCategoryFragment"){
                    gotoItemsList();
                }else if (fragSelection == "FavoritesFragment") {
                    gotoFavoritesList();
                }
            }
        });
    }



    private void updateCart() {
        itemUpdateCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Update", "Clicked on Update");
                quantity = Integer.parseInt(itemQuantityEText.getText().toString());
                itemId = itemDetail.getItemId();
                ArrayList<Integer> updateData = new ArrayList<Integer>();
                updateData.add(itemId);
                updateData.add(quantity);
                new UpdateCartAsyncTask(getActivity()).execute(updateData);
                if(itemDetail.getItemId()!= favoriteId && signIn) {
                    new AddToFavoritesAsyncTask(getActivity(), itemDetail.getItemId()).execute();
                }
                if(fragSelection == "CartFragment"){
                    gotoCartList();
                }else if  (fragSelection =="IndividualCategoryFragment"){
                    gotoItemsList();
                }else if (fragSelection == "FavoritesFragment") {
                    gotoFavoritesList();
                }

            }
        });
    }

    private void removeCart() {
        itemRemoveFromCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Remove", "Clicked on Remove");
                Log.e("Update", "Clicked on Update");
                quantity = Integer.parseInt(itemQuantityEText.getText().toString());
                itemId = itemDetail.getItemId();
                ArrayList<Integer> removeData = new ArrayList<Integer>();
                removeData.add(itemId);
                removeData.add(quantity);
                new RemoveFromCartAsyncTask(getActivity()).execute(removeData);

                if (fragSelection == "CartFragment") {
                    gotoCartList();
                } else if (fragSelection == "IndividualCategoryFragment") {
                    gotoItemsList();
                } else if (fragSelection == "FavoritesFragment") {
                    gotoFavoritesList();
                }
            }
        });
    }

    private void gotoFavoritesList() {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        if (fragment != null) {
            (getActivity()).getSupportFragmentManager() .beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

        } else {
            // error in creating fragment
            Log.e("Search Activity", "Error in creating fragment");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemDetail = (ItemDetail) getArguments().getSerializable("itemDetail");
        categoryId = getArguments().getInt("id");
        mTitle = getArguments().getString("title");
        fragSelection = getArguments().getString("fragment");
        favoriteId = getArguments().getInt("favoriteID");
        Log.d("IDF", String.valueOf(categoryId));
    }

    private void gotoItemsList() {
        IndividualCategoryFragment fragment = new IndividualCategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable("itemDetail", itemDetail);
        args.putInt("id", categoryId);
        args.putString("title", mTitle);
        Log.e("args", String.valueOf(itemDetail.toString()));
        fragment.setArguments(args);
        if (fragment != null) {
            (getActivity()).getSupportFragmentManager() .beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        } else {
            // error in creating fragment
            Log.e("Search Activity", "Error in creating fragment");
        }
    }

    private void gotoCartList() {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        if (fragment != null) {
            (getActivity()).getSupportFragmentManager() .beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

        } else {
            // error in creating fragment
            Log.e("Search Activity", "Error in creating fragment");
        }
    }
}
