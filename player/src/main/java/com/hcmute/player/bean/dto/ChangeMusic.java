
package com.hcmute.player.bean.dto;

import com.hcmute.player.bean.base.BaseMusicItem;
import com.hcmute.player.bean.base.BaseAlbumItem;
import com.hcmute.player.bean.base.BaseArtistItem;

import java.io.Serializable;

public class ChangeMusic<
        B extends BaseAlbumItem<M, A>,
        M extends BaseMusicItem<A>,
        A extends BaseArtistItem>
        implements Serializable {

  private String title;
  private String summary;
  private A artist;
  private String albumId;
  private String musicId;
  private String img;

  public ChangeMusic() {
  }

  public ChangeMusic(
          String title,
          String summary,
          String albumId,
          String musicId,
          String img,
          A artist
  ) {
    this.title = title;
    this.summary = summary;
    this.albumId = albumId;
    this.musicId = musicId;
    this.img = img;
    this.artist = artist;
  }

  public ChangeMusic(B musicAlbum, int playIndex) {
    this.title = musicAlbum.getTitle();
    this.summary = musicAlbum.getSummary();
    this.albumId = musicAlbum.getAlbumId();
    this.musicId = ((M) musicAlbum.getMusics().get(playIndex)).getMusicId();
    this.img = musicAlbum.getCoverImg();
    this.artist = (A) musicAlbum.getArtist();
  }

  public void setBaseInfo(B musicAlbum, M music) {
    this.title = music.getTitle();
    this.summary = musicAlbum.getSummary();
    this.albumId = musicAlbum.getAlbumId();
    this.musicId = music.getMusicId();
    this.img = music.getCoverImg();
    this.artist = (A) music.getArtist();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getAlbumId() {
    return albumId;
  }

  public void setAlbumId(String albumId) {
    this.albumId = albumId;
  }

  public String getMusicId() {
    return musicId;
  }

  public void setMusicId(String musicId) {
    this.musicId = musicId;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public A getArtist() {
    return artist;
  }

  public void setArtist(A artist) {
    this.artist = artist;
  }
}
