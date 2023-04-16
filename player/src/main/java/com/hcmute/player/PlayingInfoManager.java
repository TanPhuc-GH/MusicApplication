package com.hcmute.player;

import com.hcmute.player.bean.base.BaseAlbumItem;
import com.hcmute.player.bean.base.BaseArtistItem;
import com.hcmute.player.bean.base.BaseMusicItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PlayingInfoManager<B extends BaseAlbumItem<M, A>, M extends BaseMusicItem<A>, A extends BaseArtistItem> {

  private int mPlayIndex = 0;
  private int mAlbumIndex = 0;
  private Enum<RepeatMode> mRepeatMode;

  public enum RepeatMode {
    SINGLE_CYCLE,
    LIST_CYCLE,
    RANDOM
  }

  private final List<M> mOriginPlayingList = new ArrayList<>();
  private final List<M> mShufflePlayingList = new ArrayList<>();
  private B mMusicAlbum;

  boolean isInit() {
    return mMusicAlbum != null;
  }

  private void fitShuffle() {
    mShufflePlayingList.clear();
    mShufflePlayingList.addAll(mOriginPlayingList);
    Collections.shuffle(mShufflePlayingList);
  }

  Enum<RepeatMode> changeMode() {
    if (mRepeatMode == RepeatMode.LIST_CYCLE) {
      mRepeatMode = RepeatMode.SINGLE_CYCLE;
    } else if (mRepeatMode == RepeatMode.SINGLE_CYCLE) {
      mRepeatMode = RepeatMode.RANDOM;
    } else {
      mRepeatMode = RepeatMode.LIST_CYCLE;
    }
    return mRepeatMode;
  }

  B getMusicAlbum() {
    return mMusicAlbum;
  }

  void setMusicAlbum(B musicAlbum) {
    this.mMusicAlbum = musicAlbum;
    mOriginPlayingList.clear();
    mOriginPlayingList.addAll(mMusicAlbum.getMusics());
    fitShuffle();
  }

  List<M> getPlayingList() {
    if (mRepeatMode == RepeatMode.RANDOM) {
      return mShufflePlayingList;
    } else {
      return mOriginPlayingList;
    }
  }

  List<M> getOriginPlayingList() {
    return mOriginPlayingList;
  }

  M getCurrentPlayingMusic() {
    if (getPlayingList().isEmpty()) {
      return null;
    }
    return getPlayingList().get(mPlayIndex);
  }

  Enum<RepeatMode> getRepeatMode() {
    return mRepeatMode;
  }

  void countPreviousIndex() {
    if (mPlayIndex == 0) {
      mPlayIndex = (getPlayingList().size() - 1);
    } else {
      --mPlayIndex;
    }
    mAlbumIndex = mOriginPlayingList.indexOf(getCurrentPlayingMusic());
  }

  void countNextIndex() {
    if (mPlayIndex == (getPlayingList().size() - 1)) {
      mPlayIndex = 0;
    } else {
      ++mPlayIndex;
    }
    mAlbumIndex = mOriginPlayingList.indexOf(getCurrentPlayingMusic());
  }

  int getAlbumIndex() {
    return mAlbumIndex;
  }

  void setAlbumIndex(int albumIndex) {
    mAlbumIndex = albumIndex;
    mPlayIndex = getPlayingList().indexOf(mOriginPlayingList.get(mAlbumIndex));
  }
}
