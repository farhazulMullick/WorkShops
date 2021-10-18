package com.example.workshop.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workshop.R
import com.example.workshop.database.Enrollments
import com.example.workshop.database.WorkShopTable
import com.example.workshop.viewUtils.customChrometab
import com.example.workshop.viewmodel.WorkShopVeiwModel

class DashBoardAdapter(val viewModel: WorkShopVeiwModel): RecyclerView.Adapter<DashBoardAdapter.MyViewHolder>() {
    companion object{
        const val TAG = "DashBoardAdapter"
    }

    var enrolledWorkShops = emptyList<WorkShopTable>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
        val imgWorkShop: ImageView = itemView.findViewById(R.id.img_workshop)
        val btnUnenroll: Button = itemView.findViewById(R.id.btn_unenroll)
        val tvKnowMore: TextView = itemView.findViewById(R.id.btn_know_more)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashBoardAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_dashboard, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashBoardAdapter.MyViewHolder, position: Int) {
        val currentData = enrolledWorkShops[position]
        holder.apply {
            tvTitle.text = currentData.title
            tvDesc.text = currentData.description

            Glide.with(itemView.context)
                .load(currentData.imgUrl)
                .into(imgWorkShop)


            btnUnenroll.setOnClickListener {
                viewModel.unEnroll(
                    viewModel.userId.value!!,
                    workshopId = currentData.workShopId
                )
            }

            tvKnowMore.setOnClickListener {
                itemView.context.customChrometab(currentData.webLink)
            }
            Log.d(TAG, "onBindHolder -> id ${currentData.workShopId}")
        }
    }

    override fun getItemCount(): Int = enrolledWorkShops.size

    fun setData(newData: List<WorkShopTable>){
        enrolledWorkShops = newData
        notifyDataSetChanged()
    }
}