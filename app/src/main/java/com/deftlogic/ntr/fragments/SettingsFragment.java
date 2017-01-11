package com.deftlogic.ntr.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.deftlogic.ntr.R;

/**
 * Created by omkardokur on 1/11/16.
 */
public class SettingsFragment extends Fragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private boolean signIn = false;
    Button signInBtn;
    Button contact_noSignIn;
    Button myAccount;
    Button myProjects;
    Button changePassword;
    Button contact;
    Button signOut;


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        (getActivity()).setTitle(Html.fromHtml("Settings"));
        // Inflate the layout for this fragment
        sharedPreferences = getActivity().getSharedPreferences("signin", Context.MODE_PRIVATE);
        signIn = sharedPreferences.getBoolean("status", signIn);

        View view;
        if (signIn) {
            view = inflater.inflate(R.layout.fragment_settings_signin, container, false);
            myAccount = (Button) view.findViewById(R.id.btn_myAccount);
            myProjects = (Button) view.findViewById(R.id.btn_myProjects);
            changePassword = (Button)view.findViewById(R.id.btn_changePassword);
            contact = (Button)view.findViewById(R.id.btn_contact);
            signOut = (Button) view.findViewById(R.id.btn_signOut);

        } else {
            view = inflater.inflate(R.layout.fragment_settings_no_signin, container, false);
            Log.e("Home Sign in", String.valueOf(signIn));
            signInBtn = (Button) view.findViewById(R.id.btn_mySignIn);
            contact_noSignIn = (Button) view.findViewById(R.id.btn_contact_no_signIn);


        }


        myAccountClicked();

        signOutClicked();

        signInBtnClicked();
        return view;
    }

    private void signInBtnClicked() {
        if (!signIn) {
            signInBtn.setOnClickListener(new View.OnClickListener() {
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
                        Log.e("Settings Fragment", "Error in creating fragment");
                    }
                }
            });
        }
    }

    private void signOutClicked() {
        if(signIn) {
            signOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //SharedPreferences.Editor editor = sharedPreferences.edit();
                    // editor.putBoolean("status", false);
                    sharedPreferences.edit().clear().apply();
                    Fragment fragment = null;
                    fragment = new SettingsFragment();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container, fragment).commit();
                        // (getActivity()).setTitle(Html.fromHtml("Sign In"));
                    } else {
                        // error in creating fragment
                        Log.e("Settings Fragment", "Error in creating fragment");
                    }

                }
            });



        }

    }

    private void myAccountClicked() {
        if(signIn) {
            myAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String firstName = sharedPreferences.getString("firstname", null);
                    Toast.makeText(getActivity(), "Hello " + firstName, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}
