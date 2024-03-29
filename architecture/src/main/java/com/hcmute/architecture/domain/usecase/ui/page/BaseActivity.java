package com.hcmute.architecture.domain.usecase.ui.page;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hcmute.architecture.data.response.manager.NetworkStateManager;
import com.hcmute.architecture.utils.AdaptScreenUtils;
import com.hcmute.architecture.utils.BarUtils;
import com.hcmute.architecture.utils.ScreenUtils;
import com.hcmute.architecture.BaseApplication;
import com.kunminx.architecture.ui.page.DataBindingActivity;



public abstract class BaseActivity extends DataBindingActivity {

  private ViewModelProvider mActivityProvider;
  private ViewModelProvider mApplicationProvider;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {

    BarUtils.setStatusBarColor(this, Color.TRANSPARENT);
    BarUtils.setStatusBarLightMode(this, true);

    super.onCreate(savedInstanceState);

    getLifecycle().addObserver(NetworkStateManager.getInstance());

  }

  protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
    if (mActivityProvider == null) {
      mActivityProvider = new ViewModelProvider(this);
    }
    return mActivityProvider.get(modelClass);
  }

  protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
    if (mApplicationProvider == null) {
      mApplicationProvider = new ViewModelProvider((BaseApplication) this.getApplicationContext());
    }
    return mApplicationProvider.get(modelClass);
  }

  @Override
  public Resources getResources() {
    if (ScreenUtils.isPortrait()) {
      return AdaptScreenUtils.adaptWidth(super.getResources(), 360);
    } else {
      return AdaptScreenUtils.adaptHeight(super.getResources(), 640);
    }
  }

  protected void toggleSoftInput() {
    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
  }

  protected void openUrlInBrowser(String url) {
    Uri uri = Uri.parse(url);
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    startActivity(intent);
  }

}
