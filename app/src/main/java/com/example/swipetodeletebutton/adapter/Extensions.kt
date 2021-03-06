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

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import androidx.annotation.ColorInt

fun Int.toPx(): Float = this * Resources.getSystem().displayMetrics.density

fun View.dpTtoPx(value: Float): Float = value * context.resources.displayMetrics.density


@ColorInt
fun String.toColor(): Int {
    return try {
        Color.parseColor(this)
    } catch (e: Exception) {
        Color.TRANSPARENT
    }
}

fun View.animateTranslationX(
    value: Float,
    animationDuration: Long = 400,
    animationInterpolator: Interpolator = DecelerateInterpolator()
): ObjectAnimator? {
    val view = this@animateTranslationX
    if (view.translationX == value) return null

    return ObjectAnimator.ofFloat(view, "translationX", value).apply {
        duration = animationDuration
        interpolator = animationInterpolator
        start()
    }
}