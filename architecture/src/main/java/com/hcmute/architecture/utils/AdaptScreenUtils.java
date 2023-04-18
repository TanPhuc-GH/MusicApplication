package com.hcmute.architecture.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.lang.reflect.Field;

public final class AdaptScreenUtils {

  private static boolean isInitMiui = false;
  private static Field mTmpMetricsField;

  public static Resources adaptWidth(Resources resources, int designWidth) {
    DisplayMetrics dm = getDisplayMetrics(resources);
    float newXdpi = dm.xdpi = (dm.widthPixels * 72f) / designWidth;
    setAppDmXdpi(newXdpi);
    return resources;
  }


  public static Resources adaptHeight(Resources resources, int designHeight) {
    DisplayMetrics dm = getDisplayMetrics(resources);
    float newXdpi = dm.xdpi = (dm.heightPixels * 72f) / designHeight;
    setAppDmXdpi(newXdpi);
    return resources;
  }


  public static Resources closeAdapt(Resources resources) {
    DisplayMetrics dm = getDisplayMetrics(resources);
    float newXdpi = dm.xdpi = dm.density * 72;
    setAppDmXdpi(newXdpi);
    return resources;
  }


  public static int pt2Px(float ptValue) {
    DisplayMetrics metrics = Utils.getApp().getResources().getDisplayMetrics();
    return (int) (ptValue * metrics.xdpi / 72f + 0.5);
  }


  public static int px2Pt(float pxValue) {
    DisplayMetrics metrics = Utils.getApp().getResources().getDisplayMetrics();
    return (int) (pxValue * 72 / metrics.xdpi + 0.5);
  }

  private static void setAppDmXdpi(final float xdpi) {
    Utils.getApp().getResources().getDisplayMetrics().xdpi = xdpi;
  }

  private static DisplayMetrics getDisplayMetrics(Resources resources) {
    DisplayMetrics miuiDisplayMetrics = getMiuiTmpMetrics(resources);
    if (miuiDisplayMetrics == null) {
      return resources.getDisplayMetrics();
    }
    return miuiDisplayMetrics;
  }

  private static DisplayMetrics getMiuiTmpMetrics(Resources resources) {
    if (!isInitMiui) {
      DisplayMetrics ret = null;
      String simpleName = resources.getClass().getSimpleName();
      if ("MiuiResources".equals(simpleName) || "XResources".equals(simpleName)) {
        try {
          //noinspection JavaReflectionMemberAccess
          mTmpMetricsField = Resources.class.getDeclaredField("mTmpMetrics");
          mTmpMetricsField.setAccessible(true);
          ret = (DisplayMetrics) mTmpMetricsField.get(resources);
        } catch (Exception e) {
          Log.e("AdaptScreenUtils", "no field of mTmpMetrics in resources.");
        }
      }
      isInitMiui = true;
      return ret;
    }
    if (mTmpMetricsField == null) {
      return null;
    }
    try {
      return (DisplayMetrics) mTmpMetricsField.get(resources);
    } catch (Exception e) {
      return null;
    }
  }
}
