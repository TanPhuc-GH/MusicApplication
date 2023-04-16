package com.hcmute.puremusic.ui.page;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;

import com.hcmute.architecture.ui.page.BaseFragment;
import com.hcmute.architecture.utils.ToastUtils;
import com.hcmute.architecture.utils.Utils;
import com.hcmute.player.PlayingInfoManager;
import com.hcmute.player.domain.PlayerEvent;
import com.hcmute.puremusic.BR;
import com.hcmute.puremusic.R;
import com.hcmute.puremusic.databinding.FragmentPlayerBinding;
import com.hcmute.puremusic.player.PlayerManager;
import com.hcmute.puremusic.player.domain.message.DrawerCoordinateManager;
import com.hcmute.puremusic.player.domain.message.PageMessenger;
import com.hcmute.puremusic.ui.page.helper.DefaultInterface;
import com.hcmute.puremusic.ui.view.PlayerSlideListener;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.state.State;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.Objects;


public class PlayerFragment extends BaseFragment {

  private PlayerViewModel mStates;
  private PageMessenger mMessenger;
  private PlayerSlideListener mListener;

  @Override
  protected void initViewModel() {
    mStates = getFragmentScopeViewModel(PlayerViewModel.class);
    mMessenger = getApplicationScopeViewModel(PageMessenger.class);
  }

  @Override
  protected DataBindingConfig getDataBindingConfig() {
    return new DataBindingConfig(R.layout.fragment_player, BR.vm, mStates)
            .addBindingParam(BR.click, new ClickProxy())
            .addBindingParam(BR.listener, new ListenerHandler());
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mMessenger.isToAddSlideListener().observe(getViewLifecycleOwner(), aBoolean -> {
      if (view.getParent().getParent() instanceof SlidingUpPanelLayout) {
        SlidingUpPanelLayout sliding = (SlidingUpPanelLayout) view.getParent().getParent();
        sliding.addPanelSlideListener(mListener = new PlayerSlideListener((FragmentPlayerBinding) getBinding(), sliding));
        sliding.addPanelSlideListener(new DefaultInterface.PanelSlideListener() {
          @Override
          public void onPanelStateChanged(
                  View view, SlidingUpPanelLayout.PanelState panelState,
                  SlidingUpPanelLayout.PanelState panelState1) {
            DrawerCoordinateManager.getInstance().requestToUpdateDrawerMode(
                    panelState1 == SlidingUpPanelLayout.PanelState.EXPANDED,
                    this.getClass().getSimpleName()
            );
          }
        });
      }
    });

    PlayerManager.getInstance().getDispatcher().output(this, playerEvent -> {
      switch (playerEvent.eventId) {
        case PlayerEvent.EVENT_CHANGE_MUSIC:
          assert playerEvent.changeMusic != null;
          mStates.title.set(playerEvent.changeMusic.getTitle());
          mStates.artist.set(playerEvent.changeMusic.getSummary());
          mStates.coverImg.set(playerEvent.changeMusic.getImg());
          if (mListener != null) view.post(mListener::calculateTitleAndArtist);
          break;
        case PlayerEvent.EVENT_PROGRESS:
          assert playerEvent.playingMusic != null;
          mStates.maxSeekDuration.set(playerEvent.playingMusic.getDuration());
          mStates.currentSeekPosition.set(playerEvent.playingMusic.getPlayerPosition());
          break;
        case PlayerEvent.EVENT_PLAY_STATUS:
          mStates.isPlaying.set(!playerEvent.toPause);
          break;
        case PlayerEvent.EVENT_REPEAT_MODE:
          Enum<PlayingInfoManager.RepeatMode> anEnum = playerEvent.repeatMode;
          if (anEnum == PlayingInfoManager.RepeatMode.LIST_CYCLE) {
            mStates.playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT);
          } else if (anEnum == PlayingInfoManager.RepeatMode.SINGLE_CYCLE) {
            mStates.playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT_ONCE);
          } else {
            mStates.playModeIcon.set(MaterialDrawableBuilder.IconValue.SHUFFLE);
          }
          break;
      }
    });

    mMessenger.isToCloseSlidePanelIfExpanded().observe(getViewLifecycleOwner(), aBoolean -> {
      if (view.getParent().getParent() instanceof SlidingUpPanelLayout) {
        SlidingUpPanelLayout sliding = (SlidingUpPanelLayout) view.getParent().getParent();
        if (sliding.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
          sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
          mMessenger.requestToCloseActivityIfAllowed(true);
        }
      } else {
        mMessenger.requestToCloseActivityIfAllowed(true);
      }
    });

  }

  public class ClickProxy {
    public void playMode() {
      PlayerManager.getInstance().changeMode();
    }

    public void previous() {
      PlayerManager.getInstance().playPrevious();
    }

    public void togglePlay() {
      PlayerManager.getInstance().togglePlay();
    }

    public void next() {
      PlayerManager.getInstance().playNext();
    }

    public void showPlayList() {
      ToastUtils.showShortToast(getApplicationContext(), getString(R.string.unfinished));
    }

    public void slideDown() {
      mMessenger.requestToCloseSlidePanelIfExpanded(true);
    }

    public void more() {
    }
  }

  public static class ListenerHandler implements DefaultInterface.OnSeekBarChangeListener {

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
      PlayerManager.getInstance().setSeek(seekBar.getProgress());
    }
  }

  public static class PlayerViewModel extends ViewModel {
    public final State<String> title = new State<>(Utils.getApp().getString(R.string.app_name));
    public final State<String> artist = new State<>(Utils.getApp().getString(R.string.app_name));
    public final State<String> coverImg = new State<>("");
    public final State<Drawable> placeHolder = new State<>(Objects.requireNonNull(ContextCompat.getDrawable(Utils.getApp(), R.drawable.bg_album_default)));
    public final State<Integer> maxSeekDuration = new State<>(0);
    public final State<Integer> currentSeekPosition = new State<>(0);
    public final State<Boolean> isPlaying = new State<>(false, true);
    public final State<MaterialDrawableBuilder.IconValue> playModeIcon = new State<>(MaterialDrawableBuilder.IconValue.REPEAT);

    {
      if (PlayerManager.getInstance().getRepeatMode() == PlayingInfoManager.RepeatMode.LIST_CYCLE) {
        playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT);
      } else if (PlayerManager.getInstance().getRepeatMode() == PlayingInfoManager.RepeatMode.SINGLE_CYCLE) {
        playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT_ONCE);
      } else {
        playModeIcon.set(MaterialDrawableBuilder.IconValue.SHUFFLE);
      }
    }
  }

}
