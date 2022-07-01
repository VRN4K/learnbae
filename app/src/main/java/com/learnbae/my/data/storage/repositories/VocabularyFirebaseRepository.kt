package com.learnbae.my.data.storage.repositories

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.learnbae.my.data.storage.entities.toFareBaseEntity
import com.learnbae.my.domain.datacontracts.interfaces.IVocabularyFirebaseRepository
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI

class VocabularyFirebaseRepository(private val database: FirebaseDatabase) :
    IVocabularyFirebaseRepository {
    companion object {
        private const val DB_VOCABULARY_REF_NAME = "VOCABULARY"
    }

    private val dataBaseReference by lazy { database.getReference(DB_VOCABULARY_REF_NAME) }

    override fun addNewWord(userId: String, word: VocabularyWordUI) {
        dataBaseReference.child(userId).child(word.id).setValue(word.toFareBaseEntity())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Vocabulary", "addWord:success")
                    Log.d("Vocabulary", "addWordId:${word.id}")
                } else {
                    Log.d("Vocabulary", "addWord:success:failure", task.exception)
                }
            }
    }

    override fun removeWord(userId: String, wordId: String) {
        dataBaseReference.child(userId).child(wordId).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Vocabulary", "removeWord:success")
                Log.d("Vocabulary", "user:$userId  word:$wordId")
            } else {
                Log.d("Vocabulary", "removeWord:success:failure", task.exception)
            }
        }
    }
}