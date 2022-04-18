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

package com.example.swipetodeletebutton.adapter.swipe

import android.animation.ObjectAnimator
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import com.example.swipetodeletebutton.adapter.toPx

abstract class SimpleSwipeViewHolder : SwipeViewHolder {


    private var contentAnimator: ObjectAnimator? = null
    private var buttonsAnimator: ObjectAnimator? = null

    abstract val contentView: View

    abstract val buttonsView: View

    override val swipeBouncingWidth: Float = 16.toPx()

    override fun isScrollable(): Boolean = buttonsView.isVisible

    override fun setButtonsOffset(offset: Float) {
        if (buttonsAnimator?.isRunning == true) {
            buttonsAnimator?.cancel()
        }

        if (buttonsView.translationX != offset) {
            buttonsView.translationX = offset
        }
    }

    override fun setContentOffset(offset: Float) {
        if (contentAnimator?.isRunning == true) {
            contentAnimator?.cancel()
        }

        if (contentView.translationX != offset) {
            contentView.translationX = offset
        }
    }

    override fun getContentOffset(): Float {
        return contentView.translationX
    }

    override fun getButtonsOffset(): Float {
        return buttonsView.translationX
    }

    override fun animateButtonsOffset(offset: Float) {
        if (buttonsAnimator?.isRunning == true) {
            Log.w("buttonsAnimator", "Still running")
            buttonsAnimator?.cancel()
        }

        buttonsAnimator = buttonsView.animateTranslationX(offset)
    }

    override fun animateContentOffset(offset: Float) {
        if (contentAnimator?.isRunning == true) {
            Log.w("contentAnimator", "Still running")
            contentAnimator?.cancel()
        }

        contentAnimator = contentView.animateTranslationX(offset)
    }

    private fun View.animateTranslationX(value: Float): ObjectAnimator? {
        val view = this@animateTranslationX
        if (view.translationX == value) return null

        return ObjectAnimator.ofFloat(view, "translationX", value).apply {
            duration = 400
            interpolator = DecelerateInterpolator()
            start()
        }
    }
}