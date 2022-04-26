package com.example.test.views

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.activity_and_fragments.hosts.PresentHost
import com.example.test.databinding.HeaderBinding

class HeaderView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding: HeaderBinding

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.header, this, true)
        binding = HeaderBinding.bind(this)
        initAttrs(attrs, defStyleAttr, defStyleRes)
    }

    private fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
    }

    fun setBack(backed:Boolean, obj: HeaderBack?, activity:FragmentActivity, owner: LifecycleOwner) {
        with(binding) {
            if (backed&& obj!=null){
                back.visibility = View.VISIBLE
                back.setOnClickListener {
                    obj.back()
                }
                val callback = activity.onBackPressedDispatcher.addCallback(owner) {
                    obj.back()
                }
            }else{
                back.visibility = View.GONE
            }
        }
    }

    interface HeaderBack {
        fun back()
    }
}