package com.hcmute.puremusic.ui.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.hcmute.architecture.ui.page.BaseFragment;
import com.hcmute.puremusic.BR;
import com.hcmute.puremusic.R;
import com.hcmute.puremusic.data.bean.LibraryInfo;
import com.hcmute.puremusic.player.domain.usecase.request.InfoRequester;
import com.hcmute.puremusic.ui.page.adapter.DrawerAdapter;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.state.State;

import java.util.ArrayList;
import java.util.List;


public class DrawerFragment extends BaseFragment {

  private DrawerViewModel mStates;
  private InfoRequester mInfoRequester;

  @Override
  protected void initViewModel() {
    mStates = getFragmentScopeViewModel(DrawerViewModel.class);
    mInfoRequester = getFragmentScopeViewModel(InfoRequester.class);
  }

  @Override
  protected DataBindingConfig getDataBindingConfig() {
    return new DataBindingConfig(R.layout.fragment_drawer, BR.vm, mStates)
            .addBindingParam(BR.click, new ClickProxy())
            .addBindingParam(BR.adapter, new DrawerAdapter(getContext()));
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mInfoRequester.getLibraryResult().observe(getViewLifecycleOwner(), dataResult -> {
      if (!dataResult.getResponseStatus().isSuccess()) return;

      if (dataResult.getResult() != null) {
        mStates.list.set(dataResult.getResult());
      }
    });

    if (mInfoRequester.getLibraryResult().getValue() == null) {
      mInfoRequester.requestLibraryInfo();
    }
  }

  public class ClickProxy {
    public void logoClick() {
      openUrlInBrowser(getString(R.string.github_project));
    }
  }

  public static class DrawerViewModel extends ViewModel {
    public final State<List<LibraryInfo>> list = new State<>(new ArrayList<>());
  }

}
