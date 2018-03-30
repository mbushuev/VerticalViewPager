package com.example.maks.viewpagertest.ui.screens.surveys

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.maks.viewpagertest.R
import org.jetbrains.anko.bundleOf
import java.util.Random


///////////////////////////////////////////////////////////////////////////
// Question Fragment
///////////////////////////////////////////////////////////////////////////

class QuestionFragment : Fragment() {

    companion object {
        private const val CARD_NAME = "card_name"

        fun create(name: String): QuestionFragment {
            val fragment = QuestionFragment()
            val bundle = bundleOf(CARD_NAME to name)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val name by lazy { arguments?.getString(CARD_NAME, "") ?: "" }
    private val items = arrayOf(R.layout.item1, R.layout.item2)
    private val position: Int = Random().nextInt(2)

    ///////////////////////////////////////////////////////////////////////////
    // Lifecycle
    ///////////////////////////////////////////////////////////////////////////

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(items[position], container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view?.let {
            it.findViewById<TextView>(R.id.name).text = name
            val card = it.findViewById<CardView>(R.id.cardView)
            card.setOnTouchListener { _, _ ->
                true
            }
        }
    }
}