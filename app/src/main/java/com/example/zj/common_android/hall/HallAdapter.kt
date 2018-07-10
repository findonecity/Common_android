package com.example.zj.common_android.hall

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.zj.common_android.R
import com.example.zj.common_android.bean.HallRecyBean
import com.example.zj.common_android.util.glide.GlideImageUtil


/**
 * Created by ZhengJie on 2018/3/19.
 * is use for: 装车员列表 全部装车员 adapter by kotlin
 */

class HallAdapter(var mContext: Context, val mList: List<HallRecyBean>) : RecyclerView.Adapter<HallAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_hall, parent, false)
        //        view.setOnClickListener(this);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = mList[position].title
        holder.tvStartTime.text = mList[position].status.toString()
        holder.tvTime.text = mList[position].time

        when (mList[position].type) {
            1 ->{
                GlideImageUtil.loadResourceImage(mContext, R.mipmap.hall_type1, holder.ivType)
            }
            2 -> {
                GlideImageUtil.loadResourceImage(mContext, R.mipmap.hall_type2, holder.ivType)
            }
            3 ->{
                GlideImageUtil.loadResourceImage(mContext, R.mipmap.hall_type3, holder.ivType)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivType:ImageView = itemView.findViewById(R.id.ivType)
        var tvTitle:TextView = itemView.findViewById(R.id.tvTitle)
        var tvStartTime:TextView = itemView.findViewById(R.id.tvStartTime)
        var tvTime:TextView = itemView.findViewById(R.id.tvTime)

    }
}
