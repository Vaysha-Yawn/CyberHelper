package com.example.test.activity_and_fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data_base.EffectWeapon
import com.example.test.data_base.FightType
import com.example.test.data_base.Item
import com.example.test.helpers.ChooseWeaponAdapterRV
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.SkillTestVM


class ChooseWeapon : Fragment(), ChooseWeaponAdapterRV.TemplateHolder.OnItemClickListener {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mSkillVM: SkillTestVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.choose_weapon, container, false)
        val RV = view.findViewById<RecyclerView>(R.id.weaponFight)

        val characterId = mCharacterVM.characterId
        val listWeapon = mutableListOf( EffectWeapon(fightType = FightType(), name = "Рукопашный бой"))
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

        view.findViewById<ImageButton>(R.id.back).setOnClickListener {
            view.findNavController().popBackStack()
        }

        return view
    }

    override fun onItemClick(position: Int, effect: EffectWeapon) {
        mSkillVM.attack = effect
        view?.findNavController()?.navigate(R.id.action_weaponOrNotFight_to_fightAttack)
    }

}