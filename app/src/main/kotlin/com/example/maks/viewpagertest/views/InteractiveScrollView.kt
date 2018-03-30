package com.example.maks.viewpagertest.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView


///////////////////////////////////////////////////////////////////////////
// Interactive Scroll View
///////////////////////////////////////////////////////////////////////////

class InteractiveScrollView : ScrollView {
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context) : super(context)

    private var scrollWatcher = OverScrollWatcher()

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val scrollOffset = computeVerticalScrollRange() - computeVerticalScrollExtent() - scrollY
        val scrollYStart = scrollY
        scrollWatcher.offset = when {
            scrollYStart < OverScrollWatcher.MAX_DIFF -> scrollYStart
            else -> scrollOffset
        }

        return when {
            (ev?.action == MotionEvent.ACTION_CANCEL || ev?.action == MotionEvent.ACTION_UP) -> {
                scrollWatcher.reachBorder = false
                super.onInterceptTouchEvent(ev)
            }
            scrollWatcher.isScrolled() -> {
                false
            }
            else -> super.onInterceptTouchEvent(ev)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (scrollWatcher.isScrolled()) {
            this.parent.requestDisallowInterceptTouchEvent(false)
            scrollWatcher.reachBorder = false
        }
        return super.onTouchEvent(ev)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val lastView = getChildAt(childCount - 1) as View
        val bottomDiff = lastView.bottom - (height + scrollY)

        val firstView = getChildAt(0) as View
        val topDiff = firstView.top - scrollY
        scrollWatcher.reachBorder = bottomDiff == 0 || topDiff == 0

        super.onScrollChanged(l, t, oldl, oldt)
    }

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        if (clampedY) scrollWatcher.reachBorder = clampedY
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
    }


    ///////////////////////////////////////////////////////////////////////////
    // OverScroll Watcher - Helper Class
    ///////////////////////////////////////////////////////////////////////////

    private class OverScrollWatcher {
        companion object {
            const val MAX_DIFF = 30
        }

        var reachBorder: Boolean = false
        var offset: Int = MAX_DIFF

        fun isScrolled() = reachBorder && offset < MAX_DIFF
    }
}