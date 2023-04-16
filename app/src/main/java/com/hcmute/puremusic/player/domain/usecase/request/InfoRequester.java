package com.hcmute.puremusic.player.domain.usecase.request;

import androidx.lifecycle.ViewModel;

import com.hcmute.architecture.data.response.DataResult;
import com.kunminx.architecture.domain.result.MutableResult;
import com.kunminx.architecture.domain.result.Result;
import com.hcmute.puremusic.data.bean.LibraryInfo;
import com.hcmute.puremusic.data.repository.DataRepository;

import java.util.List;


public class InfoRequester extends ViewModel {

  private final MutableResult<DataResult<List<LibraryInfo>>> mLibraryResult = new MutableResult<>();

  public Result<DataResult<List<LibraryInfo>>> getLibraryResult() {
    return mLibraryResult;
  }

  public void requestLibraryInfo() {
    DataRepository.getInstance().getLibraryInfo(mLibraryResult::setValue);
  }
}
