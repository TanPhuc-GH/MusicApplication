package com.hcmute.puremusic.data.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hcmute.architecture.data.response.DataResult;
import com.hcmute.architecture.data.response.ResponseStatus;
import com.hcmute.architecture.utils.Utils;
import com.hcmute.puremusic.player.domain.usecase.CanBeStoppedUseCase;
import com.hcmute.puremusic.R;
import com.hcmute.puremusic.data.bean.LibraryInfo;
import com.hcmute.puremusic.data.bean.TestAlbum;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DataRepository {

  private static final DataRepository S_REQUEST_MANAGER = new DataRepository();

  private DataRepository() {
  }

  public static DataRepository getInstance() {
    return S_REQUEST_MANAGER;
  }


  public void getFreeMusic(DataResult.Result<TestAlbum> result) {

    Gson gson = new Gson();
    Type type = new TypeToken<TestAlbum>() {
    }.getType();
    TestAlbum testAlbum = gson.fromJson(Utils.getApp().getString(R.string.free_music_json), type);

    result.onResult(new DataResult<>(testAlbum, new ResponseStatus()));
  }

  public void getLibraryInfo(DataResult.Result<List<LibraryInfo>> result) {
    Gson gson = new Gson();
    Type type = new TypeToken<List<LibraryInfo>>() {
    }.getType();
    List<LibraryInfo> list = gson.fromJson(Utils.getApp().getString(R.string.library_json), type);

    result.onResult(new DataResult<>(list, new ResponseStatus()));
  }


  public void downloadFile(CanBeStoppedUseCase.DownloadState downloadState, DataResult.Result<CanBeStoppedUseCase.DownloadState> result) {

    Timer timer = new Timer();

    TimerTask task = new TimerTask() {
      @Override
      public void run() {

        if (downloadState.progress < 100) {
          downloadState.progress = downloadState.progress + 1;
          Log.d("TAG", "log " + downloadState.progress + "%");
        } else {
          timer.cancel();
        }
        if (downloadState.isForgive) {
          timer.cancel();
          downloadState.progress = 0;
          downloadState.isForgive = false;
          return;
        }
        result.onResult(new DataResult<>(downloadState, new ResponseStatus()));
      }
    };

    timer.schedule(task, 100, 100);
  }

}