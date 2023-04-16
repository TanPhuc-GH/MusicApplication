package com.hcmute.player.bean;

import com.hcmute.player.bean.base.BaseAlbumItem;
import com.hcmute.player.bean.base.BaseArtistItem;
import com.hcmute.player.bean.base.BaseMusicItem;

public class DefaultAlbum extends BaseAlbumItem<
        DefaultAlbum.DefaultMusic,
        DefaultAlbum.DefaultArtist> {

  public static class DefaultMusic extends BaseMusicItem<DefaultArtist> {

  }

  public static class DefaultArtist extends BaseArtistItem {

  }
}
