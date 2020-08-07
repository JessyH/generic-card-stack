package com.jhavard.genericcardstack

class CardStackSettings(
    val maxItemsDisplayed: Int = 3,
    val swipeDirection: SwipeDirection = SwipeDirection.HORIZONTAL
) {
    enum class SwipeDirection {
        HORIZONTAL, VERTICAL
    }
}