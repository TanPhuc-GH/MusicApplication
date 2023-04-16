package com.hcmute.architecture.data.response.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.hcmute.architecture.utils.NetworkUtils;

import java.util.Objects;


public class NetworkStateReceive extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    if (Objects.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {
      if (!NetworkUtils.isConnected()) {
        Toast.makeText(context, "Network not good", Toast.LENGTH_SHORT).show();
      }
    }
  }

}
