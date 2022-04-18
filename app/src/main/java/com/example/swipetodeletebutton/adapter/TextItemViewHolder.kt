/*
 * Copyright 2022 AlexK Lab
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.swipetodeletebutton.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.view.isVisible
import com.example.swipetodeletebutton.adapter.swipe.SimpleSwipeViewHolder
import com.example.swipetodeletebutton.adapter.swipe.SwipeViewHolder
import com.example.swipetodeletebutton.data.TextItem
import com.example.swipetodeletebutton.databinding.SimpleTextItemBinding

class TextItemViewHolder(
    parent: ViewGroup,
    private val onItemRemoveClicked: (TextItemViewHolder) -> Unit,
    private val binding: SimpleTextItemBinding = SimpleTextItemBinding
        .inflate(LayoutInflater.from(parent.context), parent, false),

    private val swipeViewHolderDelegate: SwipeViewHolder = object : SimpleSwipeViewHolder() {
        override val contentView: View get() = binding.textView
        override val buttonsView: View get() = binding.deleteButton
        override val offsetLimit: Float get() = -binding.deleteButton.width.toFloat()
    }

) : SwipeViewHolder by swipeViewHolderDelegate, BaseViewHolder<TextItem>(binding.root) {

    override fun onBind(item: TextItem) = with(binding) {
        resetOffsets()
        textView.text = item.name
        textView.setBackgroundColor(getBackgroundColor(item.isRemovable))
        deleteButton.isVisible = item.isRemovable
        deleteButton.setOnClickListener { onItemRemoveClicked.invoke(this@TextItemViewHolder) }
    }


    @ColorInt
    private fun getBackgroundColor(removable: Boolean): Int {
        return if (removable) {
            "#FBE9E7".toColor()
        } else {
            "#F1F8E9".toColor()
        }
    }
}