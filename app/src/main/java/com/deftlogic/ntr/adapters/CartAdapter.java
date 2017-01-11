package com.deftlogic.ntr.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.activities.MainActivity;
import com.deftlogic.ntr.fragments.IndividualDetailFragment;
import com.deftlogic.ntr.models.ItemDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by omkardokur on 2/4/16.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CustomViewHolder> {
    private List<ItemDetail> itemDetailList;
    private Context mContext;
    public CartAdapter(Context context, List<ItemDetail> itemDetailList) {
        this.itemDetailList = itemDetailList;
        this.mContext = context;


    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_cart_single_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        ItemDetail itemDetail = itemDetailList.get(i);

        //Download image using picasso library
        Picasso.with(mContext).load(itemDetail.getIteImageLink())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(customViewHolder.cartItemImageView);

        String prices = "$"+ String.valueOf(itemDetail.getItemPrice_4_Hours())+ "/$" + String.valueOf(itemDetail.getItemPrice_1_Week()) + "/$" +
                String.valueOf(itemDetail.getItemPrice_1_Month()) + "/$" + String.valueOf(itemDetail.getItemPrice_1_Month());

        int cartCount = itemDetail.getItemCartCount();

        customViewHolder.cartItemTitle.setText(itemDetail.getItemName());
        customViewHolder.cartItemTitle.setOnClickListener(clickListener);
        customViewHolder.cartItemTitle.setTag(customViewHolder);

        customViewHolder.cartItemAllPrices.setText(prices);
        customViewHolder.cartItemAllPrices.setOnClickListener(clickListener);
        customViewHolder.cartItemAllPrices.setTag(customViewHolder);

        customViewHolder.cartItemCartCount.setText(String.valueOf(cartCount));
        customViewHolder.cartItemCartCount.setOnClickListener(clickListener);
        customViewHolder.cartItemCartCount.setTag(customViewHolder);

    }

    @Override
    public int getItemCount() {
        return (null != itemDetailList ? itemDetailList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView cartItemImageView;
        protected TextView cartItemTitle;
        protected TextView cartItemAllPrices;
        protected TextView cartItemCartCount;

        public CustomViewHolder(View view) {
            super(view);
            this.cartItemImageView = (ImageView) view.findViewById(R.id.cartItemThumbnail);
            this.cartItemTitle = (TextView) view.findViewById(R.id.cartItemName);
            this.cartItemAllPrices = (TextView) view.findViewById(R.id.cartItemPrices);
            this.cartItemCartCount = (TextView) view.findViewById(R.id.cartItemCount);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CustomViewHolder holder = (CustomViewHolder) view.getTag();
            int position = holder.getPosition();
            ItemDetail itemDetail = itemDetailList.get(position);
            IndividualDetailFragment fragment = new IndividualDetailFragment();
            Bundle args = new Bundle();
            int mCategoryId = itemDetail.getItemCategoryId();
            args.putSerializable("itemDetail", itemDetail);
            args.putInt("id", mCategoryId);
            args.putString("fragment", "CartFragment");
            Log.e("args", String.valueOf(itemDetail.toString()));
            fragment.setArguments(args);
            if (fragment != null) {
                ((MainActivity)mContext).getSupportFragmentManager() .beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack( "items" ).commit();
                ((MainActivity) mContext).setTitle(Html.fromHtml("Equipment"));
            } else {
                // error in creating fragment
                Log.e("Search Activity", "Error in creating fragment");
            }
        }
    };
}
