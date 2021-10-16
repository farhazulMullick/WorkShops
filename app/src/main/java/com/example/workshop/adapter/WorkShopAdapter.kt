package com.example.workshop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workshop.R
import com.example.workshop.database.WorkShopTable

class WorkShopAdapter: RecyclerView.Adapter<WorkShopAdapter.WorkShopViewHolder>() {

    private var workShopData = emptyList<WorkShopTable>()

    class WorkShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
        val imgWorkShop: ImageView = itemView.findViewById(R.id.img_workshop)
        val btnApply: Button = itemView.findViewById(R.id.btn_apply)
        val tvKnowMore: TextView = itemView.findViewById(R.id.btn_know_more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkShopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_workshop, parent, false)
        return WorkShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkShopViewHolder, position: Int) {
        val currentData = workShopData[position]
        holder.apply {
            tvTitle.text = currentData.title
            tvDesc.text = currentData.description

            Glide.with(itemView.context)
                .load(currentData.imgUrl)
                .into(imgWorkShop)

            btnApply.setOnClickListener {

            }

            tvKnowMore.setOnClickListener {

            }
        }
    }

    override fun getItemCount(): Int {
        return  workShopData.size
    }

    fun setData(newData : List<WorkShopTable>){
        workShopData = newData
        notifyDataSetChanged()
    }
}