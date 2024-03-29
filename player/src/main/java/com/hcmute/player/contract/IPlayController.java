package com.hcmute.player.contract;

import android.content.Context;

import com.hcmute.player.bean.base.BaseAlbumItem;
import com.hcmute.player.bean.base.BaseArtistItem;
import com.hcmute.player.bean.base.BaseMusicItem;
import com.hcmute.player.domain.PlayerInfoDispatcher;


public interface IPlayController<
        B extends BaseAlbumItem<M, A>,
        M extends BaseMusicItem<A>,
        A extends BaseArtistItem>
        extends IPlayInfoManager<B, M, A> {

  void init(Context context, IServiceNotifier iServiceNotifier, ICacheProxy iCacheProxy);

  void loadAlbum(B musicAlbum);

  void loadAlbum(B musicAlbum, int playIndex);

  void playAudio();

  void playAudio(int albumIndex);

  void playNext();

  void playPrevious();

  void playAgain();

  void togglePlay();

  void pauseAudio();

  void resumeAudio();

  void clear();

  void changeMode();

  boolean isPlaying();

  boolean isPaused();

  boolean isInit();

  void setSeek(int progress);

  String getTrackTime(int progress);

  PlayerInfoDispatcher getDispatcher();
}
