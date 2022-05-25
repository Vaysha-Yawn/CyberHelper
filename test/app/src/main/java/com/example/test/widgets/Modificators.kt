package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.adapters.ModAdapterRV
import com.example.test.adapters.ModTemplateHolder
import com.example.test.data_base.Mod
import com.example.test.databinding.DialogChooseAddModificationBinding
import com.example.test.viewModels.FewRollVM
import com.example.test.viewModels.SkillTestVM
import kotlin.properties.Delegates

class Modificators : Fragment(), ModTemplateHolder.DeleteMod,
     ModDialogFragment.AddMod, ModTemplateHolder.PutModValue {

    private val VM: FewRollVM by activityViewModels()

    private val adapter = ModAdapterRV(this,this)

    private var pos :Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pos = requireArguments().getInt("pos")
        if (VM.chosenRolls[pos]?.mods?.value == null){
            VM.chosenRolls[pos]?.mods = MutableLiveData<MutableList<Mod>>(mutableListOf())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modificators, container, false)

        try {
            val binding = com.example.test.databinding.ModificatorsBinding.bind(view)

            fun bind() = with(binding) {
                modRV.layoutManager =
                    LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
                modRV.adapter = adapter

                VM.chosenRolls[pos]?.mods?.observe(viewLifecycleOwner){
                    adapter.setData(it)
                }

                addMod.setOnClickListener {
                    val dialogFragment = ModDialogFragment(this@Modificators)
                    dialogFragment.show(childFragmentManager, "chooseMod")
                }

                if (pos != null) {
                    val roll = VM.chosenRolls[pos]
                    if (roll != null) {
                        roll.mods?.let { adapter.setData(it.value!!) }
                    }
                }
            }

            bind()
        } catch (e: Exception) {
            Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()
        }
        return view
    }

    override fun deleteMod(position: Int) {
        if ( pos!=null){
            VM.chosenRolls[pos]?.mods?.value?.removeAt(position)
        }
        adapter.notifyDataSetChanged()
    }

    override fun addMod(style: Boolean) {
        when (style) {
            true -> {
                if (pos !=null ){
                    VM.chosenRolls[pos]?.mods?.value?.add(Mod(true, 0))
                }
            }
            false-> {
                if (pos !=null ){
                    VM.chosenRolls[pos]?.mods?.value?.add(Mod(false, 0))
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun putModValue(position: Int, value: Int) {
        if (pos!=null){
            VM.chosenRolls[pos]?.mods?.value?.get(position)?.value = value
        }
    }

}

class ModDialogFragment(private val addMod:AddMod) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.dialog_choose_add_modification, container, false)

        val binding = DialogChooseAddModificationBinding.bind(view)
        fun bind() = with(binding) {

            variant1.setOnClickListener {
                addMod.addMod(false)
                dismiss()
            }

            variant2.setOnClickListener {
                addMod.addMod(true)
                dismiss()
            }
        }
        bind()

        return view
    }

    interface AddMod{
        fun addMod(style:Boolean)
    }
}

