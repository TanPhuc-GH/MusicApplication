package com.hcmute.puremusic.player.domain.usecase.request;

import androidx.lifecycle.ViewModel;

import com.hcmute.architecture.data.response.DataResult;
import com.kunminx.architecture.domain.result.MutableResult;
import com.kunminx.architecture.domain.result.Result;
import com.hcmute.puremusic.data.bean.TestAlbum;
import com.hcmute.puremusic.data.repository.DataRepository;


public class MusicRequester extends ViewModel {

  private final MutableResult<DataResult<TestAlbum>> mFreeMusicsResult = new MutableResult<>();

  public Result<DataResult<TestAlbum>> getFreeMusicsResult() {
    return mFreeMusicsResult;
  }

  public void requestFreeMusics() {
    DataRepository.getInstance().getFreeMusic(mFreeMusicsResult::setValue);
  }
}
