package com.example.test.components.views.compact_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.components.views.drop_down.DropDownAdapterRV
import com.example.test.databinding.CardRvCompactDdBinding
import com.example.test.databinding.ViewCompactBinding

class CompactViewDD(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding: ViewCompactBinding
    private var defMain: String = ""
    private var titleText: String = ""
    private var addText: String = ""
    private var listDD = listOf<String>()
    private var values = mutableListOf<Int?>()
    private var obj: OnDDSelected? = null
    private lateinit var adapterDD: AbstractAdapterRV<Int?>

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
        inflater.inflate(R.layout.view_compact, this, true)
        binding = ViewCompactBinding.bind(this)
        initAttrs(attrs, defStyleAttr, defStyleRes)
    }

    private fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CompactViewDD,
            defStyleAttr,
            defStyleRes
        )

        with(binding) {

            defMain = typedArray.getString(
                R.styleable.CompactViewDD_CV_DD_item_defMain
            )?:""

            titleText = typedArray.getString(
                R.styleable.CompactViewDD_CV_DD_title
            )?:""

            addText = typedArray.getString(
                R.styleable.CompactViewDD_CV_DD_add_text
            )?:""

            title.text = titleText

            add.text = addText

            adapterDD = AbstractAdapterRV<Int?>(
                object : AbstractTemplateHolder.InitBinding<Int?> {
                    override fun funBinding(
                        view: View,
                        param: Int?,
                        updView: AbstractTemplateHolder.UpdView,
                        pos: Int
                    ) {
                        val binding = CardRvCompactDdBinding.bind(view)
                        with(binding) {
                            delete.setOnClickListener {
                                values.removeAt(pos)
                                updView.updateView()
                            }
                            if(param == null){
                                DD.setMainText(defMain)
                            }else{
                                DD.setMainText(listDD[param])
                            }
                            DD.setDDArrayAndListener(
                                listDD,
                                object : DropDownAdapterRV.TemplateHolder.WhenValueTo {
                                    override fun whenValueTo(position: Int) {
                                        values[pos] = position
                                        if (obj != null) {
                                            obj?.onDDSelected(pos, position, listDD[position])
                                        }
                                    }
                                },
                                null
                            )
                        }
                    }
                },
                R.layout.card_rv_compact_dd
            )
            RV.layoutManager =
                LinearLayoutManager(RV.context, LinearLayoutManager.VERTICAL, false)
            RV.adapter = adapterDD

            add.setOnClickListener {
                values.add(null)
                adapterDD.setData(values)
            }
        }

        typedArray.recycle()

    }

    fun setData(listValues: MutableList<Int?>) {
        values = listValues
        adapterDD.setData(values)
    }

    fun setListener(listVariants: List<String>, obj: OnDDSelected?) {
        this.listDD = listVariants
        this.obj = obj
    }

    fun setDefMain(defMain: String){
        this.defMain = defMain
    }

    fun setTitle(title:String){
        this.titleText = title
    }

    fun setAddText(addText:String){
        this.addText = addText
    }

    fun getData(): MutableList<Int?> {
        return values
    }

    interface OnDDSelected {
        fun onDDSelected(posDD: Int, positionAnswer: Int, result: String)
    }


}