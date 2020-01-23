package com.qartf.fallingwords.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.qartf.blacklanechallenge.util.ext.replaceFragment
import com.qartf.fallingwords.R

class StartFragment : Fragment() {

    internal lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_start, container, false)

        rootView.findViewById<Button>(R.id.startButton).setOnClickListener {
            (activity as AppCompatActivity).replaceFragment(GameFragment(), R.id.container)
        }
        return rootView
    }
}