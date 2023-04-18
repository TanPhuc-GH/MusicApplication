

package com.hcmute.architecture.utils;


public class DisplayUtils {


  public static int px2dp(float pxValue) {
    final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }



  public static int dp2px(float dipValue) {
    final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
    return (int) (dipValue * scale + 0.5f);
  }



  public static int px2sp(float pxValue) {
    final float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
    return (int) (pxValue / fontScale + 0.5f);
  }



  public static int sp2px(float spValue) {
    final float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
    return (int) (spValue * fontScale + 0.5f);
  }
}
