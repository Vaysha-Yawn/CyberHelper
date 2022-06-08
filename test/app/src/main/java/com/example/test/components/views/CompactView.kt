package com.example.test.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.test.R
import com.example.test.adapters.DropDownAdapterRV
import com.example.test.databinding.CardRvCompactDdBinding
import com.example.test.databinding.ViewCompactBinding
import java.util.*

class CompactView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding: ViewCompactBinding
    private var color: Int? = null
    private var type: Int? = null
    private var hint: String? = null

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
        binding = ViewCompactBinding.bind(this)
        initAttrs(attrs, defStyleAttr, defStyleRes)
    }

    private fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CompactView,
            defStyleAttr,
            defStyleRes
        )

        with(binding) {

            color = typedArray.getColor(
                R.styleable.CompactView_CV_item_color,
                ContextCompat.getColor(context, R.color.green)
            )

            type = typedArray.getInteger(
                R.styleable.CompactView_CV_item_type,
                0
            )

            hint = typedArray.getString(
                R.styleable.CompactView_CV_item_hint
            )

            val titleText = typedArray.getString(
                R.styleable.CompactView_CV_title
            )

            val addText = typedArray.getString(
                R.styleable.CompactView_CV_add_text
            )

            title.text = titleText

            add.text = addText
            val adapter = AdapterRV<List<String>>(
                HolderData<List<String>>(
                    object : TemplateHolder.Delete {
                        override fun delete(position: Int) {
                            //
                        }
                    },
                    object: TemplateHolder.InitBinding<List<String>>{
                        override fun funBinding(
                            view: View,
                            param: List<String>,
                            delete: TemplateHolder.Delete,
                            updView: TemplateHolder.UpdView
                        ) {
                            val binding = CardRvCompactDdBinding.bind(view)
                            with(binding){
                                DD.setMainText(param[0])
                                DD.setDDArrayAndListener(param, object: DropDownAdapterRV.TemplateHolder.WhenValueTo{
                                    override fun whenValueTo(position: Int) {
                                        //
                                    }
                                }, null)
                            }
                        }
                    },
                    R.layout.card_rv_compact_dd
                )
            )
            RV.adapter
        }
        typedArray.recycle()

    }

    fun <T> setData(data: T) {
        with(binding) {
            //
        }
    }

    fun getValue() {

    }

    class AdapterRV<T>(
        private val holder: HolderData<T>,
    ) :
        RecyclerView.Adapter<TemplateHolder<T>>(), TemplateHolder.UpdView {
        var list: MutableList<T> = mutableListOf<T>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateHolder<T> {
            val view =
                LayoutInflater.from(parent.context).inflate(holder.layout, parent, false)
            return TemplateHolder<T>(view, holder.delete, this, holder.funBinding)
        }

        override fun onBindViewHolder(holder: TemplateHolder<T>, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun setData(list: MutableList<T>) {
            this.list = list
            notifyDataSetChanged()
        }

        override fun updateView() {
            notifyDataSetChanged()
        }

    }

    data class HolderData<T>(
        val delete: TemplateHolder.Delete,
        val funBinding: TemplateHolder.InitBinding<T>,
        val layout: Int
    )

    class TemplateHolder<T>(
        private val view: View,
        private val delete: Delete,
        private val updView: UpdView,
        private val funBinding: InitBinding<T>,
    ) : RecyclerView.ViewHolder(view) {

        fun bind(param: T) {
            funBinding.funBinding(view, param,  delete, updView)
        }

        interface UpdView {
            fun updateView()
        }

        interface Delete {
            fun delete(position: Int)

        }

        interface InitBinding<T> {
            fun funBinding(view:View, param: T, delete: Delete, updView: UpdView)
        }

    }

    // необходимо сделать абстрактый адаптер и холдер для него, чтобы по одним параметрам создавать какой хочешь
    // параметры которые мы хотим передать:
    // разные лайауты и биндинги
    // рзные действия с ними
    //

    /*class PMAdapterRV(
        private val deleteMod: PMTemplateHolder.DeleteMod,
        private val listener: PMTemplateHolder.PutModValue,
    ) :
        RecyclerView.Adapter<PMTemplateHolder>(), PMTemplateHolder.updView {

        var list = mutableListOf<Mod>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PMTemplateHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.card_mod, parent, false)
            return PMTemplateHolder(view, deleteMod, this, listener)
        }

        override fun onBindViewHolder(holder: PMTemplateHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun setData(list: MutableList<Mod>) {
            this.list = list
            notifyDataSetChanged()
        }

        override fun updateView() {
            notifyDataSetChanged()
        }

    }

    class DDAdapterRV(
        private val deleteMod: DDTemplateHolder.DeleteMod,
        private val listener: DDTemplateHolder.PutModValue,
    ) :
        RecyclerView.Adapter<DDTemplateHolder>(), DDTemplateHolder.updView {

        var list = mutableListOf<Mod>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DDTemplateHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.card_mod, parent, false)
            return DDTemplateHolder(view, deleteMod, this, listener)
        }

        override fun onBindViewHolder(holder: DDTemplateHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun setData(list: MutableList<Mod>) {
            this.list = list
            notifyDataSetChanged()
        }

        override fun updateView() {
            notifyDataSetChanged()
        }

    }



    class EditTextAdapterRV(
        private val deleteMod: EditTextTemplateHolder.DeleteMod,
        private val listener: EditTextTemplateHolder.PutModValue,
    ) :
        RecyclerView.Adapter<EditTextTemplateHolder>(), EditTextTemplateHolder.updView {

        var list = mutableListOf<Mod>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditTextTemplateHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.card_mod, parent, false)
            return EditTextTemplateHolder(view, deleteMod, this, listener)
        }

        override fun onBindViewHolder(holder: EditTextTemplateHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun setData(list: MutableList<Mod>) {
            this.list = list
            notifyDataSetChanged()
        }

        override fun updateView() {
            notifyDataSetChanged()
        }

    }

    class StringAdapterRV(
        private val deleteMod: StringTemplateHolder.DeleteMod,
        private val listener: StringTemplateHolder.EditString,
    ) :
        RecyclerView.Adapter<StringTemplateHolder>(), StringTemplateHolder.updView {

        var list = mutableListOf<String>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringTemplateHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.card_mod, parent, false)
            return StringTemplateHolder(view, deleteMod, this, listener)
        }

        override fun onBindViewHolder(holder: StringTemplateHolder, position: Int) {
            holder.bind(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun setData(list: MutableList<String>) {
            this.list = list
            notifyDataSetChanged()
        }

        override fun updateView() {
            notifyDataSetChanged()
        }

    }

    class StringTemplateHolder(
        view: View,
        private val delete: DeleteMod,
        private val updViewr: updView,
        private val listener: EditString,
    ) : RecyclerView.ViewHolder(view) {
        private val binding = CardRvCompactStringBinding.bind(view)

        fun bind( value:String, ) = with(binding) {
            stringEditableText.text = value
            stringEditableEdit.setOnClickListener {
                listener.editString(adapterPosition, value)
            }
            delete.setOnClickListener { view ->
                this@StringTemplateHolder.delete.deleteMod(adapterPosition)
                updViewr.updateView()
            }
        }

        interface updView {
            fun updateView()
        }

        interface DeleteMod {
            fun deleteMod(position: Int)
        }

        interface EditString {
            fun editString(position: Int, value:String)
        }

    }

    class PMTemplateHolder(
        view: View,
        private val delete: DeleteMod,
        private val updViewr: updView,
        private val listener: PutModValue,
    ) : RecyclerView.ViewHolder(view), DropDownAdapterRV.TemplateHolder.WhenValueTo, PlusMinusView.NumberEvent {
        private val binding = CardModBinding.bind(view)

        fun bind(style:Int, value:String, ) = with(binding) {

                PMLinear.visibility = View.GONE
                DD.visibility = View.VISIBLE
                DD.setMainText(value)
                DD.setDDArrayAndListener(DSpecialGameData().modName, this@TemplateHolder, null)

                PMLinear.visibility = View.VISIBLE
                DD.visibility = View.GONE
                PM.setValue(value.toInt())
                PM.setListener(99, 0, this@TemplateHolder)

            delete.setOnClickListener { view ->
                this@TemplateHolder.delete.deleteMod(adapterPosition)
                updViewr.updateView()
            }
        }

        interface updView {
            fun updateView()
        }

        interface DeleteMod {
            fun deleteMod(position: Int)
        }

        interface PutModValue {
            fun putModValue(position: Int, value:Int)
        }

        override fun whenValueTo(position: Int) {
            listener.putModValue(adapterPosition, position)
        }

        override fun numberEvent(number: Int) {
            listener.putModValue(adapterPosition, number)
        }
    }



    class DDTemplateHolder(
        view: View,
        private val delete: DeleteMod,
        private val updViewr: updView,
        private val listener: PutModValue,
    ) : RecyclerView.ViewHolder(view), DropDownAdapterRV.TemplateHolder.WhenValueTo, PlusMinusView.NumberEvent {
        private val binding = CardModBinding.bind(view)

        fun bind(style:Int, value:String, ) = with(binding) {

            PMLinear.visibility = View.GONE
            DD.visibility = View.VISIBLE
            DD.setMainText(value)
            DD.setDDArrayAndListener(DSpecialGameData().modName, this@TemplateHolder, null)

            PMLinear.visibility = View.VISIBLE
            DD.visibility = View.GONE
            PM.setValue(value.toInt())
            PM.setListener(99, 0, this@TemplateHolder)

            delete.setOnClickListener { view ->
                this@TemplateHolder.delete.deleteMod(adapterPosition)
                updViewr.updateView()
            }
        }

        interface updView {
            fun updateView()
        }

        interface DeleteMod {
            fun deleteMod(position: Int)
        }

        interface PutModValue {
            fun putModValue(position: Int, value:Int)
        }

        override fun whenValueTo(position: Int) {
            listener.putModValue(adapterPosition, position)
        }

        override fun numberEvent(number: Int) {
            listener.putModValue(adapterPosition, number)
        }
    }

    class EditTextTemplateHolder(
        view: View,
        private val delete: DeleteMod,
        private val updViewr: updView,
        private val listener: PutModValue,
    ) : RecyclerView.ViewHolder(view), DropDownAdapterRV.TemplateHolder.WhenValueTo, PlusMinusView.NumberEvent {
        private val binding = CardModBinding.bind(view)

        fun bind(style:Int, value:String, ) = with(binding) {

            PMLinear.visibility = View.GONE
            DD.visibility = View.VISIBLE
            DD.setMainText(value)
            DD.setDDArrayAndListener(DSpecialGameData().modName, this@TemplateHolder, null)

            PMLinear.visibility = View.VISIBLE
            DD.visibility = View.GONE
            PM.setValue(value.toInt())
            PM.setListener(99, 0, this@TemplateHolder)

            delete.setOnClickListener { view ->
                this@TemplateHolder.delete.deleteMod(adapterPosition)
                updViewr.updateView()
            }
        }

        interface updView {
            fun updateView()
        }

        interface DeleteMod {
            fun deleteMod(position: Int)
        }

        interface PutModValue {
            fun putModValue(position: Int, value:Int)
        }

        override fun whenValueTo(position: Int) {
            listener.putModValue(adapterPosition, position)
        }

        override fun numberEvent(number: Int) {
            listener.putModValue(adapterPosition, number)
        }
    }*/
}