package com.hcmute.architecture.utils;

import static android.Manifest.permission.WRITE_SETTINGS;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;


public final class ScreenUtils {

  private ScreenUtils() {
    throw new UnsupportedOperationException("u can't instantiate me...");
  }


  public static int getScreenWidth() {
    WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
    Point point = new Point();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      wm.getDefaultDisplay().getRealSize(point);
    }
    return point.x;
  }

  public static int getScreenHeight() {
    WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
    Point point = new Point();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      wm.getDefaultDisplay().getRealSize(point);
    }
    return point.y;
  }

  public static float getScreenDensity() {
    return Resources.getSystem().getDisplayMetrics().density;
  }


  public static int getScreenDensityDpi() {
    return Resources.getSystem().getDisplayMetrics().densityDpi;
  }


  public static void setFullScreen(@NonNull final AppCompatActivity activity) {
    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
  }


  public static void setNonFullScreen(@NonNull final AppCompatActivity activity) {
    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
  }


  public static void toggleFullScreen(@NonNull final AppCompatActivity activity) {
    int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
    Window window = activity.getWindow();
    if ((window.getAttributes().flags & fullScreenFlag) == fullScreenFlag) {
      window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
              | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    } else {
      window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
              | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
  }

  public static boolean isFullScreen(@NonNull final AppCompatActivity activity) {
    int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
    return (activity.getWindow().getAttributes().flags & fullScreenFlag) == fullScreenFlag;
  }


  public static boolean isLandscape() {
    return Utils.getApp().getResources().getConfiguration().orientation
            == Configuration.ORIENTATION_LANDSCAPE;
  }

  public static void setLandscape(@NonNull final AppCompatActivity activity) {
    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
  }

  public static boolean isPortrait() {
    return Utils.getApp().getResources().getConfiguration().orientation
            == Configuration.ORIENTATION_PORTRAIT;
  }


  public static void setPortrait(@NonNull final AppCompatActivity activity) {
    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  }

  @SuppressLint("SwitchIntDef")
  public static int getScreenRotation(@NonNull final AppCompatActivity activity) {
    switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
      case Surface.ROTATION_90:
        return 90;
      case Surface.ROTATION_180:
        return 180;
      case Surface.ROTATION_270:
        return 270;
      default:
        return 0;
    }
  }

  public static Bitmap screenShot(@NonNull final AppCompatActivity activity) {
    return screenShot(activity, false);
  }


  public static Bitmap screenShot(@NonNull final AppCompatActivity activity, boolean isDeleteStatusBar) {
    View decorView = activity.getWindow().getDecorView();
    decorView.setDrawingCacheEnabled(true);
    decorView.setWillNotCacheDrawing(false);
    Bitmap bmp = decorView.getDrawingCache();
    if (bmp == null) {
      return null;
    }
    DisplayMetrics dm = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    Bitmap ret;
    if (isDeleteStatusBar) {
      Resources resources = activity.getResources();
      int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
      int statusBarHeight = resources.getDimensionPixelSize(resourceId);
      ret = Bitmap.createBitmap(
              bmp,
              0,
              statusBarHeight,
              dm.widthPixels,
              dm.heightPixels - statusBarHeight
      );
    } else {
      ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
    }
    decorView.destroyDrawingCache();
    return ret;
  }

  public static boolean isScreenLock() {
    KeyguardManager km =
            (KeyguardManager) Utils.getApp().getSystemService(Context.KEYGUARD_SERVICE);
    return km.inKeyguardRestrictedInputMode();
  }

  public static int getSleepDuration() {
    try {
      return Settings.System.getInt(
              Utils.getApp().getContentResolver(),
              Settings.System.SCREEN_OFF_TIMEOUT
      );
    } catch (Settings.SettingNotFoundException e) {
      e.printStackTrace();
      return -123;
    }
  }

  @RequiresPermission(WRITE_SETTINGS)
  public static void setSleepDuration(final int duration) {
    Settings.System.putInt(
            Utils.getApp().getContentResolver(),
            Settings.System.SCREEN_OFF_TIMEOUT,
            duration
    );
  }

  public static boolean isTablet() {
    return (Utils.getApp().getResources().getConfiguration().screenLayout
            & Configuration.SCREENLAYOUT_SIZE_MASK)
            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
  }
}
