package com.tangtuongco.chamcong.Ulty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);

        Toasty.info(context, status, Toast.LENGTH_SHORT).show();
    }
}
