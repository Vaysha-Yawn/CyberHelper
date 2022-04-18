package com.example.test.activity_and_fragments.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.views.PlusMinusView


class About : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)
        val pm = findViewById<PlusMinusView>(R.id.PM)
        pm.setListener(4, 0)
        pm.getValue()
    }
}


