package com.deftlogic.ntr.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.activities.MainActivity;
import com.deftlogic.ntr.backgroundTasks.AddToFavoritesAsyncTask;
import com.deftlogic.ntr.backgroundTasks.RemoveFromFavoritesAsyncTask;
import com.deftlogic.ntr.fragments.IndividualDetailFragment;
import com.deftlogic.ntr.models.ItemDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by omkardokur on 2/2/16.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.CustomViewHolder> implements Filterable {
    private static final int FAV_SCREEN = 0;
    private static final int NO_FAV_SCREEN = 1;
    private List<ItemDetail> itemDetailList;
    private List<ItemDetail> mItemDetailList;
    private Context mContext;
    String  mTitle;
    int mCategoryId;
    SharedPreferences sharedPreferences;
    private boolean signIn = false;

    public ItemAdapter(Context context, List<ItemDetail> itemDetailList, int categoryId, String title) {
        this.itemDetailList = itemDetailList;
        this.mContext = context;
        this.mCategoryId = categoryId;
        this.mTitle = title;
        sharedPreferences = mContext.getSharedPreferences("signin", Context.MODE_PRIVATE);
        signIn = sharedPreferences.getBoolean("status", signIn);
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_individual_signed_single_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final ItemDetail itemDetail = itemDetailList.get(i);


        //Download image using picasso library
        Picasso.with(mContext).load(itemDetail.getIteImageLink())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(customViewHolder.itemImageView);

        String prices = "$"+ String.valueOf(itemDetail.getItemPrice_4_Hours())+ "/$" + String.valueOf(itemDetail.getItemPrice_1_Week()) + "/$" +
                String.valueOf(itemDetail.getItemPrice_1_Month()) + "/$" + String.valueOf(itemDetail.getItemPrice_1_Month());

        int cartCount = itemDetail.getItemCartCount();
        String cartString;
        if(cartCount>0) {
            cartString = "in Cart " + "(" + String.valueOf(cartCount) + ")";
        }
        else{
            cartString = "";
        }

        customViewHolder.itemTitle.setText(itemDetail.getItemName());
        customViewHolder.itemTitle.setOnClickListener(clickListener);
        customViewHolder.itemTitle.setTag(customViewHolder);

        customViewHolder.itemAllPrices.setText(prices);
        customViewHolder.itemAllPrices.setOnClickListener(clickListener);
        customViewHolder.itemAllPrices.setTag(customViewHolder);

        customViewHolder.itemCartCount.setText(cartString);
        customViewHolder.itemCartCount.setOnClickListener(clickListener);
        customViewHolder.itemCartCount.setTag(customViewHolder);



        if(signIn){
            Log.e("Log", "In");
            if(itemDetail.getItemId()==itemDetail.getItemFavoritesId() ){
                customViewHolder.itemFavorite.setChecked(true);
            }else {
                customViewHolder.itemFavorite.setChecked(false);
            }

            customViewHolder.itemFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (customViewHolder.itemFavorite.isChecked()) {
                        System.out.println("Checked");
                        new AddToFavoritesAsyncTask(mContext, itemDetail.getItemId()).execute();
                    } else {
                        System.out.println("Un-Checked");
                        new RemoveFromFavoritesAsyncTask(mContext, itemDetail.getItemId()).execute();
                    }
                }
            });

        }else {
            customViewHolder.itemFavorite.setVisibility(View.GONE);
            Log.e("Log", "Out");


        }
    }

    @Override
    public int getItemCount() {
        return (null != itemDetailList ? itemDetailList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView itemImageView;
        protected TextView itemTitle;
        protected TextView itemAllPrices;
        protected TextView itemCartCount;
        protected CheckBox itemFavorite;

        public CustomViewHolder(View view) {
            super(view);
            this.itemImageView = (ImageView) view.findViewById(R.id.itemThumbnail);
            this.itemTitle = (TextView) view.findViewById(R.id.itemName);
            this.itemAllPrices = (TextView) view.findViewById(R.id.itemPrices);
            this.itemCartCount = (TextView) view.findViewById(R.id.itemCartCount);
            this.itemFavorite = (CheckBox) view.findViewById(R.id.itemFavoriteCb);
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
            args.putSerializable("itemDetail", itemDetail);
            args.putInt("id", mCategoryId);
            args.putString("title", mTitle);
            args.putInt("favoriteID",itemDetail.getItemFavoritesId() );
            args.putString("fragment", "IndividualCategoryFragment" );
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


    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                itemDetailList = (ArrayList<ItemDetail>) results.values; // has

                notifyDataSetChanged();
            }
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults(); // Holds the
                // results of a
                // filtering
                // operation in
                // values
                // List<String> FilteredArrList = new ArrayList<String>();
                List<ItemDetail> FilteredArrList = new ArrayList<ItemDetail>();

                if (mItemDetailList == null) {
                    mItemDetailList = new ArrayList<ItemDetail>(itemDetailList); // saves

                }

                /********
                 *
                 * If constraint(CharSequence that is received) is null returns
                 * the mOriginalValues(Original) values else does the Filtering
                 * and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mItemDetailList.size();
                    results.values = mItemDetailList;
                } else {
                    Locale locale = Locale.getDefault();
                    constraint = constraint.toString().toLowerCase(locale);
                    for (int i = 0; i < mItemDetailList.size(); i++) {
                        ItemDetail item = mItemDetailList.get(i);

                        String data = item.getItemName();
                        if (data.toLowerCase(locale).contains(constraint.toString())) {

                            FilteredArrList.add(item);
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;

                }
                return results;
            }
        };
        return filter;
    }

}
