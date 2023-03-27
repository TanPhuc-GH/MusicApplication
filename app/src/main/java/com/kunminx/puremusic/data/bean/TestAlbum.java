package com.kunminx.puremusic.data.bean;

import com.kunminx.player.bean.base.BaseAlbumItem;
import com.kunminx.player.bean.base.BaseArtistItem;
import com.kunminx.player.bean.base.BaseMusicItem;

public class TestAlbum extends BaseAlbumItem<TestAlbum.TestMusic, TestAlbum.TestArtist> {

  private String albumMid;

  public String getAlbumMid() {
    return albumMid;
  }

  public static class TestMusic extends BaseMusicItem<TestArtist> {

    private String songMid;

    public String getSongMid() {
      return songMid;
    }
  }

  public static class TestArtist extends BaseArtistItem {

    private String birthday;

    public String getBirthday() {
      return birthday;
    }
  }
}

