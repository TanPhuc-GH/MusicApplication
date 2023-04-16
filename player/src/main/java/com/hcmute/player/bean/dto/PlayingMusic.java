package com.hcmute.player.bean.dto;


import com.hcmute.player.bean.base.BaseMusicItem;
import com.hcmute.player.bean.base.BaseAlbumItem;
import com.hcmute.player.bean.base.BaseArtistItem;

import java.io.Serializable;


public class PlayingMusic<
        B extends BaseAlbumItem<M, A>,
        M extends BaseMusicItem<A>,
        A extends BaseArtistItem>
        extends ChangeMusic<B, M, A> implements Serializable {

  private String nowTime;
  private String allTime;
  private int duration;
  private int playerPosition;

  public PlayingMusic(String nowTime, String allTime) {
    this.nowTime = nowTime;
    this.allTime = allTime;
  }

  public PlayingMusic(
          String title,
          String summary,
          String bookId,
          String chapterId,
          String nowTime,
          String allTime,
          String img,
          A artist
  ) {
    super(title, summary, bookId, chapterId, img, artist);
    this.nowTime = nowTime;
    this.allTime = allTime;
  }

  public PlayingMusic(
          B baseAlbumItem,
          int playIndex,
          String nowTime,
          String allTime
  ) {
    super(baseAlbumItem, playIndex);
    this.nowTime = nowTime;
    this.allTime = allTime;
  }

  public String getNowTime() {
    return nowTime;
  }

  public void setNowTime(String nowTime) {
    this.nowTime = nowTime;
  }

  public String getAllTime() {
    return allTime;
  }

  public void setAllTime(String allTime) {
    this.allTime = allTime;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public int getPlayerPosition() {
    return playerPosition;
  }

  public void setPlayerPosition(int playerPosition) {
    this.playerPosition = playerPosition;
  }
}
