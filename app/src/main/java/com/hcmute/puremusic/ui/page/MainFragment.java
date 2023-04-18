package com.hcmute.puremusic.ui.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.hcmute.architecture.domain.usecase.ui.page.BaseFragment;
import com.hcmute.player.domain.PlayerEvent;
import com.hcmute.puremusic.BR;
import com.hcmute.puremusic.R;
import com.hcmute.puremusic.data.bean.TestAlbum;
import com.hcmute.puremusic.player.PlayerManager;
import com.hcmute.puremusic.player.domain.message.PageMessenger;
import com.hcmute.puremusic.player.domain.usecase.request.MusicRequester;
import com.hcmute.puremusic.ui.page.adapter.PlaylistAdapter;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.state.State;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends BaseFragment {

  private MainViewModel mStates;
  private PageMessenger mMessenger;
  private MusicRequester mMusicRequester;
  private PlaylistAdapter mAdapter;

  @Override
  protected void initViewModel() {
    mStates = getFragmentScopeViewModel(MainViewModel.class);
    mMessenger = getApplicationScopeViewModel(PageMessenger.class);
    mMusicRequester = getFragmentScopeViewModel(MusicRequester.class);
  }

  @Override
  protected DataBindingConfig getDataBindingConfig() {

    mAdapter = new PlaylistAdapter(getContext());
    mAdapter.setOnItemClickListener((viewId, item, position) -> {
      PlayerManager.getInstance().playAudio(position);
    });

    return new DataBindingConfig(R.layout.fragment_main, BR.vm, mStates)
            .addBindingParam(BR.click, new ClickProxy())
            .addBindingParam(BR.adapter, mAdapter);
  }

  @SuppressLint("NotifyDataSetChanged")
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    PlayerManager.getInstance().getDispatcher().output(this, playerEvent -> {
      if (playerEvent.eventId == PlayerEvent.EVENT_CHANGE_MUSIC) {
        mAdapter.notifyDataSetChanged();
      }
    });

    mMusicRequester.getFreeMusicsResult().observe(getViewLifecycleOwner(), dataResult -> {
      if (!dataResult.getResponseStatus().isSuccess()) return;

      TestAlbum musicAlbum = dataResult.getResult();
      if (musicAlbum != null && musicAlbum.getMusics() != null) {
        mStates.list.set(musicAlbum.getMusics());
        if (PlayerManager.getInstance().getAlbum() == null ||
                !PlayerManager.getInstance().getAlbum().getAlbumId().equals(musicAlbum.getAlbumId())) {
          PlayerManager.getInstance().loadAlbum(musicAlbum);
        }
      }
    });

    if (PlayerManager.getInstance().getAlbum() == null) {
      mMusicRequester.requestFreeMusics();
    } else {
      mStates.list.set(PlayerManager.getInstance().getAlbum().getMusics());
    }
  }

  public class ClickProxy {
    public void openMenu() {
      mMessenger.requestToOpenOrCloseDrawer(true);
    }
  }

  public static class MainViewModel extends ViewModel {
    public final State<Boolean> initTabAndPage = new State<>(true);
    public final State<String> pageAssetPath = new State<>("summary.html");
    public final State<List<TestAlbum.TestMusic>> list = new State<>(new ArrayList<>());
  }

}
