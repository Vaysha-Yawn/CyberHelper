package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.activity_and_fragments.hosts.FightHost
import com.example.test.data_base.EffectWeapon
import com.example.test.adapters.ChooseWeaponAdapterRV
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM
import com.example.test.views.HeaderView


class ChooseWeapon : Fragment(), ChooseWeaponAdapterRV.TemplateHolder.OnItemClickListener, HeaderView.HeaderBack {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.choose_weapon, container, false)
        val RV = view.findViewById<RecyclerView>(R.id.weaponFight)
        val characterId = mCharacterVM.characterId
        val listWeapon = mutableListOf(
            EffectWeapon(
                //fightType = TemplateFightType().mapFightType["Рукопашный бой"] ?: FightType(),
                name = "Рукопашный бой"
            )
        )
        mCharacterVM.characterList.value!!.singleOrNull { character ->
            character.id == characterId
        }?.attributes?.forEach { groupParam ->
            groupParam.attributes?.listItem?.forEach { item ->
                item.effectsWeapon.forEach {
                    listWeapon.add( it)
                }
            }
        }

        RV.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        val adapter = ChooseWeaponAdapterRV(listWeapon, this)
        RV.adapter = adapter

        return view
    }

    override fun onItemClick(position: Int, effect: EffectWeapon) {
        mSkillVM.attack = effect
        mSkillVM.map[0] = mutableMapOf()
        mSkillVM.map[1] = mutableMapOf()
        mSkillVM.map[1] = mutableMapOf()
        mSkillVM.lastIndex = 2
        view?.findNavController()?.navigate(R.id.action_chooseWeapon_to_fightSecond)
    }

    override fun back() {
        (activity as FightHost).backToCharacterMenu()
    }
}