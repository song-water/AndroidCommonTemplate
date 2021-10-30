/**
LICENSE
Copyright (c) <2016><Tamsiree>

"Anti 996" License Version 1.0 (Draft)

Permission is hereby granted to any individual or legal entity
obtaining a copy of this licensed work (including the source code,
documentation and / or related items, hereinafter collectively referred to as the "licensed work"), free of charge, to deal with the licensed work for any purpose, including without limitation, the rights to use, reproduce, modify, prepare derivative works of, distribute, publish and sublicense the licensed work, subject to the following conditions:

1. The individual or the legal entity must conspicuously display,
without modification, this License and the notice on each redistributed or derivative copy of the Licensed Work.

2. The individual or the legal entity must strictly comply with all
applicable laws, regulations, rules and standards of the jurisdiction relating to labor and employment where the individual is physically located or where the individual was born or naturalized; or where the legal entity is registered or is operating (whichever is stricter). In case that the jurisdiction has no such laws, regulations, rules and standards or its laws, regulations, rules and standards are unenforceable, the individual or the legal entity are required to comply with Core International Labor Standards.

3. The individual or the legal entity shall not induce, suggest or force
its employee(s), whether full-time or part-time, or its independent
contractor(s), in any methods, to agree in oral or written form, to
directly or indirectly restrict, weaken or relinquish his or her
rights or remedies under such laws, regulations, rules and standards
relating to labor and employment as mentioned above, no matter whether
such written or oral agreements are enforceable under the laws of the
said jurisdiction, nor shall such individual or the legal entity
limit, in any methods, the rights of its employee(s) or independent
contractor(s) from reporting or complaining to the copyright holder or
relevant authorities monitoring the compliance of the license about
its violation(s) of the said license.

THE LICENSED WORK IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN ANY WAY CONNECTION WITH THE
LICENSED WORK OR THE USE OR OTHER DEALINGS IN THE LICENSED WORK.
 * */
package com.water.song.template.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.widget.ImageView

/**
 * @author Water-Song
 */
private const val BLUR_RADIUS = 20f
private const val SCALED_WIDTH = 100
private const val SCALED_HEIGHT = 100

fun Bitmap.blur(context: Context, radius: Float): Bitmap {
    // 将缩小后的图片做为预渲染的图片。
    val inputBitmap = Bitmap.createScaledBitmap(this, SCALED_WIDTH, SCALED_HEIGHT, false)
    // 创建一张渲染后的输出图片。
    val outputBitmap = Bitmap.createBitmap(inputBitmap)

    // 创建RenderScript内核对象
    val rs = RenderScript.create(context)
    // 创建一个模糊效果的RenderScript的工具对象
    val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

    // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
    // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
    val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
    val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)

    // 设置渲染的模糊程度, 25f是最大模糊度
    blurScript.setRadius(radius)
    // 设置blurScript对象的输入内存
    blurScript.setInput(tmpIn)
    // 将输出数据保存到输出内存中
    blurScript.forEach(tmpOut)

    // 将数据填充到Allocation中
    tmpOut.copyTo(outputBitmap)
    return outputBitmap
}

/**
 * 图片背景切换动画帮助类
 */
fun Bitmap?.startSwitchBackgroundAnim(view: ImageView) {
    this ?: return
    val oldDrawable = view.drawable
    val oldBitmapDrawable: Drawable
    var oldTransitionDrawable: TransitionDrawable? = null
    if (oldDrawable is TransitionDrawable) {
        oldTransitionDrawable = oldDrawable
        oldBitmapDrawable = oldTransitionDrawable.findDrawableByLayerId(oldTransitionDrawable.getId(1))
    } else if (oldDrawable is BitmapDrawable) {
        oldBitmapDrawable = oldDrawable
    } else {
        oldBitmapDrawable = ColorDrawable(-0x3d3d3e)
    }

    if (oldTransitionDrawable == null) {
        oldTransitionDrawable =
            TransitionDrawable(arrayOf(oldBitmapDrawable, BitmapDrawable(view.resources, this)))
        oldTransitionDrawable.setId(0, 0)
        oldTransitionDrawable.setId(1, 1)
        oldTransitionDrawable.isCrossFadeEnabled = true
        view.setImageDrawable(oldTransitionDrawable)
    } else {
        oldTransitionDrawable.setDrawableByLayerId(oldTransitionDrawable.getId(0), oldBitmapDrawable)
        oldTransitionDrawable.setDrawableByLayerId(
            oldTransitionDrawable.getId(1),
            BitmapDrawable(view.resources, this)
        )
    }
    oldTransitionDrawable.startTransition(1000)
}