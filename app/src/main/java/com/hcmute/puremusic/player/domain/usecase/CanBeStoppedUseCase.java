

package com.hcmute.puremusic.player.domain.usecase;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.hcmute.architecture.data.response.DataResult;
import com.hcmute.architecture.domain.usecase.UseCase;
import com.hcmute.puremusic.data.repository.DataRepository;

import java.io.File;



public class CanBeStoppedUseCase extends UseCase<CanBeStoppedUseCase.RequestValues,
        CanBeStoppedUseCase.ResponseValue> implements DefaultLifecycleObserver {

  private final DownloadState downloadState = new DownloadState();

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    if (getRequestValues() != null) {
      downloadState.isForgive = true;
      downloadState.file = null;
      downloadState.progress = 0;
      getUseCaseCallback().onError();
    }
  }

  @Override
  protected void executeUseCase(RequestValues requestValues) {
    DataRepository.getInstance().downloadFile(downloadState, dataResult -> {
      getUseCaseCallback().onSuccess(new ResponseValue(dataResult));
    });
  }

  public static final class RequestValues implements UseCase.RequestValues {

  }

  public static final class ResponseValue implements UseCase.ResponseValue {

    private final DataResult<DownloadState> mDataResult;

    public ResponseValue(DataResult<CanBeStoppedUseCase.DownloadState> dataResult) {
      mDataResult = dataResult;
    }

    public DataResult<CanBeStoppedUseCase.DownloadState> getDataResult() {
      return mDataResult;
    }
  }

  public static final class DownloadState {

    public boolean isForgive = false;

    public int progress;

    public File file;
  }
}
