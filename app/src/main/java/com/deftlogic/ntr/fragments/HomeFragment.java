package com.deftlogic.ntr.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.deftlogic.ntr.R;

/**
 * Created by omkardokur on 1/11/16.
 */
public class HomeFragment extends Fragment {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private boolean signIn = false;
    Button reserveEquipment;
    Button signInHome;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        (getActivity()).setTitle(Html.fromHtml("Home"));
        // Inflate the layout for this fragment
        sharedPreferences = getActivity().getSharedPreferences("signin", Context.MODE_PRIVATE);
        signIn = sharedPreferences.getBoolean("status", signIn);

        View view;
        if(signIn) {
            view = inflater.inflate(R.layout.fragment_home_signedin, container, false);
        }else{
            view = inflater.inflate(R.layout.fragment_home_no_signin, container, false);
            Log.e("Home Sign in",String.valueOf(signIn));
            signInHome = (Button) view.findViewById(R.id.signInButtonHome);
        }

        reserveEquipment = (Button) view.findViewById(R.id.reserveEquipmentButton);


        reserveEquipmentClicked();
        signInHomeClicked();
        return view;
    }

    private void signInHomeClicked() {

        if (!signIn) {
            signInHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = null;
                    fragment = new SignInFragment();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container, fragment).addToBackStack("tag").commit();
                        (getActivity()).setTitle(Html.fromHtml("Sign In"));
                    } else {
                        // error in creating fragment
                        Log.e("MainActivity", "Error in creating fragment");
                    }
                }
            });
        }
    }

    private void reserveEquipmentClicked() {
        reserveEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new SearchFragment();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment).addToBackStack("tag").commit();
                    (getActivity()).setTitle(Html.fromHtml("Search"));
                } else {
                    // error in creating fragment
                    Log.e("MainActivity", "Error in creating fragment");
                }
            }
        });
    }
}

