package com.hcmute.architecture.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@SuppressLint("ApplySharedPref")
public final class SPUtils {

  private static final Map<String, SPUtils> SP_UTILS_MAP = new HashMap<>();
  private final SharedPreferences sp;

  private SPUtils(final String spName) {
    sp = Utils.getApp().getSharedPreferences(spName, Context.MODE_PRIVATE);
  }

  private SPUtils(final String spName, final int mode) {
    sp = Utils.getApp().getSharedPreferences(spName, mode);
  }


  public static SPUtils getInstance() {
    return getInstance("", Context.MODE_PRIVATE);
  }


  public static SPUtils getInstance(final int mode) {
    return getInstance("", mode);
  }

  public static SPUtils getInstance(String spName) {
    return getInstance(spName, Context.MODE_PRIVATE);
  }


  public static SPUtils getInstance(String spName, final int mode) {
    if (isSpace(spName)) {
      spName = "spUtils";
    }
    SPUtils spUtils = SP_UTILS_MAP.get(spName);
    if (spUtils == null) {
      synchronized (SPUtils.class) {
        spUtils = SP_UTILS_MAP.get(spName);
        if (spUtils == null) {
          spUtils = new SPUtils(spName, mode);
          SP_UTILS_MAP.put(spName, spUtils);
        }
      }
    }
    return spUtils;
  }

  private static boolean isSpace(final String s) {
    if (s == null) {
      return true;
    }
    for (int i = 0, len = s.length(); i < len; ++i) {
      if (!Character.isWhitespace(s.charAt(i))) {
        return false;
      }
    }
    return true;
  }


  public void put(@NonNull final String key, final String value) {
    put(key, value, false);
  }


  public void put(@NonNull final String key, final String value, final boolean isCommit) {
    if (isCommit) {
      sp.edit().putString(key, value).commit();
    } else {
      sp.edit().putString(key, value).apply();
    }
  }

  public String getString(@NonNull final String key) {
    return getString(key, "");
  }

  public String getString(@NonNull final String key, final String defaultValue) {
    return sp.getString(key, defaultValue);
  }

  public void put(@NonNull final String key, final int value) {
    put(key, value, false);
  }


  public void put(@NonNull final String key, final int value, final boolean isCommit) {
    if (isCommit) {
      sp.edit().putInt(key, value).commit();
    } else {
      sp.edit().putInt(key, value).apply();
    }
  }


  public int getInt(@NonNull final String key) {
    return getInt(key, -1);
  }


  public int getInt(@NonNull final String key, final int defaultValue) {
    return sp.getInt(key, defaultValue);
  }

  public void put(@NonNull final String key, final long value) {
    put(key, value, false);
  }


  public void put(@NonNull final String key, final long value, final boolean isCommit) {
    if (isCommit) {
      sp.edit().putLong(key, value).commit();
    } else {
      sp.edit().putLong(key, value).apply();
    }
  }


  public long getLong(@NonNull final String key) {
    return getLong(key, -1L);
  }


  public long getLong(@NonNull final String key, final long defaultValue) {
    return sp.getLong(key, defaultValue);
  }

  public void put(@NonNull final String key, final float value) {
    put(key, value, false);
  }


  public void put(@NonNull final String key, final float value, final boolean isCommit) {
    if (isCommit) {
      sp.edit().putFloat(key, value).commit();
    } else {
      sp.edit().putFloat(key, value).apply();
    }
  }

  public float getFloat(@NonNull final String key) {
    return getFloat(key, -1f);
  }


  public float getFloat(@NonNull final String key, final float defaultValue) {
    return sp.getFloat(key, defaultValue);
  }


  public void put(@NonNull final String key, final boolean value) {
    put(key, value, false);
  }


  public void put(@NonNull final String key, final boolean value, final boolean isCommit) {
    if (isCommit) {
      sp.edit().putBoolean(key, value).commit();
    } else {
      sp.edit().putBoolean(key, value).apply();
    }
  }

  public boolean getBoolean(@NonNull final String key) {
    return getBoolean(key, false);
  }

  public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
    return sp.getBoolean(key, defaultValue);
  }


  public void put(@NonNull final String key, final Set<String> value) {
    put(key, value, false);
  }

  public void put(@NonNull final String key,
                  final Set<String> value,
                  final boolean isCommit) {
    if (isCommit) {
      sp.edit().putStringSet(key, value).commit();
    } else {
      sp.edit().putStringSet(key, value).apply();
    }
  }

  public Set<String> getStringSet(@NonNull final String key) {
    return getStringSet(key, Collections.emptySet());
  }


  public Set<String> getStringSet(@NonNull final String key,
                                  final Set<String> defaultValue) {
    return sp.getStringSet(key, defaultValue);
  }

  public Map<String, ?> getAll() {
    return sp.getAll();
  }


  public boolean contains(@NonNull final String key) {
    return sp.contains(key);
  }


  public void remove(@NonNull final String key) {
    remove(key, false);
  }


  public void remove(@NonNull final String key, final boolean isCommit) {
    if (isCommit) {
      sp.edit().remove(key).commit();
    } else {
      sp.edit().remove(key).apply();
    }
  }

  public void clear() {
    clear(false);
  }


  public void clear(final boolean isCommit) {
    if (isCommit) {
      sp.edit().clear().commit();
    } else {
      sp.edit().clear().apply();
    }
  }
}
