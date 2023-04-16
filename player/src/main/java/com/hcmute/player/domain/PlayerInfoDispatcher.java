package com.hcmute.player.domain;

import com.kunminx.architecture.domain.dispatch.MviDispatcher;

public class PlayerInfoDispatcher extends MviDispatcher<PlayerEvent> {
  @Override
  protected void onHandle(PlayerEvent event) {
    sendResult(event);
  }
}
