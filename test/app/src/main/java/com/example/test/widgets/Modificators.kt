package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.data_base.SpecialGameData
import com.example.test.databinding.DialogChooseAddModificationBinding
import com.example.test.databinding.ModificatorsBinding
import com.example.test.helpers.ModAdapterRV
import com.example.test.helpers.ModTemplateHolder
import com.example.test.viewModels.SkillTestVM
import kotlin.properties.Delegates

class Modificators : Fragment(), ModTemplateHolder.LoadFragment, ModTemplateHolder.DeleteMod,
    ModTemplateHolder.updIdMod, ModDialogFragment.AddMod {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private val adapter = ModAdapterRV(this, this, this)
    private var keyListMod by Delegates.notNull<Int>()
    private var keyListDeletedMod by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modificators, container, false)

        keyListMod = mSkillVM.createId()
        keyListDeletedMod = mSkillVM.createId()

        mSkillVM.mapMod[keyListMod] = MutableLiveData<MutableList<Mod>>()
        mSkillVM.mapMod[keyListMod]?.value = mutableListOf<Mod>()
        mSkillVM.mapListInt[keyListDeletedMod] = MutableLiveData<MutableList<Int>>()
        mSkillVM.mapListInt[keyListDeletedMod]?.value = mutableListOf<Int>()
        try {
            val binding = ModificatorsBinding.bind(view)

            fun bind() = with(binding) {
                modRV.layoutManager =
                    LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
                modRV.adapter = adapter
                mSkillVM.mapMod[keyListMod]?.observe(viewLifecycleOwner) {
                    adapter.setData(it)
                }
                addMod.setOnClickListener {
                    val dialogFragment = ModDialogFragment(this@Modificators)
                    dialogFragment.show(childFragmentManager, "chooseMod")

                }
            }
            bind()
        } catch (e: Exception) {
            Toast.makeText(view.context, "$e", Toast.LENGTH_LONG).show()
        }
        return view
    }

    override fun loadFragment(position: Int, style: Boolean, value: Int, id: Int) {
        if (style) {
            val bundle = Bundle()
            bundle.putString("main", "Выберите модификатор")
            bundle.putString("them", "blue")
            bundle.putInt("indexMod", position)
            bundle.putString("goal", "modification")
            bundle.putInt("value", value)
            bundle.putInt("key", keyListMod)
            val options = SpecialGameData().modName
            bundle.putStringArrayList("list", options)
            val fragment = DropDownList()
            fragment.arguments = bundle
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        } else {
            val bundle = Bundle()
            bundle.putInt("value", value)
            bundle.putInt("minValue", 0)
            bundle.putInt("maxValue", 30)
            bundle.putString("them", "blue")
            bundle.putString("goal", "mod")
            bundle.putInt("editKey", keyListMod)
            bundle.putInt("indexMod", position)
            val fragment = PlusAndMinus()
            fragment.arguments = bundle
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }
    }

    override fun deleteMod(position: Int) {
        val id = mSkillVM.mapMod[keyListMod]?.value?.get(position)?.resId
        if (id != null) {
            mSkillVM.mapListInt[keyListDeletedMod]?.value?.add(id)
            mSkillVM.mapMod[keyListMod]?.value?.removeAt(position)
        }
    }

    override fun updIdMod(position: Int, id: Int) {
        mSkillVM.mapMod[keyListMod]?.value?.get(position)?.resId = id
    }

    override fun addMod(style: Boolean) {
        when(style){
            true-> {
                var id = 0
                if (mSkillVM.mapListInt[keyListDeletedMod]?.value?.isNotEmpty() == true) {
                    id = mSkillVM.mapListInt[keyListDeletedMod]?.value?.minOrNull() ?: 0
                    if (id != 0) {
                        mSkillVM.mapListInt[keyListDeletedMod]?.value?.remove(id)
                    }
                }
                mSkillVM.mapMod[keyListMod]?.value!!.add(Mod(true, 0, id))
                adapter.notifyItemInserted(mSkillVM.mapMod[keyListMod]?.value?.size ?: 1 - 1)
            }
            false-> {
                var id = 0
                if (mSkillVM.mapListInt[keyListDeletedMod]?.value?.isNotEmpty() == true) {
                    id = mSkillVM.mapListInt[keyListDeletedMod]?.value?.minOrNull() ?: 0
                    if (id != 0) {
                        mSkillVM.mapListInt[keyListDeletedMod]?.value?.remove(id)
                    }
                }
                mSkillVM.mapMod[keyListMod]?.value?.add(Mod(false, 0, id))
                adapter.notifyItemInserted(mSkillVM.mapMod[keyListMod]?.value?.size ?: 1 - 1)
            }
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

            modStyleDD.setOnClickListener {
                addMod.addMod(true)
                dismiss()
            }

            modStylePM.setOnClickListener {
                addMod.addMod(false)
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

data class Mod(var style: Boolean, var value: Int, var resId: Int)