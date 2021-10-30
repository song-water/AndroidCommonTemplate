package com.water.song.template.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.water.song.template.common.view.tcardgralleryview.CardAdapterHelper

/**
 * Created by Tamsiree on 8/30/16.
 */
internal class AdapterCardGallery(
    private var imgSources: MutableList<Int>
) : RecyclerView.Adapter<AdapterCardGallery.ViewHolder>() {
    private val cardAdapterHelper = CardAdapterHelper()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.demo_layout_view_card_item, parent, false)
        cardAdapterHelper.onCreateViewHolder(parent, itemView)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cardAdapterHelper.onBindViewHolder(holder.itemView, position, itemCount)
        holder.mImageView.setImageResource(imgSources[position])
        holder.mImageView.setOnClickListener {
            Toast.makeText(holder.mImageView.context, "position: $position", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return imgSources.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImageView: ImageView = itemView.findViewById<View>(R.id.imageView) as ImageView
    }
}