package com.example.test.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.SpecialGameData
import com.example.test.helpers.DropDownAdapterRV
import com.example.test.viewModels.SkillTestVM

class DropDownList : Fragment(), DropDownAdapterRV.TemplateHolder.OnItemClickListener {

    private val mSkillVM: SkillTestVM by activityViewModels()
    private lateinit var goal: String
    private lateinit var tvMainTypeWeapon: TextView
    private lateinit var itemsRV: RecyclerView
    private lateinit var list: ArrayList<String>
    private var more: Int = 0
    private var less: Int = 0
    private var indexMod: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.drop_down_list, container, false)
        val bundle = this.arguments
        val main = bundle!!.getString("main", "")
        val them = bundle.getString("them", "yellow")
        list = bundle.getStringArrayList("list")!!
        goal = bundle.getString("goal", "")
        indexMod = bundle.getInt("indexMod", -1) ?: -1

        tvMainTypeWeapon = view.findViewById<TextView>(R.id.main)
        itemsRV = view.findViewById<RecyclerView>(R.id.items)

        tvMainTypeWeapon.text = main
        itemsRV.visibility = View.GONE

        more = R.drawable.more
        less = R.drawable.less

        when (them) {
            "yellow" -> {
                tvMainTypeWeapon.setBackgroundResource(R.drawable.spinner_yellow)
            }
            "green" -> {
                tvMainTypeWeapon.setBackgroundResource(R.drawable.spinner_green)
            }
            "blue" -> {
                more = R.drawable.more_blue
                less = R.drawable.less_blue
                tvMainTypeWeapon.setBackgroundResource(R.drawable.drop_down_dark_blue)
                tvMainTypeWeapon.setTextColor(resources.getColor(R.color.cyan))
            }
        }
        tvMainTypeWeapon.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            more,
            0
        )
        tvMainTypeWeapon.setOnClickListener {
            if (itemsRV.visibility != View.VISIBLE) {
                itemsRV.visibility = View.VISIBLE
                tvMainTypeWeapon.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    less,
                    0
                )
            } else {
                itemsRV.visibility = View.GONE
                tvMainTypeWeapon.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    more,
                    0
                )
            }
        }

        val adapterItems =
            DropDownAdapterRV(list, them, this)
        itemsRV.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        itemsRV.adapter = adapterItems

        if (goal == "modification"){
            val value = mSkillVM.modification.value!![indexMod].value
            if (indexMod >= 0 && value>0) {
                tvMainTypeWeapon.text = list[value-1]
            }

        }

        return view
    }

    override fun onItemClick(position: Int) {
        if (goal == "") {
            tvMainTypeWeapon.text = list[position]
            itemsRV.visibility = View.GONE
            tvMainTypeWeapon.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                more,
                0
            )
        }
        if (goal == "characterMenu") {
            val bundleQ = Bundle()
            bundleQ.putString("title", list[position])
            parentFragment?.view?.findNavController()
                ?.navigate(R.id.action_characterMenu_to_pres_skillTest, bundleQ)
        }
        if (goal == "difficult" || goal == "modification") {
            tvMainTypeWeapon.text = list[position]
            itemsRV.visibility = View.GONE
            tvMainTypeWeapon.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                more,
                0
            )
            try {
                if (goal == "difficult") {
                    mSkillVM.difficult.value = position
                }

                if (goal == "modification") {
                    if (indexMod >= 0) {
                        mSkillVM.modification.value!![indexMod].value = position+1
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(view?.context, "$e", Toast.LENGTH_LONG).show()
            }
        }
    }
}

/*interface loadFragmentDropDown {
    fun loadFragmentDropDown(main: String, them: String, goal: String) {
        val bundle = Bundle()
        bundle.putString("main", main)
        bundle.putString("them", them)
        bundle.putInt("indexMod", position)
        bundle.putString("goal", goal)
        val options = SpecialGameData().modName
        bundle.putStringArrayList("list", options)
        val fragment = DropDownList()
        fragment.arguments = bundle
        childFragmentManager.commit {
            replace(id, fragment)
            addToBackStack(null)
        }
    }
}*/


/*fun loadFragmentDropDown(main:String, them:String, list:ArrayList<String>, fragmentId:Int ){
    val bundle = Bundle()
    bundle.putString("main", main)
    bundle.putString("them", them)
    bundle.putStringArrayList("list", list)
    val fragment = DropDownList()
    fragment.arguments = bundle
    childFragmentManager.commit {
        replace(fragmentId, fragment)
        addToBackStack(null)
    }
}*/

/* используйте код ниже для:

Подключения
val bundle = Bundle()
bundle.putString("main", "Выберите тип оружия")
bundle.putStringArrayList("list", arrayListOf("Ближний бой", "Дальний бой", "Автоматический огонь", "Взрывчатка"))
loadFragment(R.id.weapon_edit_type_RV, DropDownList(), bundle)  , где R.id.weapon_edit_type_RV это id места под фрагмент

private fun loadFragment(frCont: Int, fragment: Fragment, bundle: Bundle?) {
        if (bundle != null) {
            fragment.arguments = bundle
        }
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(frCont, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

Нахождения выбранного варианта
val frag: Fragment = supportFragmentManager.findFragmentById(R.id.weapon_edit_type_RV)!!
val type = frag.view?.findViewById<TextView>(R.id.fr_type_weapon_main)?.text.toString()

Проверки
if (type==main){
                res=0
                Toast.makeText(this, "Выберите тип",Toast.LENGTH_SHORT).show()
            }
 */