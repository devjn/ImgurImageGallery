package com.github.devjn.imgurimagegallery.widgets

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by @author Jahongir on 05-May-18
 * devjn@jn-arts.com
 * GirdSpacing
 *
 * @param spanCount number of spans/columns
 * @param spacing in pixels
 * @param includeEdge if true item is spaced from list edge
 */
class GridSpacingItemDecoration(private val spacing: Int,
                                private val includeEdge: Boolean = false) : RecyclerView.ItemDecoration() {

    private val halfSpace: Int = spacing / 2

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        if (parent.paddingLeft != halfSpace) {
            parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace)
            parent.clipToPadding = false
        }

        outRect.top = halfSpace
        outRect.bottom = halfSpace
        outRect.left = halfSpace
        outRect.right = halfSpace
    }

}
