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

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.swipetodeletebutton.data.Profile
import com.example.swipetodeletebutton.data.TextItem

@Suppress("UNCHECKED_CAST")
class ItemsAdapter : RecyclerView.Adapter<BaseViewHolder<Any>>() {

    private var items = mutableListOf<Any>()

    interface OnClickListener {
        fun onRemoveProfileClicked(holder: ProfileViewHolder, item: Profile)
    }

    var onClickListener: OnClickListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Any>) {
        items = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].javaClass.name.hashCode()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {

        TextItem::class.java.name.hashCode() -> TextItemViewHolder(
            parent = parent,
            onItemRemoveClicked = this::onRemoveTextItemClicked
        )

        Profile::class.java.name.hashCode() -> ProfileViewHolder(
            parent = parent,
            onItemRemoveClicked = this::onRemoveProfileClicked
        )

        else -> throw IllegalStateException("Undefined item type: $viewType")

    } as BaseViewHolder<Any>

    override fun onBindViewHolder(holder: BaseViewHolder<Any>, position: Int) {
        holder.onBind(items[position])
    }

    fun removeItemAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun onRemoveTextItemClicked(holder: TextItemViewHolder) {
        removeItemAt(holder.adapterPosition)
    }

    private fun onRemoveProfileClicked(holder: ProfileViewHolder) {
        val item = items[holder.adapterPosition] as? Profile ?: return
        onClickListener?.onRemoveProfileClicked(holder, item)
    }
}



