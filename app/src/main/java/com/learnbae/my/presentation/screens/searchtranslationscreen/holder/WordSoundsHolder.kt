package com.learnbae.my.presentation.screens.searchtranslationscreen.holder

import com.learnbae.my.databinding.ExampleTranslationItemBinding
import com.learnbae.my.databinding.SoundItemBinding
import com.learnbae.my.presentation.common.recycler.SimpleAdapter
import com.learnbae.my.presentation.common.recycler.SimpleViewHolder

class WordSoundsHolder(private val binding: SoundItemBinding) :
    SimpleViewHolder<Pair<String, String>>(binding.root) {

    override fun bindTo(
        item: Pair<String, String>,
        pos: Int,
        onClickCallback: ((Pair<String, String>, Int) -> Unit)?
    ) {
        with(binding) {
            this.wordTranscription.text = item.first
            this.soundPlayButton.setOnClickListener { onClickCallback?.invoke(item, pos) }
        }
    }
}