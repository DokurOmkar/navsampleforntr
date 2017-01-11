package com.deftlogic.ntr.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by omkardokur on 1/31/16.
 */
public class FieldAssistAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private FieldAssistAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new FieldAssistAuthenticator(this);
    }

    /*
    * When the system binds to this Service to make the RPC call
    * return the authenticator's IBinder.
    */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }

}
