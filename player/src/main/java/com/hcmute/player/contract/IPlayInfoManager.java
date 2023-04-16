package com.hcmute.player.contract;

import com.hcmute.player.PlayingInfoManager;
import com.hcmute.player.bean.base.BaseAlbumItem;
import com.hcmute.player.bean.base.BaseArtistItem;
import com.hcmute.player.bean.base.BaseMusicItem;

import java.util.List;

public interface IPlayInfoManager<B extends BaseAlbumItem<M, A>, M extends BaseMusicItem<A>, A extends BaseArtistItem> {

  B getAlbum();

  List<M> getAlbumMusics();

  void setChangingPlayingMusic(boolean changingPlayingMusic);

  int getAlbumIndex();

  Enum<PlayingInfoManager.RepeatMode> getRepeatMode();

  M getCurrentPlayingMusic();

  void requestLastPlayingInfo();
}
