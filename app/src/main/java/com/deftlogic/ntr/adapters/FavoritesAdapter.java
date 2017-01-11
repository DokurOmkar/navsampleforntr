package com.deftlogic.ntr.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.activities.MainActivity;
import com.deftlogic.ntr.backgroundTasks.RemoveFromFavoritesAsyncTask;
import com.deftlogic.ntr.fragments.IndividualDetailFragment;
import com.deftlogic.ntr.models.ItemDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by omkardokur on 2/4/16.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.CustomViewHolder> {
    private List<ItemDetail> itemDetailList;
    private Context mContext;


    public FavoritesAdapter(Context context, List<ItemDetail> itemDetailList) {
        this.itemDetailList = itemDetailList;
        this.mContext = context;
        Log.d("FF", String.valueOf(itemDetailList));

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_favorites_signedin_single_row, null);
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
                .into(customViewHolder.favoriteItemImageView);

        String prices = "$"+ String.valueOf(itemDetail.getItemPrice_4_Hours())+ "/$" + String.valueOf(itemDetail.getItemPrice_1_Week()) + "/$" +
                String.valueOf(itemDetail.getItemPrice_1_Month()) + "/$" + String.valueOf(itemDetail.getItemPrice_1_Month());

        customViewHolder.favoriteItemTitle.setText(itemDetail.getItemName());
        customViewHolder.favoriteItemTitle.setOnClickListener(clickListener);
        customViewHolder.favoriteItemTitle.setTag(customViewHolder);

        customViewHolder.favoriteItemAllPrices.setText(prices);
        customViewHolder.favoriteItemAllPrices.setOnClickListener(clickListener);
        customViewHolder.favoriteItemAllPrices.setTag(customViewHolder);

        if(itemDetail.getItemId()==itemDetail.getItemFavoritesId() ){
            customViewHolder.favoriteItem.setChecked(true);
        }else {
            customViewHolder.favoriteItem.setChecked(false);
        }

        customViewHolder.favoriteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!customViewHolder.favoriteItem.isChecked()){

                    System.out.println("Un-Checked");
                    itemDetailList.remove(customViewHolder.getAdapterPosition());
                    notifyItemRemoved(customViewHolder.getAdapterPosition());
                    new RemoveFromFavoritesAsyncTask(mContext,itemDetail.getItemId()).execute();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != itemDetailList ? itemDetailList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView favoriteItemImageView;
        protected TextView favoriteItemTitle;
        protected TextView favoriteItemAllPrices;
        protected CheckBox favoriteItem;


        public CustomViewHolder(View view) {
            super(view);
            this.favoriteItemImageView = (ImageView) view.findViewById(R.id.favoriteItemThumbnail);
            this.favoriteItemTitle = (TextView) view.findViewById(R.id.favoriteItemName);
            this.favoriteItemAllPrices = (TextView) view.findViewById(R.id.favoriteItemPrices);
            this.favoriteItem = (CheckBox) view.findViewById(R.id.favoriteItemCb);

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
            args.putString("fragment", "FavoritesFragment" );
            Log.e("args", String.valueOf(itemDetail.toString()));
            fragment.setArguments(args);
            if (fragment != null) {
                ((MainActivity)mContext).getSupportFragmentManager() .beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack( "items" ).commit();
                ((MainActivity) mContext).setTitle(Html.fromHtml(  "Equipment" ));
            } else {
                // error in creating fragment
                Log.e("Search Activity", "Error in creating fragment");
            }
        }
    };

}
