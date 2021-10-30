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

package com.water.song.template.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.water.song.template.common.view.TBlurView
import com.water.song.template.common.view.indicator.TIndicator
import com.water.song.template.common.view.tcardgralleryview.CardScaleHelper
import com.water.song.template.common.view.tcardgralleryview.TCardGalleryView

class ActivityTCardGallery : AppCompatActivity() {
    private val imgSources: MutableList<Int> = ArrayList()
    private var cardScaleHelper: CardScaleHelper? = null
    private var lastRvItemPos = -1
    private var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_tcard_grallery)
        supportActionBar?.title = getString(R.string.demo_card_gallery_title)
        initView()
    }

    private fun initView() {
        imgSources.clear()
        imgSources.add(R.drawable.demo_pic_card_1)
        imgSources.add(R.drawable.demo_pic_card_2)
        imgSources.add(R.drawable.demo_pic_card_3)

        val tIndicator = findViewById<TIndicator>(R.id.tIndicator)
        val recyclerView = findViewById<TCardGalleryView>(R.id.recyclerView)?.also { rv ->
            linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rv.layoutManager = linearLayoutManager
            rv.adapter = AdapterCardGallery(imgSources)
        } ?: return
        tIndicator.attachToRecyclerView(recyclerView)

        val blurView = findViewById<TBlurView>(R.id.blurView)
        cardScaleHelper = CardScaleHelper().also { scaleHelper ->
            scaleHelper.currentItemPos = 0
            scaleHelper.attachToRecyclerView(recyclerView) { position ->
                if (lastRvItemPos != position) {
                    lastRvItemPos = position
                    blurView.notifyChange(imgSources[lastRvItemPos])
                }
            }
        }
    }
}
