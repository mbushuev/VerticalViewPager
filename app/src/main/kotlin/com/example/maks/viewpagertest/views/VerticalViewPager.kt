package com.example.maks.viewpagertest.views

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.maks.viewpagertest.R


///////////////////////////////////////////////////////////////////////////
// Vertical View Pager
///////////////////////////////////////////////////////////////////////////

class VerticalViewPager : ViewPager {

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
    }

    private var mSwipeOrientation: Int = 0

    constructor(context: Context) : super(context) {
        mSwipeOrientation = HORIZONTAL
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setSwipeOrientation(context, attrs)

        val marginPx = resources.getDimensionPixelSize(R.dimen.page_margin)
        pageMargin = marginPx
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return super.onTouchEvent(swapXY(event))
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val intercepted = super.onInterceptTouchEvent(swapXY(event))
        swapXY(event)
        return intercepted
    }

    private fun setSwipeOrientation(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerticalViewPager)
        mSwipeOrientation = typedArray.getInteger(R.styleable.VerticalViewPager_swipe_orientation, 0)
        typedArray.recycle()
        initSwipeMethods()
    }

    private fun initSwipeMethods() {
        if (mSwipeOrientation == VERTICAL) {
            setPageTransformer(false, VerticalPageTransformer())
        }
    }

    private fun swapXY(event: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()

        val newX = event.y / height * width
        val newY = event.x / width * height

        event.setLocation(newX, newY)
        return event
    }


    ///////////////////////////////////////////////////////////////////////////
    // Vertical Page Transformer
    ///////////////////////////////////////////////////////////////////////////

    private inner class VerticalPageTransformer : ViewPager.PageTransformer {

        private val topShift = 0.025f
        private val scaleShift = 1 - topShift * 1.5f
        private val scaleShiftBack = 1 - scaleShift * 0.95f

        override fun transformPage(page: View, position: Float) {
            when {
                position <= -2 -> {
                    page.scaleX = scaleShift + position * scaleShiftBack
                    page.scaleY = scaleShift + position * scaleShiftBack
                    page.translationX = page.width * -position
                    page.translationY = page.height * -position * topShift * 2.5f
                    page.alpha = 2.75f + position
                }
                position < 0 && position > -2 -> {
                    page.scaleX = scaleShift + position * scaleShiftBack
                    page.scaleY = scaleShift + position * scaleShiftBack
                    page.translationX = page.width * -position
                    page.translationY = page.height * -position * topShift * 2.5f
                    page.alpha = 1f
                }
                position == 0f -> {
                    page.scaleX = scaleShift
                    page.scaleY = scaleShift
                    page.translationX = 0f
                    page.translationY = 0f
                    page.alpha = 1f
                }
                position > 0 -> {
                    page.scaleX = scaleShift
                    page.scaleY = scaleShift
                    page.translationX = page.width * -position
                    page.translationY = position * page.height
                    page.alpha = 1f
                }
                else -> page.alpha = 1f
            }
        }
    }
}