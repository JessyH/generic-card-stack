package com.jhavard.genericcardstack

import androidx.recyclerview.widget.RecyclerView

/**
 * Responsible for laying out the child views (cards) and performing the swipe behavior
 */
class CardStackLayoutManager : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (recycler == null || itemCount == 0) return

        detachAndScrapAttachedViews(recycler)

        for (position in 0 until itemCount) {
            val view = recycler.getViewForPosition(position)

            addView(view, 0)
            measureChildWithMargins(view, 0, 0)
            layoutDecoratedWithMargins(
                view, paddingLeft, paddingTop, width - paddingLeft, height - paddingBottom
            )

            if (itemCount > 0) {
                view.translationY = -30f * position
                view.scaleX = 0.8f - 0.05f * position
                view.scaleY = 0.8f
            }
        }
    }
}