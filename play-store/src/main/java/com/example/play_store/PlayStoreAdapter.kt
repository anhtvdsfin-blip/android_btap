package com.example.play_store

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.play_store.AppAdapter

class PlayStoreAdapter(private val mainItems: List<PlayStoreItem>) :
    RecyclerView.Adapter<PlayStoreAdapter.BaseViewHolder<*>>() {

    // ĐỊNH NGHĨA VIEW TYPE (CHỈ MỘT LẦN VÀ PHẢI KHÁC NHAU)
    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_VERTICAL_APP = 1      // Dùng cho mục ứng dụng dọc đơn lẻ
    private val VIEW_TYPE_HORIZONTAL_LIST = 2   // Dùng cho danh sách cuộn ngang

    // --- ViewHolder Classes ---

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    class HeaderViewHolder(itemView: View) : BaseViewHolder<PlayStoreItem.Header>(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvHeaderTitle)
        override fun bind(item: PlayStoreItem.Header) {
            tvTitle.text = item.title
        }
    }

    class HorizontalListViewHolder(itemView: View) : BaseViewHolder<PlayStoreItem.HorizontalList>(itemView) {
        private val recyclerViewHorizontal: RecyclerView = itemView.findViewById(R.id.recyclerViewHorizontal)

        override fun bind(item: PlayStoreItem.HorizontalList) {
            recyclerViewHorizontal.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            // Đảm bảo R.layout.item_app_horizontal đã được tạo
            recyclerViewHorizontal.adapter = AppAdapter(item.apps, R.layout.item_app_horizontal)
        }
    }

    // ViewHolder cho MỘT mục ứng dụng dọc
    class VerticalListViewHolder(itemView: View) : BaseViewHolder<AppItem>(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvAppTitle)
        private val tvDetails: TextView = itemView.findViewById(R.id.tvAppDetails)
        private val ivIcon: ImageView = itemView.findViewById(R.id.ivAppIcon)

        override fun bind(item: AppItem) { // Nhận AppItem trực tiếp
            tvTitle.text = item.title
            tvDetails.text = "${item.category} • ${item.rating} ★ • ${item.size}"
            ivIcon.setImageResource(item.iconResId)
        }
    }

    // --- Phương thức của Adapter ---

    override fun getItemViewType(position: Int): Int {
        // Đảm bảo logic kiểm tra chính xác lớp con của sealed class
        return when (mainItems[position]) {
            is PlayStoreItem.Header -> VIEW_TYPE_HEADER
            is PlayStoreItem.VerticalAppItem -> VIEW_TYPE_VERTICAL_APP
            is PlayStoreItem.HorizontalList -> VIEW_TYPE_HORIZONTAL_LIST
            // Không cần else vì sealed class bao phủ tất cả trường hợp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        // PHẢI XỬ LÝ TẤT CẢ CÁC VIEW TYPE
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
                HeaderViewHolder(view)
            }
            VIEW_TYPE_VERTICAL_APP -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app_vertical, parent, false)
                VerticalListViewHolder(view)
            }
            VIEW_TYPE_HORIZONTAL_LIST -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_list, parent, false)
                HorizontalListViewHolder(view)
            }
            // Loại bỏ 'else' nếu bạn chắc chắn tất cả view type đã được xử lý
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = mainItems[position]

        // PHẢI XỬ LÝ TẤT CẢ CÁC HOLDER, KHÔNG CÓ ELSE
        when (holder) {
            is HeaderViewHolder -> holder.bind(element as PlayStoreItem.Header)

            // Khắc phục lỗi ClassCastException: Lấy AppItem từ PlayStoreItem.VerticalAppItem
            is VerticalListViewHolder -> holder.bind((element as PlayStoreItem.VerticalAppItem).app)

            is HorizontalListViewHolder -> holder.bind(element as PlayStoreItem.HorizontalList)

            // Không cần else nếu BaseViewHolder bao phủ tất cả các holder trên
            else -> throw IllegalStateException("Unexpected ViewHolder type")
        }
    }

    override fun getItemCount(): Int = mainItems.size
}