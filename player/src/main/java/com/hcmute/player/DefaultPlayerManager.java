package com.hcmute.player;

import android.annotation.SuppressLint;
import android.content.Context;

import com.hcmute.player.bean.DefaultAlbum;
import com.hcmute.player.contract.ICacheProxy;
import com.hcmute.player.contract.IPlayController;
import com.hcmute.player.contract.IServiceNotifier;
import com.hcmute.player.domain.PlayerInfoDispatcher;

import java.util.List;


public class DefaultPlayerManager implements IPlayController<DefaultAlbum, DefaultAlbum.DefaultMusic, DefaultAlbum.DefaultArtist> {

  @SuppressLint("StaticFieldLeak")
  private static final DefaultPlayerManager sManager = new DefaultPlayerManager();

  private final PlayerController<DefaultAlbum, DefaultAlbum.DefaultMusic, DefaultAlbum.DefaultArtist> mController;

  private DefaultPlayerManager() {
    mController = new PlayerController<>();
  }

  public static DefaultPlayerManager getInstance() {
    return sManager;
  }

  @Override
  public void init(Context context, IServiceNotifier iServiceNotifier, ICacheProxy iCacheProxy) {
    mController.init(context.getApplicationContext(), iServiceNotifier, iCacheProxy);
  }

  @Override
  public void loadAlbum(DefaultAlbum musicAlbum) {
    mController.loadAlbum(musicAlbum);
  }

  @Override
  public void loadAlbum(DefaultAlbum musicAlbum, int playIndex) {
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
  public DefaultAlbum getAlbum() {
    return mController.getAlbum();
  }

  @Override
  public List<DefaultAlbum.DefaultMusic> getAlbumMusics() {
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
  public DefaultAlbum.DefaultMusic getCurrentPlayingMusic() {
    return mController.getCurrentPlayingMusic();
  }
}
