package com.kunminx.puremusic;

import com.kunminx.architecture.BaseApplication;
import com.kunminx.architecture.utils.Utils;
import com.kunminx.puremusic.player.PlayerManager;

public class App extends BaseApplication {

  @Override
  public void onCreate() {
    super.onCreate();

    Utils.init(this);
    PlayerManager.getInstance().init(this);
  }

}