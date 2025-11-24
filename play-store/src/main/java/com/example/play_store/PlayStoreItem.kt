package com.example.play_store

// Model cho một ứng dụng/game đơn lẻ
data class AppItem(
    val title: String,
    val category: String,
    val rating: Double,
    val size: String,
    val iconResId: Int // Resource ID của icon (ví dụ: R.drawable.ic_app_icon)
)

// Model cho các Phần (Sections) trên màn hình chính
// Model cho các Phần (Sections) trên màn hình chính
sealed class PlayStoreItem {
    data class Header(val title: String) : PlayStoreItem()

    // Dùng cho danh sách các mục con theo chiều ngang (OK)
    data class HorizontalList(val apps: List<AppItem>) : PlayStoreItem()

    // THÊM: Dùng cho MỘT mục ứng dụng dọc (Vertical App Item)
    data class VerticalAppItem(val app: AppItem) : PlayStoreItem()

    // BỎ: Lớp VerticalList cũ không còn được dùng theo cách này.
}