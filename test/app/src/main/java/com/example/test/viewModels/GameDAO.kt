package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data_base.Character
import com.example.test.data_base.Game
import io.realm.Realm
import io.realm.RealmList

open class GameDAO : ViewModel() {

    private var realm: Realm = Realm.getDefaultInstance()

    fun getNewGameId(): Int {
        var id = realm.where(Game::class.java).max("id")?.toInt()
        if (id == null) {
            id = 1
        } else {
            id += 1
        }
        return id
    }

    val gameName = MutableLiveData<String>()
    val gameList = MutableLiveData<RealmList<Game>>()

    init {
        gameList.value = RealmList<Game>()
    }

    //CREATE

    fun addGame(id: Int, name: String) {
        val game = Game()
        game.id = id
        game.name = name
        realm.executeTransaction { transactionRealm ->
            transactionRealm.insert(game)
        }
    }

    //READ

    fun initGameName(id: Int) {
        val game = loadGameById(id)
        gameName.value = game.name!!
    }

    fun initAllGames() {
        val games = realm.where(Game::class.java).findAll()
        games.forEach {
            gameList.value!!.add(it)
        }
    }

    fun loadGameById(id: Int): Game {
        return realm.where(Game::class.java).equalTo("id", id).findFirst()!!
    }

    //UPDATE

    fun updateGameName(id: Int, value: String) {
        val game = loadGameById(id)
        realm.executeTransaction {
            game.name = value
        }
        gameName.value = value
        gameList.value!!.forEach {
            if (it.id == id) {
                it.name = value
            }
        }
    }

    //DELETE

    fun deleteGameById(id: Int) {
        var game = Game()
        gameList.value!!.forEach {
            if (it.id == id) {
                game = it
            }
        }
        if (game != Game()){
            gameList.value!!.remove(game)
            realm.executeTransaction {
                game.deleteFromRealm()
            }
        }
        val listOfCharacter =
            realm.where(Character::class.java).equalTo("gameId", id).findAll()
        realm.executeTransaction {
            listOfCharacter.deleteAllFromRealm()
        }
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }

}