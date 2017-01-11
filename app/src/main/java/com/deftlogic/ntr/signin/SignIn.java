package com.deftlogic.ntr.signin;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by omkardokur on 1/14/16.
 */
public class SignIn {
    Context mContext;
    private boolean signIn = false;
    public SignIn(Context mContext) {
        this.mContext = mContext;
    }


    public void checkSignIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("signin",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("status", signIn);
        editor.commit();

    }

}
