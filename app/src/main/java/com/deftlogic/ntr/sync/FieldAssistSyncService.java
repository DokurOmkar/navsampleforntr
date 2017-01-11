package com.deftlogic.ntr.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by omkardokur on 1/31/16.
 */
public class FieldAssistSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static FieldAssistSyncAdapter nFASyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("SunshineSyncService", "onCreate - SunshineSyncService");
        synchronized (sSyncAdapterLock) {
            if (nFASyncAdapter == null) {
                nFASyncAdapter = new FieldAssistSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return nFASyncAdapter.getSyncAdapterBinder();
    }
}
