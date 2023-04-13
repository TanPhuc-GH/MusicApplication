package com.hcmute.puremusic;

import android.os.Bundle;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.hcmute.architecture.ui.page.BaseActivity;
import com.hcmute.puremusic.player.domain.message.DrawerCoordinateManager;
import com.hcmute.puremusic.player.domain.message.PageMessenger;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.state.State;

public class MainActivity extends BaseActivity {

  private MainActivityViewModel mStates;
  private PageMessenger mMessenger;
  private boolean mIsListened = false;

  @Override
  protected void initViewModel() {
    mStates = getActivityScopeViewModel(MainActivityViewModel.class);
    mMessenger = getApplicationScopeViewModel(PageMessenger.class);
  }

  @Override
  protected DataBindingConfig getDataBindingConfig() {

    return new DataBindingConfig(R.layout.activity_main, com.hcmute.puremusic.BR.vm, mStates)
            .addBindingParam(com.hcmute.puremusic.BR.listener, new ListenerHandler());
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    init();
  }

  private void init() {
    mMessenger.isToCloseActivityIfAllowed().observe(this, aBoolean -> {
      NavController nav = Navigation.findNavController(this, R.id.main_fragment_host);
      if (nav.getCurrentDestination() != null && nav.getCurrentDestination().getId() != R.id.mainFragment) {
        nav.navigateUp();

      } else if (Boolean.TRUE.equals(mStates.isDrawerOpened.get())) {

        mMessenger.requestToOpenOrCloseDrawer(false);

      } else {
        super.onBackPressed();
      }
    });

    mMessenger.isToOpenOrCloseDrawer().observe(this, aBoolean -> {

      mStates.openDrawer.set(aBoolean);

    });

    DrawerCoordinateManager.getInstance().isEnableSwipeDrawer().observe(this, aBoolean -> {

      mStates.allowDrawerOpen.set(aBoolean);

    });
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (!mIsListened) {

      mMessenger.requestToAddSlideListener(true);

      mIsListened = true;
    }
  }

  @Override
  public void onBackPressed() {

    mMessenger.requestToCloseSlidePanelIfExpanded(true);
  }

  public class ListenerHandler extends DrawerLayout.SimpleDrawerListener {
    @Override
    public void onDrawerOpened(View drawerView) {
      super.onDrawerOpened(drawerView);
      mStates.isDrawerOpened.set(true);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
      super.onDrawerClosed(drawerView);
      mStates.isDrawerOpened.set(false);
      mStates.openDrawer.set(false);
    }
  }

  public static class MainActivityViewModel extends ViewModel {

    public final State<Boolean> isDrawerOpened = new State<>(false);

    public final State<Boolean> openDrawer = new State<>(false);

    public final State<Boolean> allowDrawerOpen = new State<>(true);

  }
}
