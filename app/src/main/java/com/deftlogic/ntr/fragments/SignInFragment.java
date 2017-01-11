package com.deftlogic.ntr.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deftlogic.ntr.R;
import com.deftlogic.ntr.backgroundTasks.GetCartAsyncTask;
import com.deftlogic.ntr.backgroundTasks.SignInAsyncTask;
import com.deftlogic.ntr.interfaces.LoginResultsCallback;

import butterknife.ButterKnife;

/**
 * Created by omkardokur on 2/8/16.
 */
public class SignInFragment  extends Fragment implements LoginResultsCallback{
    EditText emailText;
    EditText passwordText;
    Button loginButton;
    private static final String TAG = "SignInFragment";


    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ButterKnife.bind(getActivity());
        (getActivity()).setTitle(Html.fromHtml("Sign In"));
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_signin, container, false);

        loginButton = (Button) view.findViewById(R.id.btn_login);
        emailText = (EditText) view.findViewById(R.id.input_email);
        passwordText = (EditText) view.findViewById(R.id.input_password);
        loginClicked();

        return view;
    }

    private void loginClicked() {
      loginButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              login();
          }
      });
    }


    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new SignInAsyncTask(this,getActivity(), email, password).execute();
        progressDialog.dismiss();
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        Fragment fragment = null;
        fragment = new HomeFragment();
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
          //  (getActivity()).setTitle(Html.fromHtml("Sign In"));
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }

    }

    public void onLoginFailed() {
        Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 100) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    @Override
    public void loginStatus (Boolean status) {
        if(!status){
            onLoginFailed();
        }else{
            onLoginSuccess();
        }

    }
}