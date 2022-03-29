package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.data_base.EffectWeapon
import com.example.test.databinding.FightSecondBinding
import com.example.test.helpers.FragmentsAdapterRV
import com.example.test.viewModels.SkillTestVM
import com.example.test.widgets.*


class FightSecond : Fragment(), FragmentsAdapterRV.TemplateHolder.LoadFragment,
    RadioGroupTwo.OnCheckedRadio {

    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fight_second, container, false)
        mSkillVM.clearVM()
        val attack = mSkillVM.attack ?: EffectWeapon()

        fun loadPM(value:Int, minValue:Int, maxValue:Int?, resId:Int){
            val bundleD = Bundle()
            bundleD.putInt("value", value)
            bundleD.putInt("minValue", minValue)
            if (maxValue!=null){
                bundleD.putInt("maxValue", maxValue)
            }
            bundleD.putString("them", "green")
            bundleD.putString("goal", "")
            val fragmentD = PlusAndMinus()
            fragmentD.arguments = bundleD
            childFragmentManager.commit {
                replace(resId, fragmentD)
                addToBackStack(null)
            }
        }
        fun loadDD(main:String, them:String, goal:String, list:ArrayList<String>, id:Int){
            val fragment = DropDownList()
            val bundle = Bundle()
            bundle.putString("main", main)
            bundle.putString("them", them)
            bundle.putString("goal", goal)
            bundle.putStringArrayList("list", list)
            fragment.arguments = bundle
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }

        fun loadFragmentLight(fragment:Fragment, id:Int){
            childFragmentManager.commit {
                replace(id, fragment)
                addToBackStack(null)
            }
        }

        val list = mutableListOf<String>()
        val listFr = mutableListOf<Fragment>()

        val bundleD = Bundle()
        bundleD.putString("goal", "goal")
        val fragmentD = Roll()
        fragmentD.arguments = bundleD
        list.add("Бросок атакующего")
        listFr.add(fragmentD)

        val bundle = Bundle()
        bundle.putString("text1", "Успех")
        bundle.putString("text2", "Не успех")
        val fragment = RadioGroupTwo(this)
        fragment.arguments = bundle
        list.add("Проверка выбора")
        listFr.add(fragment)

        when (attack.fightType?.roll) {
            "one roll" -> {

            }
            "few roll" -> {
                //здесь добавить VP2 в качестве отдельного фрагмента
                //loadFragmentLight(Roll(), R.id.mainFr)
            }
            "arbitrary number" -> {
                val bundleF = Bundle()
                bundleF.putInt("value", 0)
                bundleF.putInt("minValue", 0)
                bundleF.putString("them", "green")
                val fragmentF = PlusAndMinus()
                fragmentF.arguments = bundleF
                list.add("")
                listFr.add(fragmentF)

            }
        }

        val fragmentHeader = Header()
        childFragmentManager.commit {
            replace(R.id.header, fragmentHeader)
            addToBackStack(null)
        }

        val binding = FightSecondBinding.bind(view)
        fun bind() = with(binding) {
            title.text = attack.fightType?.name?:""
            val adapterRV = FragmentsAdapterRV(list, listFr, this@FightSecond)
            RV.adapter = adapterRV
            RV.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            btnNext.setOnClickListener {
                view.findNavController().navigate(R.id.action_fightAttack_to_fightThreeGoal)
            }
        }

        bind()

        return view
    }

    override fun loadFragment(position: Int, id: Int, fragment: Fragment) {
        childFragmentManager.commit {
            replace(id, fragment)
            addToBackStack(null)
        }
    }

    override fun checkedRadio1() {
        Toast.makeText(view?.context, "text1", Toast.LENGTH_SHORT).show()
    }

    override fun checkedRadio2() {
        Toast.makeText(view?.context, "text2", Toast.LENGTH_SHORT).show()
    }


}