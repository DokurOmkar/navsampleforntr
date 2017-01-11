package com.deftlogic.ntr.backgroundTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.deftlogic.ntr.connections.HttpPost;
import com.deftlogic.ntr.models.LoginJsonResponse;
import com.deftlogic.ntr.models.SessionIdResponse;
import com.deftlogic.ntr.utils.CommonUtils;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by omkardokur on 2/10/16.
 */
public class GetSessionIdAsyncTask extends AsyncTask<Void,Void,Void> {
    Context sContext;
    Gson gson;
    String responseStr;
    SessionIdResponse sessionIdResponse;
    public GetSessionIdAsyncTask(Context sContext) {
        this.sContext = sContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {
        String[] headerName = {CommonUtils.RR_API_KEY_NAME};
        String[] headerValue = {CommonUtils.RR_API_KEY_VALUE};
        String[] paramName = {"0"};
        String[] paramValue = {"0"};
        HttpPost httpPostNew = new HttpPost();
        try {
            responseStr = httpPostNew.httpPost(CommonUtils.SESSION_ID_URL,paramName,paramValue,headerName,headerValue);
            Log.e("value", responseStr);
            gson = new Gson();
            sessionIdResponse = gson.fromJson(responseStr, SessionIdResponse.class);
            SharedPreferences sharedPreferences = sContext.getSharedPreferences("uSession",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("sessionId", sessionIdResponse.getSessionId());
            editor.putBoolean("status", true);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
