package com.kunminx.puremusic.data.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kunminx.architecture.data.response.DataResult;
import com.kunminx.architecture.data.response.ResponseStatus;
import com.kunminx.architecture.utils.Utils;
import com.kunminx.puremusic.R;
import com.kunminx.puremusic.data.bean.LibraryInfo;
import com.kunminx.puremusic.data.bean.TestAlbum;
import com.kunminx.puremusic.player.domain.usecase.CanBeStoppedUseCase;

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
          Log.d("TAG", "下载进度 " + downloadState.progress + "%");
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