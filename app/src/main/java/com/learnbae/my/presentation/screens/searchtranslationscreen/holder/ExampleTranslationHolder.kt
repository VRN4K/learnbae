package com.learnbae.my.presentation.screens.searchtranslationscreen.holder

import com.learnbae.my.R
import com.learnbae.my.databinding.ExampleTranslationItemBinding
import com.learnbae.my.presentation.common.recycler.SimpleViewHolder

class ExampleTranslationHolder(private val binding: ExampleTranslationItemBinding) :
    SimpleViewHolder<String>(binding.root) {

    override fun bindTo(item: String, pos: Int, onClickCallback: ((String, Int) -> Unit)?) {
        with(binding) {
            println(item)
            exampleTranslation.text =
                root.resources.getString(R.string.words_list_item_pattern_regular, item)
        }
    }
}