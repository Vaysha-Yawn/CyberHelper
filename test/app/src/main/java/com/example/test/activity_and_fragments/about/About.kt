package com.example.test.activity_and_fragments.about

import android.os.Binder
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.test.R
import com.example.test.databinding.AboutBinding


class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)
        val goalSpinner = findViewById<Spinner>(R.id.goalSpinner)
        val arr = arrayOf("Одна цель", "Несколько целей")
        goalSpinner.adapter = ArrayAdapter(this, R.layout.spinner_item, arr)
        goalSpinner.setPopupBackgroundDrawable(resources.getDrawable(R.drawable.bcg_blue_line))
        //goalSpinner
        /*} catch (e: Exception) {
            Toast.makeText(this, "$e", Toast.LENGTH_LONG).show()
        }
*/
    }
}


