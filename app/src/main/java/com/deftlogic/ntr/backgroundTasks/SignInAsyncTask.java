package com.deftlogic.ntr.backgroundTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.deftlogic.ntr.connections.HttpPost;
import com.deftlogic.ntr.interfaces.LoginResultsCallback;
import com.deftlogic.ntr.models.LoginJsonResponse;
import com.deftlogic.ntr.utils.CommonUtils;
import com.google.gson.Gson;

import java.util.List;


/**
 * Created by omkardokur on 2/9/16.
 */
public class SignInAsyncTask extends AsyncTask<Void,Void,Boolean> {
    Context sContext;
    String sEmail;
    String sPassword;
    LoginJsonResponse loginJsonResponse;
    Gson gson;
    String responseStr;
    int uniqueSessionId;
    private LoginResultsCallback loginResultsCallback;
    private boolean aLoginStatus;

    private String CUSTOMER_ID = "customer_id";
    private String FIRST_NAME = "firstname";
    private String LAST_NAME = "lastname";
    private String PHONE = "phone";
    private String EMAIL = "email";
    private String IMAGE_URL = "image_url";
    private String ADDRESS_ID = "address_id";
    private String ADDRESS_TYPE = "address_type";
    private String ADDRESS1 = "address1";
    private String ADDRESS2 = "address2";
    private String CITY = "city";
    private String STATE = "state";
    private String ZIP = "zip";

    public SignInAsyncTask(LoginResultsCallback loginResultsCallback, Context context, String email, String password) {
        this.sContext = context;
        this.sEmail = email;
        this.sPassword = password;
        this.loginResultsCallback = loginResultsCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        SharedPreferences sPreferences = sContext.getSharedPreferences("uSession",Context.MODE_PRIVATE);
        uniqueSessionId = sPreferences.getInt("sessionId", 0);

       // Log.e("sessionId", String.valueOf(uniqueSessionId));
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String[] headerName = {CommonUtils.RR_API_KEY_NAME,"username","password", "RR-SESSION-ID"};
        String[] headerValue = {CommonUtils.RR_API_KEY_VALUE,sEmail,sPassword, String.valueOf(uniqueSessionId)};
        String[] paramName = {"0"};
        String[] paramValue = {"0"};
        HttpPost httpPostNew = new HttpPost();
        try {
            responseStr = httpPostNew.httpPost(CommonUtils.LOGIN_URL,paramName,paramValue,headerName,headerValue);
            Log.e("value", responseStr);
            if(responseStr != null) {
                gson = new Gson();
                loginJsonResponse = gson.fromJson(responseStr, LoginJsonResponse.class);
                Log.e("Name", loginJsonResponse.getFirstname());
                List<LoginJsonResponse.AddressesEntity> list = loginJsonResponse.getAddresses();
                Log.e("Name", list.get(0).getAddress1().toString());

                SharedPreferences sharedPreferences = sContext.getSharedPreferences("signin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("status", true);
                editor.putInt(CUSTOMER_ID, loginJsonResponse.getCustomer_id());
                editor.putString(FIRST_NAME, loginJsonResponse.getFirstname());
                editor.putString(LAST_NAME, loginJsonResponse.getLastname());
                editor.putLong(PHONE, loginJsonResponse.getPhone());
                editor.putString(EMAIL, loginJsonResponse.getEmail());
                // editor.put(IMAGE_URL, loginJsonResponse.getImage_url());
                editor.putInt(ADDRESS_ID, list.get(0).getAddress_id());
                editor.putString(ADDRESS_TYPE, list.get(0).getAddress_type());
                editor.putString(ADDRESS1, list.get(0).getAddress1());
                editor.putString(ADDRESS2, list.get(0).getAddress2());
                editor.putString(CITY, list.get(0).getCity());
                editor.putString(STATE, list.get(0).getState());
                editor.putInt(ZIP, list.get(0).getZip());
                editor.commit();
                aLoginStatus = true;
            }else {
                aLoginStatus = false;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return aLoginStatus;

      }

    @Override
    protected void onPostExecute(Boolean loginStatus) {
        super.onPostExecute(loginStatus);
        loginResultsCallback.loginStatus(loginStatus);

    }
}
