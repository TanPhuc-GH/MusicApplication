package com.hcmute.puremusic;

import com.hcmute.architecture.utils.Utils;
import com.hcmute.architecture.BaseApplication;
import com.hcmute.puremusic.player.PlayerManager;

public class App extends BaseApplication {

  @Override
  public void onCreate() {
    super.onCreate();

    Utils.init(this);
    PlayerManager.getInstance().init(this);
  }

}