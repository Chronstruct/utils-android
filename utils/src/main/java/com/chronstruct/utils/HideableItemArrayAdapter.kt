package com.chronstruct.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class HideableItemArrayAdapter(context: Context, resource: Int, items: List<String>) : ArrayAdapter<String>(context, resource, items) {
  var hiddenItemIndex = 0

  override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {

    // If this is the initial dummy entry, make it hidden
    if (position == hiddenItemIndex) {
      val hiddenView = TextView(context)
      hiddenView.height = 0
      hiddenView.visibility = View.GONE
      return hiddenView
    }

    // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
    parent.isVerticalScrollBarEnabled = false

    // Pass convertView as null to prevent reuse of special case views
    return super.getDropDownView(position, null, parent)
  }
}