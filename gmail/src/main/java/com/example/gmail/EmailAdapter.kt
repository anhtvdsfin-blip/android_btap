package com.example.gmail

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class EmailAdapter(
    private val emails: List<Email>,
    private val onStarClicked: (Email) -> Unit // Callback khi nhấn Star
) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    // Danh sách màu ngẫu nhiên cho avatar
    private val avatarColors = intArrayOf(
        Color.parseColor("#E57373"), Color.parseColor("#81C784"),
        Color.parseColor("#64B5F6"), Color.parseColor("#FFD54F"),
        Color.parseColor("#BA68C8"), Color.parseColor("#4DB6AC")
    )

    // Hàm lấy màu ngẫu nhiên dựa trên vị trí hoặc tên người gửi
    private fun getAvatarColor(position: Int): Int {
        return avatarColors[position % avatarColors.size]
    }

    class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAvatar: TextView = itemView.findViewById(R.id.tvAvatar)
        val tvSender: TextView = itemView.findViewById(R.id.tvSender)
        val tvSubject: TextView = itemView.findViewById(R.id.tvSubject)
        val tvSummary: TextView = itemView.findViewById(R.id.tvSummary)
        val btnStar: ImageButton = itemView.findViewById(R.id.btnStar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val email = emails[position]

        // 1. Avatar và Màu nền
        holder.tvAvatar.text = email.sender.firstOrNull()?.toString()?.uppercase() ?: ""
        val drawable = holder.tvAvatar.background as GradientDrawable
        drawable.setColor(getAvatarColor(position))

        // 2. Nội dung
        holder.tvSender.text = email.sender
        holder.tvSubject.text = email.subject
        holder.tvSummary.text = email.summary

        // 3. Star Icon
        updateStarIcon(holder.btnStar, email.isStarred)

        // 4. Xử lý sự kiện Star
        holder.btnStar.setOnClickListener {
            // Thay đổi trạng thái dữ liệu
            email.isStarred = !email.isStarred
            // Cập nhật icon trên giao diện
            updateStarIcon(holder.btnStar, email.isStarred)
            // Gọi callback nếu cần xử lý logic bên ngoài Activity
            onStarClicked(email)
        }
    }

    // Hàm hỗ trợ cập nhật icon Star
    private fun updateStarIcon(button: ImageButton, isStarred: Boolean) {
        val icon = if (isStarred) R.drawable.ic_star_outline else R.drawable.ic_star_outline
        button.setImageResource(icon)
        // Thay đổi màu sắc thành vàng nếu được đánh dấu
        val color = if (isStarred) Color.YELLOW else ContextCompat.getColor(button.context, R.color.gray_star) // Giả sử bạn có color/gray_star
        button.setColorFilter(color)
    }

    override fun getItemCount(): Int = emails.size
}
