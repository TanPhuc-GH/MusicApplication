

package com.kunminx.puremusic.player.domain.message;

import androidx.lifecycle.ViewModel;

import com.kunminx.architecture.domain.result.MutableResult;
import com.kunminx.architecture.domain.result.Result;

public class PageMessenger extends ViewModel {

  private final MutableResult<Boolean> toCloseSlidePanelIfExpanded = new MutableResult<>();

  private final MutableResult<Boolean> toCloseActivityIfAllowed = new MutableResult<>();

  private final MutableResult<Boolean> toOpenOrCloseDrawer = new MutableResult<>();

  private final MutableResult<Boolean> toAddSlideListener = new MutableResult<>();

  public Result<Boolean> isToAddSlideListener() {
    return toAddSlideListener;
  }

  public Result<Boolean> isToCloseSlidePanelIfExpanded() {
    return toCloseSlidePanelIfExpanded;
  }

  public Result<Boolean> isToCloseActivityIfAllowed() {
    return toCloseActivityIfAllowed;
  }

  public Result<Boolean> isToOpenOrCloseDrawer() {
    return toOpenOrCloseDrawer;
  }

  public void requestToCloseActivityIfAllowed(boolean allow) {
    toCloseActivityIfAllowed.setValue(allow);
  }

  public void requestToOpenOrCloseDrawer(boolean open) {
    toOpenOrCloseDrawer.setValue(open);
  }

  public void requestToCloseSlidePanelIfExpanded(boolean close) {
    toCloseSlidePanelIfExpanded.setValue(close);
  }

  public void requestToAddSlideListener(boolean add) {
    toAddSlideListener.setValue(add);
  }
}
