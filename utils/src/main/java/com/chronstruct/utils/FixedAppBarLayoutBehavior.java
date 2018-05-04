package com.chronstruct.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * This AppBar is a combination of two things:
 * 1. Don't scroll if there isn't content to scroll (setDragCallback)
 * 2. Fix click timing after scroll (the restK
 * <p>
 * add
 * app:layout_behavior="com.chronstruct.tally.util.FixedAppBarLayoutBehavior"
 * to your AppBarLayout
 */
public class FixedAppBarLayoutBehavior extends AppBarLayout.Behavior {

  private boolean shouldScroll = true;

  /**
   * This constructor used for the static, xml usage
   */
  public FixedAppBarLayoutBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);

    /*
     * Normally, AppBarLayout scrolls even if the scroll content fits the screen because it is draggable
     * This class prevents the drag so that only the associated scrollable will scroll it
     *
     * See https://stackoverflow.com/questions/34108501/how-to-disable-scrolling-of-appbarlayout-in-coordinatorlayout
     */
    setDragCallback(new DragCallback() {
      @Override
      public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
        return false;
      }
    });
  }

  /*
   * Workaround AppBarLayout.Behavior for https://issuetracker.google.com/66996774
   *
   * See https://gist.github.com/chrisbanes/8391b5adb9ee42180893300850ed02f2 for
   * example usage.
   */
  @Override
  public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target,
      int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
    super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,
        dxUnconsumed, dyUnconsumed, type);
    stopNestedScrollIfNeeded(dyUnconsumed, child, target, type);
  }

  @Override
  public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child,
      View target, int dx, int dy, int[] consumed, int type) {
    super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    stopNestedScrollIfNeeded(dy, child, target, type);
  }

  private void stopNestedScrollIfNeeded(int dy, AppBarLayout child, View target, int type) {
    if (type == ViewCompat.TYPE_NON_TOUCH) {
      final int currOffset = getTopAndBottomOffset();
      if ((dy < 0 && currOffset == 0)
          || (dy > 0 && currOffset == -child.getTotalScrollRange())) {
        ViewCompat.stopNestedScroll(target, ViewCompat.TYPE_NON_TOUCH);
      }
    }
  }

  /**
   * Able to prevent scrolling the appbar
   * see https://stackoverflow.com/a/48086783/2444069
   */
  @Override
  public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {
    // super() call needed to prevent scrolling when Recycler isn't tall enough to need scroll
    return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type) && shouldScroll;
  }

  @Override
  public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
    if(shouldScroll){
      return super.onTouchEvent(parent, child, ev);
    }else{
      return false;
    }
  }

  public void setShouldScroll(boolean shouldScroll){
    this.shouldScroll = shouldScroll;
  }

  public boolean getShouldScroll(){
    return shouldScroll;
  }
}
