package com.example.play_store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.play_store.ui.theme.MyApplicationTheme
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView // Cho RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager // Cho LinearLayoutManager
import com.example.play_store.PlayStoreAdapter // Đảm bảo đúng tên package của bạn

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewMain = findViewById(R.id.recyclerViewMain)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val data = createSampleData()
        val adapter = PlayStoreAdapter(data)
        recyclerViewMain.layoutManager = LinearLayoutManager(this)
        recyclerViewMain.adapter = adapter
    }

    private fun createSampleData(): List<PlayStoreItem> {
        // Dữ liệu mẫu cho danh sách dọc
        val verticalApps = listOf(
            AppItem("Mech Assemble: Zombie Swarm", "Action", 4.8, "624 MB", R.drawable.ic_app_icon_placeholder),
            AppItem("MU: Hồng Hoả Đảo", "Role Playing", 4.8, "339 MB", R.drawable.ic_app_icon_placeholder),
            AppItem("War Inc: Rising", "Strategy", 4.9, "231 MB", R.drawable.ic_app_icon_placeholder)
        )

        // Trong hàm createSampleData()
        val horizontalApps = listOf<AppItem>( // <-- Thêm <AppItem> để chỉ rõ kiểu
            // Dữ liệu mẫu cho danh sách ngang (đã được tạo trước đó)
            AppItem("Suno - AI Music", "Apps", 4.5, "", R.drawable.ic_app_icon_placeholder),
            AppItem("Claude by...", "Apps", 4.7, "", R.drawable.ic_app_icon_placeholder),
            AppItem("DramaBox - ...", "Apps", 4.6, "", R.drawable.ic_app_icon_placeholder),
            AppItem("Pil...", "Apps", 4.9, "", R.drawable.ic_app_icon_placeholder)
        )


        // Sửa cấu trúc dữ liệu chính:
        val mainList = mutableListOf<PlayStoreItem>()

        // Thêm Header
        mainList.add(PlayStoreItem.Header("Sponsored • Suggested for you"))

        // THAY THẾ VerticalList bằng cách THÊM TỪNG APPITEM VÀO DANH SÁCH CHÍNH
        verticalApps.forEach { app ->
            mainList.add(PlayStoreItem.VerticalAppItem(app)) // SỬ DỤNG CLASS MỚI!
        }

        // Thêm Header và HorizontalList
        mainList.add(PlayStoreItem.Header("Recommended for you"))
        mainList.add(PlayStoreItem.HorizontalList(horizontalApps))

        return mainList
    }
}