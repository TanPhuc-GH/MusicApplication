/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hcmute.puremusic.ui.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.hcmute.architecture.ui.page.BaseFragment;
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

/**
 * Create by KunMinX at 19/10/29
 */
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
