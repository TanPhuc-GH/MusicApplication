
package com.hcmute.architecture.data.response.manager;

import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.hcmute.architecture.utils.Utils;


public class NetworkStateManager implements DefaultLifecycleObserver {

  private static final NetworkStateManager S_MANAGER = new NetworkStateManager();
  private final NetworkStateReceive mNetworkStateReceive = new NetworkStateReceive();

  private NetworkStateManager() {
  }

  public static NetworkStateManager getInstance() {
    return S_MANAGER;
  }

  @Override
  public void onResume(@NonNull LifecycleOwner owner) {
    IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    Utils.getApp().getApplicationContext().registerReceiver(mNetworkStateReceive, filter);
  }

  @Override
  public void onPause(@NonNull LifecycleOwner owner) {
    Utils.getApp().getApplicationContext().unregisterReceiver(mNetworkStateReceive);
  }
}
