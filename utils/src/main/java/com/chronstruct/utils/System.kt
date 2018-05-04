package com.chronstruct.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager

fun getScreenWidth(): Int = Resources.getSystem().displayMetrics.widthPixels

fun getScreenHeight(): Int = Resources.getSystem().displayMetrics.heightPixels

fun getStatusBarHeight(context: Context): Int {
  var result = 0

  val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")

  if (resourceId > 0) {
    result = context.resources.getDimensionPixelSize(resourceId)
  }
  return result
}

/**
 * from https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard/17789187#17789187
 */
fun hideKeyboard(activity: Activity) {
  val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
  //Find the currently focused view, so we can grab the correct window token from it.
  var view = activity.currentFocus
  //If no view currently has focus, create a new one, just so we can grab a window token from it
  if (view == null) {
    view = View(activity)
  }
  imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * For in DialogFragment, where hideKeyboard(getActivity()); won't work
 *
 * Usage:
 *   hideKeyboardFrom(ctx, container.rootView)
 *
 * from https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard/17789187#17789187
 */
fun hideKeyboardFrom(context: Context, view: View) {
  val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(view.windowToken, 0)
}