package com.example.test.character_list.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.data_base.data_sample.test_data.DTemplateParamStr
import com.example.test.data_base.realm.character.CharacterDAO
import com.example.test.data_base.realm.game.GameDAO
import com.example.test.data_base.realm.game_system.GameSystemDAO
import com.example.test.components.views.HeaderView
import com.google.android.material.bottomnavigation.BottomNavigationView


class CharacterList : Fragment(), HeaderView.HeaderBack {

    private val mCharacterVM: CharacterDAO by activityViewModels()
    private val mGameVM: GameDAO by activityViewModels()
    private val mGameSystemVM: GameSystemDAO by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.character_list, container, false)
        try {
            val gameId = mCharacterVM.gameId
            val characterId = this.requireArguments().getInt("characterId", 0)
            mCharacterVM.initCharacterId(characterId)

            val tvName = view.findViewById<TextView>(R.id.CharacterList_NameCharacter)
            mCharacterVM.characterList.observe(viewLifecycleOwner) { list ->
                list.forEach {
                    if (it.id == characterId) {
                        tvName.text =
                            DTemplateParamStr().readParamStr(
                                it,
                                "Базовые параметры",
                                "Имя персонажа"
                            )
                    }
                }
            }

            view.findViewById<HeaderView>(R.id.headerView)
                .setBack(true, this, requireActivity(), viewLifecycleOwner)

            val menu = view.findViewById<BottomNavigationView>(R.id.CharacterList_Menu)
            childFragmentManager.commit {
                if (mCharacterVM.currentCharacterListTab != null) {
                    replace(
                        R.id.Character_list_fragment_container,
                        CharacterList_Base().getFragment(mCharacterVM.currentCharacterListTab!!)
                    )
                } else {
                    replace(R.id.Character_list_fragment_container, CharacterList_Base())
                }
            }


            menu.setOnItemSelectedListener setOnNavigationItemSelectedListener@{
                when (it.itemId) {
                    R.id.Base -> {
                        mCharacterVM.currentCharacterListTab = 0
                        childFragmentManager.commit {
                            replace(
                                R.id.Character_list_fragment_container,
                                CharacterList_Base().getFragment(0)
                            )
                        }
                        return@setOnNavigationItemSelectedListener true
                    }

                    R.id.Skill_and_HP -> {
                        mCharacterVM.currentCharacterListTab = 1
                        childFragmentManager.commit {
                            replace(
                                R.id.Character_list_fragment_container,
                                CharacterList_Base().getFragment(1)
                            )
                        }
                        return@setOnNavigationItemSelectedListener true
                    }

                    R.id.Armor_and_weapon -> {
                        mCharacterVM.currentCharacterListTab = 2
                        childFragmentManager.commit {
                            replace(
                                R.id.Character_list_fragment_container,
                                CharacterList_Base().getFragment(2)
                            )
                        }
                        return@setOnNavigationItemSelectedListener true
                    }

                    R.id.Stuff -> {
                        mCharacterVM.currentCharacterListTab = 3
                        childFragmentManager.commit {
                            replace(
                                R.id.Character_list_fragment_container,
                                CharacterList_Base().getFragment(3)
                            )
                        }
                        return@setOnNavigationItemSelectedListener true
                    }

                    R.id.About -> {
                        mCharacterVM.currentCharacterListTab = 4
                        childFragmentManager.commit {
                            replace(
                                R.id.Character_list_fragment_container,
                                CharacterList_Base().getFragment(4)
                            )
                        }
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                false
            }

            view.findViewById<Button>(R.id.CharacterMenu_Delete).setOnClickListener {
                val bundle = Bundle()
                bundle.putString("id", characterId.toString())
                bundle.putString("param", "character")
                bundle.putString("key", "персонажа")
                val r =
                    view.context.getSharedPreferences("id", 0).getString("newGameId", "0")!!.toInt()
                if (gameId == r) {
                    view.findNavController()
                        .navigate(R.id.action_new_characterList_to_new_delete, bundle)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_characterList_to_delete, bundle)
                }
            }

            view.findViewById<HeaderView>(R.id.headerView)
                .setBack(true, this, requireActivity(), viewLifecycleOwner)

        } catch (e: Exception) {
            Toast.makeText(view?.context, "$e", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    override fun back() {
        view?.findNavController()?.popBackStack()
    }

}
