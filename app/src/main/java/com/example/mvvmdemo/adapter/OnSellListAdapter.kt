package com.example.mvvmdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmdemo.R
import com.example.mvvmdemo.domain.MapData
import com.example.mvvmdemo.domain.OnSellData
import kotlinx.android.synthetic.main.item_on_sell.view.*

class OnSellListAdapter:RecyclerView.Adapter<OnSellListAdapter.InnerHolder>() {
    private val mContentList = arrayListOf<MapData>()
    class InnerHolder(itemView:View) :RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_on_sell, parent, false)
        return InnerHolder(itemView)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.itemView.apply {
            with(mContentList[position]){
                val coverUrl = "https:$pict_url"
                itemTitleTv.text =title
                offPriceTv.text = String.format("￥%.2f",zk_final_price.toFloat()-coupon_amount)
                Glide.with(context).load(coverUrl).into(itemCoverIv)
            }
        }
    }

    override fun getItemCount(): Int {
        return mContentList.size
    }

    fun setData(it: List<MapData>) {
        mContentList.clear()
        mContentList.addAll(it)
        notifyDataSetChanged()
    }
}