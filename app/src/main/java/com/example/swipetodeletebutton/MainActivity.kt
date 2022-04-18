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

package com.example.swipetodeletebutton

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.swipetodeletebutton.adapter.ItemsAdapter
import com.example.swipetodeletebutton.adapter.ProfileViewHolder
import com.example.swipetodeletebutton.adapter.swipe.SwipeCallback
import com.example.swipetodeletebutton.adapter.toColor
import com.example.swipetodeletebutton.data.Profile
import com.example.swipetodeletebutton.data.TextItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity(), ItemsAdapter.OnClickListener {

    private val itemsAdapter = ItemsAdapter().apply {
        onClickListener = this@MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUp(findViewById(R.id.recyclerView))
    }


    private fun setUp(recyclerView: RecyclerView) {
        recyclerView.adapter = itemsAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        ItemTouchHelper(SwipeCallback())
            .attachToRecyclerView(recyclerView)

        itemsAdapter.submitList(getData())
    }

    private fun getData(): List<Any> {
        val result = mutableListOf<Any>()

        result.add(
            Profile(
                name = "Riley Nunez",
                description = "(800) 665-7899",
                icon = R.drawable.ic_baseline_face_24,
                iconTintColor = "#00897B".toColor(),
                backgroundColor = "#E0F2F1".toColor(),
            )
        )
        result.add(
            Profile(
                name = "Mckenzie Choi",
                description = "(800) 456-1086",
                icon = R.drawable.ic_baseline_face_24,
                iconTintColor = "#FB8C00".toColor(),
                backgroundColor = "#FFF3E0".toColor(),
            )
        )
        result.add(
            Profile(
                name = "Brady Montgomery",
                description = "(800) 984-0568",
                icon = R.drawable.ic_baseline_face_24,
                iconTintColor = "#43A047".toColor(),
                backgroundColor = "#E8F5E9".toColor(),
            )
        )

        (0..10).mapTo(result) {
            TextItem(
                isRemovable = it % 3 == 0, // every third item removable
                name = "Text item #$it " + if (it % 3 == 0) "Is Removable" else "Not Removable"
            )
        }

        return result
    }

    override fun onRemoveProfileClicked(holder: ProfileViewHolder, item: Profile) {
        MaterialAlertDialogBuilder(this)
            .setCancelable(false)
            .setTitle("Delete profile")
            .setMessage("Are you sure profile '${item.name}' will be permanently deleted?")
            .setNegativeButton(android.R.string.cancel) { d, _ ->
                d.dismiss()
                holder.resetOffsets(animated = true)
            }.setPositiveButton(android.R.string.ok) { d, _ ->
                d.dismiss()
                itemsAdapter.removeItemAt(holder.adapterPosition)
            }.show()

    }
}

