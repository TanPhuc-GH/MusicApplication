package com.kunminx.puremusic.data.config;

import android.os.Environment;

import com.kunminx.architecture.utils.Utils;
public class Configs {

  public static final String COVER_PATH = Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();

  public static final String TOKEN = "token";

}
