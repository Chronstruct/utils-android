package com.chronstruct.utils

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.graphics.Rect
import android.support.annotation.LayoutRes
import android.support.design.widget.AppBarLayout
import android.support.v7.view.menu.MenuBuilder
import android.view.*
import android.support.v4.app.Fragment as SupportFragment


/**
 * Note: Fragments should still use `inflater.inflate()`, not this
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
  return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

/*
fun View.setIsVisible(isVisible: Boolean) {
    if (isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}
*/

fun View.addHitSlop(extraPadding: Int) {
  val parent = this.parent as View
  parent.post {
    val rect = Rect()
    this.getHitRect(rect)
    rect.top -= extraPadding
    rect.right += extraPadding
    rect.bottom += extraPadding
    rect.left -= extraPadding
    parent.touchDelegate = TouchDelegate(rect, this)
  }
}

// from https://antonioleiva.com/kotlin-ongloballayoutlistener/
inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
  viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
    override fun onGlobalLayout() {
      if (measuredWidth > 0 && measuredHeight > 0) {
        viewTreeObserver.removeOnGlobalLayoutListener(this)
        f()
      }
    }
  })
}

// from https://github.com/pawegio/KAndroid/blob/master/kandroid/src/main/kotlin/com/pawegio/kandroid/KView.kt
var View.visible
  get() = visibility == View.VISIBLE
  set(value) {
    visibility = if (value) View.VISIBLE else View.GONE
  }

//fun ImageView.imageUrl(url: String) {
//  Glide.with(context).load(url).into(this)
//}

// https://stackoverflow.com/a/43411336/2444069
@SuppressLint("RestrictedApi")
fun MenuBuilder.showIconsWithText() {
  setOptionalIconsVisible(true)
}

/**
 * Filters emissions to distinct objects
 * see https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1
 */

fun <T> LiveData<T>.getDistinct(): LiveData<T> {
  val distinctLiveData = MediatorLiveData<T>()
  distinctLiveData.addSource(this, object : Observer<T> {
    private var initialized = false
    private var lastObj: T? = null
    override fun onChanged(obj: T?) {
      if (!initialized) {
        initialized = true
        lastObj = obj
        distinctLiveData.postValue(lastObj)
      } else if ((obj == null && lastObj != null)
          || obj != lastObj) {
        lastObj = obj
        distinctLiveData.postValue(lastObj)
      }
    }
  })
  return distinctLiveData
}

fun AppBarLayout.isExpanded() : Boolean = this.height - this.bottom == 0