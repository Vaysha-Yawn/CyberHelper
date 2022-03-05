package com.example.test.activity_and_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.EffectWeapon
import com.example.test.data_base.Item
import com.example.test.helpers.WeaponFightAdapterRV
import com.example.test.viewModels.CharacterDAO


class WeaponOrNotFight : Fragment(), WeaponFightAdapterRV.TemplateHolder.OnItemClickListener {

    private val mCharacterVM: CharacterDAO by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.weapon_or_not_fight, container, false)
        val RV = view.findViewById<RecyclerView>(R.id.weaponFight)

        val characterId = mCharacterVM.characterId
        val listWeapon = mutableMapOf( EffectWeapon(fightType = "Рукопашный бой") to Item("Нет, рукопашный бой"))
        mCharacterVM.characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }?.attributes?.forEach { groupParam ->
            groupParam.attributes?.listItem?.forEach { item ->
                item.effectsWeapon.forEach {
                    listWeapon.put( it, item)
                }
            }
        }

        RV.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        val adapter = WeaponFightAdapterRV(listWeapon, this)
        RV.adapter = adapter

        view.findViewById<ImageButton>(R.id.back).setOnClickListener {
            view.findNavController().popBackStack()
        }

        return view
    }

    override fun onItemClick(position: Int,  item: Item, effect: EffectWeapon) {
        Toast.makeText(view?.context, "$position, ${item.name}, ${effect.fightType}", Toast.LENGTH_SHORT).show()
    }

}