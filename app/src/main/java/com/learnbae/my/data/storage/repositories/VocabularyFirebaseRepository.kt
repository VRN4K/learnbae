package com.learnbae.my.data.storage.repositories

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.learnbae.my.data.storage.entities.toFareBaseEntity
import com.learnbae.my.domain.datacontracts.interfaces.IVocabularyFirebaseRepository
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
                    Log.d("Vocabulary", "addWord:failure", task.exception)
                }
            }
    }

    override fun removeWord(userId: String, wordId: String) {
        dataBaseReference.child(userId).child(wordId).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Vocabulary", "removeWord:success")
                Log.d("Vocabulary", "user:$userId  word:$wordId")
            } else {
                Log.d("Vocabulary", "removeWord:failure", task.exception)
            }
        }
    }

    override fun removeAllWords(userId: String) {
        dataBaseReference.child(userId).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Vocabulary", "removeAllWord:success")
            } else {
                Log.d("Vocabulary", "removeAll:failure", task.exception)
            }
        }
    }

    override suspend fun getAllWordsId(userId: String): List<String> {
        return suspendCoroutine {
            dataBaseReference.child(userId).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Vocabulary", "getWordsId:success")
                    it.resume(task.result.children.map { it.key.toString() })
                } else {
                    Log.d("Vocabulary", "getWordsId:failure", task.exception)
                }
            }
        }
    }

    override suspend fun getWordsCount(userId: String): Int {
        return suspendCoroutine {
            dataBaseReference.child(userId).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Vocabulary", "getWordCount:success")
                    it.resume(task.result.childrenCount.toInt())
                } else {
                    Log.d("Vocabulary", "getWordCount:failure", task.exception)
                }
            }
        }
    }

    override suspend fun synchronizeWords(userId: String, wordsList: List<VocabularyWordUI>) {
        wordsList.onEach { addNewWord(userId, it) }
    }

    override suspend fun getAllWords(userId: String): List<VocabularyWordUI> {
        return suspendCoroutine {
            dataBaseReference.child(userId).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Vocabulary", "getAllWords:success")
                    it.resume(task.result.children.map {
                        VocabularyWordUI(
                            it.key!!,
                            it.children.find { word -> word.key == "word" }!!.value.toString(),
                            it.children.find { word -> word.key == "translation" }!!.value.toString()
                        )
                    })
                } else {
                    Log.d("Vocabulary", "getAllWords:failure", task.exception)
                }
            }
        }
    }
}