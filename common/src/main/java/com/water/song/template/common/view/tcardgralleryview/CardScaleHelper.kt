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
package com.water.song.template.common.view.tcardgralleryview

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.water.song.template.common.util.toDp
import kotlin.math.abs
import kotlin.math.max

/**
 * Copy from {@link https://github.com/tamsiree/RxTool}
 */
class CardScaleHelper {
    private var recyclerView: RecyclerView? = null
    private lateinit var context: Context
    private var scale = 0.8f // 两边视图scale
    private var pagePadding = 15 // 卡片的padding, 卡片间的距离等于2倍的mPagePadding
    private var showLeftCardWidth = 15 // 左边卡片显示大小
    private var cardWidth = 0 // 卡片宽度 = 0
    private var onePageWidth = 0 // 滑动一页的距离 = 0
    private var cardGalleryWidth = 0
    var currentItemPos = 0
    private var currentItemOffset = 0
    private val linearSnapHelper = CardLinearSnapHelper()

    fun attachToRecyclerView(targetRecyclerView: RecyclerView, listener: IntResponseCallback) {
        this.recyclerView = targetRecyclerView
        context = targetRecyclerView.context

        targetRecyclerView.addOnScrollListener(
            RecyclerViewPageChangeListenerHelper(
                linearSnapHelper,
                object : RecyclerViewPageChangeListenerHelper.OnPageChangeListener {
                    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
//                        super.onScrollStateChanged(recyclerView, newState)
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            linearSnapHelper.noNeedToScroll =
                                currentItemOffset == 0 || currentItemOffset == getDestItemOffset(
                                    targetRecyclerView.adapter!!.itemCount - 1
                                )
                        } else {
                            linearSnapHelper.noNeedToScroll = false
                        }
                    }

                    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                        super.onScrolled(recyclerView, dx, dy)
                        // dx>0则表示右滑, dx<0表示左滑, dy<0表示上滑, dy>0表示下滑
                        if (dx != 0) { //去掉奇怪的内存疯涨问题
                            currentItemOffset += dx
                            computeCurrentItemPos()
                            // 开启log会影响滑动体验, 调试时才开启
//                    LogUtils.v(String.format("dx=%s, dy=%s, mScrolledX=%s", dx, dy, mCurrentItemOffset));
                            onScrolledChangedCallback(recyclerView)
                        }
                    }

                    override fun onPageSelected(position: Int) {
                        listener.onResponse(position)
                    }
                }
            )
        )

        initWidth()
        linearSnapHelper.attachToRecyclerView(targetRecyclerView)
    }

    /**
     * 初始化卡片宽度
     */
    private fun initWidth() {
        recyclerView?.let { rv ->
            rv.post {
                cardGalleryWidth = rv.width
                val padding = 2 * (pagePadding + showLeftCardWidth)
                cardWidth = cardGalleryWidth - padding.toDp(context)
                onePageWidth = cardWidth
                rv.smoothScrollToPosition(currentItemPos)
                onScrolledChangedCallback(rv)
            }
        }
    }

    private fun getDestItemOffset(destPos: Int): Int {
        return onePageWidth * destPos
    }

    /**
     * 计算mCurrentItemOffset
     */
    private fun computeCurrentItemPos() {
        if (onePageWidth <= 0) {
            return
        }
        var pageChanged = false
        // 滑动超过一页说明已翻页
        if (abs(currentItemOffset - currentItemPos * onePageWidth) >= onePageWidth) {
            pageChanged = true
        }
        if (pageChanged) {
            // val tempPos = currentItemPos
            currentItemPos = currentItemOffset / onePageWidth
            //            LogUtils.d(String.format("=======onCurrentItemPos Changed======= tempPos=%s, mCurrentItemPos=%s", tempPos, mCurrentItemPos));
        }
    }

    /**
     * RecyclerView位移事件监听, view大小随位移事件变化
     */
    private fun onScrolledChangedCallback(rv: RecyclerView?) {
        rv ?: return
        val offset = currentItemOffset - currentItemPos * onePageWidth
        val percent = max(abs(offset) * 1.0 / onePageWidth, 0.0001).toFloat()
        var leftView: View? = null
        var rightView: View? = null
        if (currentItemPos > 0) {
            leftView = rv.layoutManager?.findViewByPosition(currentItemPos - 1)
        }
        val currentView: View? = rv.layoutManager?.findViewByPosition(currentItemPos)
        if (currentItemPos < rv.adapter!!.itemCount - 1) {
            rightView = rv.layoutManager?.findViewByPosition(currentItemPos + 1)
        }
        if (leftView != null) {
            // y = (1 - mScale)x + mScale
            leftView.scaleY = (1 - scale) * percent + scale
        }
        if (currentView != null) {
            // y = (mScale - 1)x + 1
            currentView.scaleY = (scale - 1) * percent + 1
        }
        if (rightView != null) {
            // y = (1 - mScale)x + mScale
            rightView.scaleY = (1 - scale) * percent + scale
        }
    }

    fun setScale(scale: Float) {
        this.scale = scale
    }

    fun setPagePadding(pagePadding: Int) {
        this.pagePadding = pagePadding
    }

    fun setShowLeftCardWidth(showLeftCardWidth: Int) {
        this.showLeftCardWidth = showLeftCardWidth
    }
}