package com.example.test.data_base

import io.realm.RealmList

data class FightType (
    var name:String,
    var forAllOrItem:Boolean,
    var difficult:String,
    var roll: String,
    var successCommentBoolean: Boolean,
    var successCommentText: String,
    var successEffectAdd: RealmList<EffectAdd>,
    var successDamageBoolean: Boolean,
    var failCommentBoolean: Boolean,
    var failCommentText: String,
    var failEffectAdd: RealmList<EffectAdd>,
)