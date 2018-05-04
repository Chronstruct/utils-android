package com.chronstruct.utils

import android.content.Context
import android.support.v7.widget.LinearLayoutManager

/**
 * thanks to https://stackoverflow.com/questions/30531091/how-to-disable-recyclerview-scrolling
 */
class DisableableScrollLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
  var hasScrollingEnabled = true

  override fun canScrollVertically(): Boolean {
    return hasScrollingEnabled && super.canScrollVertically()
  }

}

