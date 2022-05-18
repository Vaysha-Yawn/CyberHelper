package com.example.test.activity_and_fragments.character_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.adapters.GroupAdapterRV
import com.example.test.data_base.GroupParam
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import com.example.test.viewModels.GameSystemDAO


class CharacterList_Base : Fragment() {

    private  val KEY = "groupNumber"

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()
    private val mGameSystemVM: GameSystemDAO by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.character_list__item, container, false)

        val groupNumber = arguments?.getInt(KEY) ?: 0

        val groupRv = view.findViewById<RecyclerView>(R.id.group_rv)
        val adapterGroup = GroupAdapterRV()
        groupRv.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        groupRv.adapter = adapterGroup
        val characterId = mCharacterVM.characterId
        val gameId = mCharacterVM.gameId
        //
        val groupList = mutableListOf<GroupParam>()
        mCharacterVM.characterList.observe(viewLifecycleOwner) { list ->
            list.forEach { character ->
                if (character.id == characterId) {
                    val listNameGroup = mGameSystemVM.currentGameSystem.tabsToGroup[groupNumber]!!
                    listNameGroup.key.forEach { nameGroup ->
                        character.attributes.forEach { gp ->
                            if (gp.title == nameGroup) {
                                groupList.add(gp)
                            }
                        }
                    }
                }
            }
        }
        val r = view.context.getSharedPreferences("id", 0).getString("newGameId", "0")!!.toInt()
        val newOrPres = gameId == r
        adapterGroup.setData(groupList, newOrPres)
        return view
    }

    fun getFragment(groupNumber: Int):CharacterList_Base{
        val bundle = Bundle()
        bundle.putInt(KEY, groupNumber)
        return CharacterList_Base().apply {
            arguments = bundle
        }
    }
}