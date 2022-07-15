package com.learnbae.my.presentation.common.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.learnbae.my.R
import com.learnbae.my.databinding.TranslationItemBinding
import com.learnbae.my.databinding.WordOfADayCustomViewBinding
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.presentation.common.recycler.SimpleAdapter
import com.learnbae.my.presentation.screens.mainscreen.holder.WordMinicardHolder
import java.util.*

class WordOfADayCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding =
        WordOfADayCustomViewBinding.inflate(LayoutInflater.from(context), this, true)

    private val translationAdapter by lazy {
        SimpleAdapter(
            TranslationItemBinding::inflate,
            createViewHolder = { WordMinicardHolder(it) },
            onClickCallback = { _, _ -> }
        )
    }

    init {
        binding.wordTranslationRecycler.adapter = translationAdapter
        attrs.apply {
            val typedArray = context.obtainStyledAttributes(
                this,
                R.styleable.WordOfADayCustomView,
                0,
                0
            )
            if (typedArray.hasValue(R.styleable.WordOfADayCustomView_word_title)) {
                wordTitle = typedArray.getString(R.styleable.WordOfADayCustomView_word_title)!!
            }
            if (typedArray.hasValue(R.styleable.WordOfADayCustomView_word_transcription)) {
                wordTranscription =
                    typedArray.getString(R.styleable.WordOfADayCustomView_word_transcription)!!
            }
            typedArray.recycle()
        }
    }

    var wordTitle: String
        set(value) {
            binding.wordOfADayTitle.text = value
            field = value
        }

    var wordTranscription: String
        set(value) {
            binding.wordOfADayTranscription.text = value
            field = value
        }

    fun setTranslationsItems(items: List<String>) {
        translationAdapter.swapItems(items)

    }

    fun showPlayButton(isShow: Boolean) {
        binding.soundPlayButton.visibility = if (isShow) VISIBLE else GONE
    }

    fun changeLoadingState(isLoading: Boolean) {
        binding.loadingAnimator.changeLoadingState(isLoading, binding.wordOfADayContent.id)
    }

    fun setOnPlayButtonClickListener(action: () -> Unit) {
        binding.soundPlayButton.setOnClickListener { action() }
    }

    fun setAddToVocabularyCheckStatus(isWordInVocabulary: Boolean) {
        binding.addWordToFavorite.isChecked = isWordInVocabulary
    }

    fun setOnFavoriteIconClickListener(
        addAction: (VocabularyWordUI) -> Unit,
        deleteAction: (String) -> Unit
    ) {
        binding.addWordToFavorite.setOnCheckedChangeListener { _, check ->
            if (check) {
                addAction(
                    VocabularyWordUI(
                        UUID.randomUUID().toString(),
                        wordTitle,
                        translationAdapter.items.joinToString(", ")
                    )
                )
            } else {
                deleteAction(wordTitle)
            }
        }
    }
}