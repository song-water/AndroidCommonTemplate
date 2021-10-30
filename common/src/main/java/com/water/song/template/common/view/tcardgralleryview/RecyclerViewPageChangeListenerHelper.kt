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

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

/**
 * @ClassName RecyclerViewPageChangeListenerHelper
 * @Description 备注：RecyclerView 实现类似 ViewPager 的 PageChangeListener 监听
 * Copy from {@link https://github.com/tamsiree/RxTool}
 */
class RecyclerViewPageChangeListenerHelper(
    private val snapHelper: SnapHelper,
    private val onPageChangeListener: OnPageChangeListener
) : RecyclerView.OnScrollListener() {
    private var oldPosition = -1 //防止同一Position多次触发

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        onPageChangeListener.onScrolled(recyclerView, dx, dy)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        var position = 0
        recyclerView.layoutManager?.let {
            //获取当前选中的itemView
            val view = snapHelper.findSnapView(it)
            if (view != null) {
                //获取itemView的position
                position = it.getPosition(view)
            }
            onPageChangeListener.onScrollStateChanged(recyclerView, newState)
            //newState == RecyclerView.SCROLL_STATE_IDLE 当滚动停止时触发防止在滚动过程中不停触发
            if (newState == RecyclerView.SCROLL_STATE_IDLE && oldPosition != position) {
                oldPosition = position
                onPageChangeListener.onPageSelected(position)
            }
        }
    }

    interface OnPageChangeListener {
        fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int)
        fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int)
        fun onPageSelected(position: Int)
    }
}