package com.deftlogic.ntr.adapters;

/**
 * Created by omkardokur on 1/15/16.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.activities.MainActivity;
import com.deftlogic.ntr.fragments.IndividualCategoryFragment;
import com.deftlogic.ntr.fragments.SearchFragment;
import com.deftlogic.ntr.models.CategoryItem;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CustomViewHolder> implements Filterable {
    private List<CategoryItem> categoryItemList;
    private List<CategoryItem> mCategoryItemList;
    private Context mContext;


    public CategoryAdapter(Context context, List<CategoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
        this.mContext = context;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_search_single_item,null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        CategoryItem categoryItem = categoryItemList.get(i);

        //Setting text view title
        customViewHolder.categoryNameText.setText(Html.fromHtml(categoryItem.getCategoryName()));
        customViewHolder.categoryCountText.setText(Html.fromHtml(categoryItem.getCategoryCount()));
        //Handle click event on both title and image click
        customViewHolder.categoryNameText.setOnClickListener(clickListener);
        customViewHolder.categoryCountText.setOnClickListener(clickListener);

        customViewHolder.categoryNameText.setTag(customViewHolder);
        customViewHolder.categoryCountText.setTag(customViewHolder);


    }

    @Override
    public int getItemCount() {
        return (null != categoryItemList ? categoryItemList.size() : 0);
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                categoryItemList = (ArrayList<CategoryItem>) results.values; // has

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
                List<CategoryItem> FilteredArrList = new ArrayList<CategoryItem>();

                if (mCategoryItemList == null) {
                    mCategoryItemList = new ArrayList<CategoryItem>(categoryItemList); // saves

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
                    results.count = mCategoryItemList.size();
                    results.values = mCategoryItemList;
                } else {
                    Locale locale = Locale.getDefault();
                    constraint = constraint.toString().toLowerCase(locale);
                    for (int i = 0; i < mCategoryItemList.size(); i++) {
                        CategoryItem item = mCategoryItemList.get(i);

                        String data = item.getCategoryName();
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

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView categoryNameText ;
        protected TextView categoryCountText;

        public CustomViewHolder(View view) {
            super(view);

            this.categoryNameText = (TextView) view.findViewById(R.id.categoryNameTextView);
            this.categoryCountText = (TextView) view.findViewById(R.id.categoryCountTextView);


        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CustomViewHolder holder = (CustomViewHolder) view.getTag();
            int position = holder.getPosition();
            CategoryItem categoryItem = categoryItemList.get(position);

            IndividualCategoryFragment fragment = new IndividualCategoryFragment();
            Bundle args = new Bundle();
            args.putInt("id", categoryItem.getCategoryId());
            args.putString("title",categoryItem.getCategoryName());
            Log.e("args", String.valueOf(categoryItem.getCategoryId()));
            fragment.setArguments(args);
            if (fragment != null) {

                ((MainActivity)mContext).getSupportFragmentManager() .beginTransaction()
                        .replace(R.id.frame_container, fragment).addToBackStack( "tag" ).commit();
               // ((MainActivity) mContext).setTitle(Html.fromHtml("<small>"+categoryItem.getCategoryName()+"</small>"));
            } else {
                // error in creating fragment
                Log.e("Search Activity", "Error in creating fragment");
            }
            //Toast.makeText(mContext, categoryItem.getCategoryName(), Toast.LENGTH_SHORT).show();
        }
    };

}
