package com.example.maks.viewpagertest.ui.screens.surveys

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import com.example.maks.viewpagertest.R
import com.example.maks.viewpagertest.views.VerticalViewPager


///////////////////////////////////////////////////////////////////////////
// Survey Activity Screen
///////////////////////////////////////////////////////////////////////////

class SurveyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        val pager = findViewById<VerticalViewPager>(R.id.sa_vp_questions)

        val pagerAdapter = QuestionCardPagerAdapter(supportFragmentManager)
        pager.adapter = pagerAdapter

        pager.offscreenPageLimit = 3
    }
}


///////////////////////////////////////////////////////////////////////////
// Question Card Pager Adapter
///////////////////////////////////////////////////////////////////////////

class QuestionCardPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val pages: List<Fragment> = listOf(
            QuestionFragment.create("Card 1"),
            QuestionFragment.create("Card 2"),
            QuestionFragment.create("Card 3"),
            QuestionFragment.create("Card 4"),
            QuestionFragment.create("Card 5"),
            QuestionFragment.create("Card 6"),
            QuestionFragment.create("Card 7"),
            QuestionFragment.create("Card 8"),
            QuestionFragment.create("Card 9"),
            QuestionFragment.create("FINISH!")
    )

    override fun getCount(): Int = pages.size

    override fun getItem(position: Int): Fragment = pages[position]
}