package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.data_base.EffectWeapon
import com.example.test.databinding.FightSecondBinding
import com.example.test.helpers.FragmentsAdapterRV
import com.example.test.viewModels.SkillTestVM
import com.example.test.widgets.*


class FightSecond : Fragment(), FragmentsAdapterRV.TemplateHolder.LoadFragment,
    RadioGroupTwo.OnCheckedRadio, Header.HeaderBack {

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

       /* val bundle = Bundle()
        bundle.putString("text1", "Успех")
        bundle.putString("text2", "Не успех")
        val fragment = RadioGroupTwo(this)
        fragment.arguments = bundle
        list.add("Проверка выбора")
        listFr.add(fragment)*/




        when (attack.fightType?.roll) {
            "one roll" -> {
                val fragment = Roll()
                val bundle = Bundle()

                fragment.arguments = bundle
                list.add("Проверка атаки")
                listFr.add(fragment)
            }
            "few roll" -> {
                val fragment = FewRoll()
                val bundle = Bundle()
                fragment.arguments = bundle
                list.add("Проверка атаки")
                listFr.add(fragment)
            }
            "arbitrary number" -> {
                /*val bundleF = Bundle()
                bundleF.putInt("value", 0)
                bundleF.putInt("minValue", 0)
                bundleF.putString("them", "green")
                val fragmentF = PlusAndMinus()
                fragmentF.arguments = bundleF
                list.add("")
                listFr.add(fragmentF)*/
            }
            "DD by table" -> {

                //loadDD(main:String, "green", goal:String, list:ArrayList<String>, resId:Int)
            }
        }

        val binding = FightSecondBinding.bind(view)
        fun bind() = with(binding) {

            loadFragmentLight(Header(this@FightSecond), R.id.header)
            title.text = attack.fightType?.name ?: "Какое-то название"
            val adapterRV = FragmentsAdapterRV(list, listFr, this@FightSecond)
            RV.adapter = adapterRV
            RV.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            btnNext.setOnClickListener {
                view.findNavController().navigate(R.id.action_fightSecond_to_fightThree)
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

    override fun back() {
        view?.findNavController()?.popBackStack()
    }


}