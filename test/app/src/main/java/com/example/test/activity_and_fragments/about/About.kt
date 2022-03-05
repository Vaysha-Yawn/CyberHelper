package com.example.test.activity_and_fragments.about

import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R


class About : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.skill_test)

        } catch (e: Exception) {
            Toast.makeText(this, "$e", Toast.LENGTH_LONG).show()
        }

    }
}


