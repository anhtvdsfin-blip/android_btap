package com.example.gmail


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View // Cần thiết cho các thao tác View khác

class MainActivity : AppCompatActivity() { // Activity chính của module

    private lateinit var recyclerViewEmails: RecyclerView
    private lateinit var emailAdapter: EmailAdapter
    private val emailList = mutableListOf<Email>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sử dụng file activity_main.xml mới (chỉ chứa RecyclerView)
        setContentView(R.layout.activity_main)

        // Ánh xạ RecyclerView
        // Lưu ý: ID là recyclerViewEmails, khớp với layout mới
        recyclerViewEmails = findViewById(R.id.recyclerViewEmails)

        addSampleData()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // Bạn cần đảm bảo EmailAdapter đã tồn tại và import đúng
        emailAdapter = EmailAdapter(emailList) { clickedEmail ->
            Toast.makeText(this, "${clickedEmail.sender} - Star: ${if (clickedEmail.isStarred) "Đã đánh dấu" else "Chưa đánh dấu"}", Toast.LENGTH_SHORT).show()
        }
        recyclerViewEmails.layoutManager = LinearLayoutManager(this)
        recyclerViewEmails.adapter = emailAdapter
    }

    // Thêm dữ liệu mẫu
    private fun addSampleData() {
        emailList.add(Email("Edurila.com", "$19 Only (First 10 spots) - Bestselling...", "Are you looking to Learn Web Designing..."))
        emailList.add(Email("Chris Abad", "Help make Campaign Monitor better", "Let us know your thoughts! No Images..."))
        emailList.add(Email("Tuto.com", "8h de formation gratuite et les nouvea...", "Photoshop, SEO, Blender, CSS, WordPress..."))
        emailList.add(Email("Google", "Security alert for your linked Google account", "Someone just used your password...", isStarred = true))
    }
}