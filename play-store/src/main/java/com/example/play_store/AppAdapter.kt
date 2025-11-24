package com.example.play_store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// AppItem là data class bạn đã định nghĩa
// layoutResId là R.layout.item_app_horizontal
class AppAdapter(private val apps: List<AppItem>, private val layoutResId: Int) :
    RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Ánh xạ các View của item_app_horizontal
        val ivIcon: ImageView = itemView.findViewById(R.id.ivHorizontalAppIcon)
        val tvTitle: TextView = itemView.findViewById(R.id.tvHorizontalAppTitle)
        val tvCategory: TextView = itemView.findViewById(R.id.tvHorizontalAppCategory)

        fun bind(item: AppItem) {
            ivIcon.setImageResource(item.iconResId)
            tvTitle.text = item.title
            tvCategory.text = item.category
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = apps[position]
        holder.bind(app)
    }

    override fun getItemCount(): Int = apps.size
}