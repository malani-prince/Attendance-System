package com.example.attendancesystem

import android.content.Context
import android.graphics.*
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancesystem.Constants.ExtraKey.PUBLIC_UPLOAD
import com.example.attendancesystem.databinding.RowItemUploadBinding
import java.io.*
import java.net.*

class UpLoadListAdapter(var context: Context, private var customerList: ArrayList<Attachments>, private var listener: OnItemClickListener) : RecyclerView.Adapter<UpLoadListAdapter.Holder>() {
    
    inner class Holder(var binding: RowItemUploadBinding) : RecyclerView.ViewHolder(binding.root)
    
    override fun getItemCount(): Int {
        return customerList.size
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(RowItemUploadBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = customerList[position]
        
        holder.binding.apply {
            if (item.awsImageUrl?.isNotEmpty() == true) {
                setImage(ivImage, item.path)
                
            } else if (item.path?.contains(PUBLIC_UPLOAD) == true) {
                setImage(ivImage, item.path)
                
            } else {
                setImage(ivImage, item.path)
            }
            
            ivClose.setOnClickListener {
                listener.onItemClick(holder.adapterPosition, item)
            }
            
            cardView.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }
    
    override fun getItemViewType(position: Int): Int {
        return position
    }
}
