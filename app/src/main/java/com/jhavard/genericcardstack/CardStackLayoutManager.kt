package com.jhavard.genericcardstack

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.absoluteValue
import kotlin.math.min

/**
 * Responsible for laying out the child views (cards) and performing the swipe behavior
 */
class CardStackLayoutManager(
    private val listener: CardStackListener,
    private val settings: CardStackSettings
) : RecyclerView.LayoutManager() {

    private var currentDx = 0
    private var hasSwipeEnded: Boolean = false

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (recycler == null || itemCount == 0) return

        detachAndScrapAttachedViews(recycler)

        for (position in 0 until min(itemCount, settings.maxItemsDisplayed)) {
            val view = recycler.getViewForPosition(position)

            addView(view, 0)
            measureChildWithMargins(view, 0, 0)
            layoutDecoratedWithMargins(
                view, paddingLeft, paddingTop, width - paddingLeft, height - paddingBottom
            )

            if (position == 0) {
                if (hasSwipeEnded) {
                    currentDx = 0

                    if (view.translationX.absoluteValue > width * 0.40) {
                        removeAndRecycleView(view, recycler)
                        Handler(Looper.getMainLooper()).post {
                            listener.onCardSwiped()
                        }
                    }
                }
                view.translationX = currentDx.toFloat()
                updateItemRotation(view)
            }

            updateItemTranslation(view, position)
            updateItemScale(view, position)
        }
    }


    // Scroll
    ////////////////////////////////////////////////////////////////////////////////////////////////

    override fun canScrollHorizontally(): Boolean =
        settings.swipeDirection == CardStackSettings.SwipeDirection.HORIZONTAL

    override fun canScrollVertically(): Boolean =
        settings.swipeDirection == CardStackSettings.SwipeDirection.VERTICAL

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        currentDx -= dx
        onLayoutChildren(recycler, state)
        return dx
    }

    override fun onScrollStateChanged(state: Int) {
        when (state) {
            RecyclerView.SCROLL_STATE_DRAGGING -> hasSwipeEnded = false
            RecyclerView.SCROLL_STATE_IDLE -> {
                hasSwipeEnded = true
                requestLayout()
            }
            RecyclerView.SCROLL_STATE_SETTLING -> hasSwipeEnded = false
        }
    }


    // Private methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private fun updateItemTranslation(view: View, position: Int) {
        view.translationY = -30f * position
    }

    private fun updateItemScale(view: View, position: Int) {
        view.scaleX = settings.scaleFactor - 0.05f * position
        view.scaleY = settings.scaleFactor
    }

    private fun updateItemRotation(view: View) {
        // TODO
    }
}