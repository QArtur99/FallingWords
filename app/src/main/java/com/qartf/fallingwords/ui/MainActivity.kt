package com.qartf.fallingwords.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qartf.blacklanechallenge.util.ext.setFirstFragment
import com.qartf.fallingwords.R
import com.qartf.fallingwords.util.JsonUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) setFirstFragment(StartFragment(), R.id.container)
    }
}
