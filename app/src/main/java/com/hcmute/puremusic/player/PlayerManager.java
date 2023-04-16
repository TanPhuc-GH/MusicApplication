package com.hcmute.puremusic.player;

import android.content.Context;
import android.content.Intent;

import com.danikula.videocache.HttpProxyCacheServer;
import com.hcmute.player.PlayerController;
import com.hcmute.player.PlayingInfoManager;
import com.hcmute.player.contract.ICacheProxy;
import com.hcmute.player.contract.IPlayController;
import com.hcmute.player.contract.IServiceNotifier;
import com.hcmute.player.domain.PlayerInfoDispatcher;
import com.hcmute.puremusic.player.helper.PlayerFileNameGenerator;
import com.hcmute.puremusic.player.notification.PlayerService;
import com.hcmute.puremusic.data.bean.TestAlbum;

import java.util.List;

public class PlayerManager implements IPlayController<TestAlbum, TestAlbum.TestMusic, TestAlbum.TestArtist> {

  private static final PlayerManager sManager = new PlayerManager();

  private final PlayerController<TestAlbum, TestAlbum.TestMusic, TestAlbum.TestArtist> mController;

  private PlayerManager() {
    mController = new PlayerController<>();
  }

  public static PlayerManager getInstance() {
    return sManager;
  }

  private HttpProxyCacheServer mProxy;

  public void init(Context context) {
    init(context, null, null);
  }

  @Override
  public void init(Context context, IServiceNotifier iServiceNotifier, ICacheProxy iCacheProxy) {
    Context context1 = context.getApplicationContext();

    mProxy = new HttpProxyCacheServer.Builder(context1)
            .fileNameGenerator(new PlayerFileNameGenerator())
            .maxCacheSize(2147483648L) // 2GB
            .build();

    mController.init(context1, startOrStop -> {
      Intent intent = new Intent(context1, PlayerService.class);
      if (startOrStop) {
        context1.startService(intent);
      } else {
        context1.stopService(intent);
      }
    }, url -> mProxy.getProxyUrl(url));
  }

  @Override
  public void loadAlbum(TestAlbum musicAlbum) {
    mController.loadAlbum(musicAlbum);
  }

  @Override
  public void loadAlbum(TestAlbum musicAlbum, int playIndex) {
    mController.loadAlbum(musicAlbum, playIndex);
  }

  @Override
  public void playAudio() {
    mController.playAudio();
  }

  @Override
  public void playAudio(int albumIndex) {
    mController.playAudio(albumIndex);
  }

  @Override
  public void playNext() {
    mController.playNext();
  }

  @Override
  public void playPrevious() {
    mController.playPrevious();
  }

  @Override
  public void playAgain() {
    mController.playAgain();
  }

  @Override
  public void pauseAudio() {
    mController.pauseAudio();
  }

  @Override
  public void resumeAudio() {
    mController.resumeAudio();
  }

  @Override
  public void clear() {
    mController.clear();
  }

  @Override
  public void changeMode() {
    mController.changeMode();
  }

  @Override
  public boolean isPlaying() {
    return mController.isPlaying();
  }

  @Override
  public boolean isPaused() {
    return mController.isPaused();
  }

  @Override
  public boolean isInit() {
    return mController.isInit();
  }

  @Override
  public void requestLastPlayingInfo() {
    mController.requestLastPlayingInfo();
  }

  @Override
  public void setSeek(int progress) {
    mController.setSeek(progress);
  }

  @Override
  public String getTrackTime(int progress) {
    return mController.getTrackTime(progress);
  }

  @Override
  public PlayerInfoDispatcher getDispatcher() {
    return mController.getDispatcher();
  }

  @Override
  public TestAlbum getAlbum() {
    return mController.getAlbum();
  }

  @Override
  public List<TestAlbum.TestMusic> getAlbumMusics() {
    return mController.getAlbumMusics();
  }

  @Override
  public void setChangingPlayingMusic(boolean changingPlayingMusic) {
    mController.setChangingPlayingMusic(changingPlayingMusic);
  }

  @Override
  public int getAlbumIndex() {
    return mController.getAlbumIndex();
  }

  @Override
  public Enum<PlayingInfoManager.RepeatMode> getRepeatMode() {
    return mController.getRepeatMode();
  }

  @Override
  public void togglePlay() {
    mController.togglePlay();
  }

  @Override
  public TestAlbum.TestMusic getCurrentPlayingMusic() {
    return mController.getCurrentPlayingMusic();
  }
}
