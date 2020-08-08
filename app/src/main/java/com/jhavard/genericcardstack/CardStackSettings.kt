package com.jhavard.genericcardstack

import com.jhavard.genericcardstack.utils.GreaterThanZeroDelegate

class CardStackSettings(
    val swipeDirection: SwipeDirection = SwipeDirection.HORIZONTAL
) {
    constructor(
        maxItemsDisplayed: Int, scaleFactor: Float, swipeDirection: SwipeDirection
    ) : this(swipeDirection) {
        this.maxItemsDisplayed = maxItemsDisplayed
        this.scaleFactor = scaleFactor
    }

    var maxItemsDisplayed: Int by GreaterThanZeroDelegate(defaultValue = 3)
    var scaleFactor: Float by GreaterThanZeroDelegate(defaultValue = 0.8F)

    enum class SwipeDirection {
        HORIZONTAL, VERTICAL
    }
}