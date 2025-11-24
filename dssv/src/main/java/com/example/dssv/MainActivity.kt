package com.example.dssv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etStudentId: EditText
    private lateinit var etFullName: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var recyclerViewStudents: RecyclerView
    private lateinit var studentAdapter: StudentAdapter

    private val studentList = ArrayList<Student>()
    private var selectedStudent: Student? = null // Lưu sinh viên đang được chọn để cập nhật

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ View
        etStudentId = findViewById(R.id.etStudentId)
        etFullName = findViewById(R.id.etFullName)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerViewStudents = findViewById(R.id.recyclerViewStudents)

        // Khởi tạo Adapter và RecyclerView
        setupRecyclerView()

        // Thêm dữ liệu mẫu (như trong ảnh)
        addSampleData()

        // Xử lý sự kiện nút Add
        btnAdd.setOnClickListener {
            addStudent()
        }

        // Xử lý sự kiện nút Update
        btnUpdate.setOnClickListener {
            updateStudent()
        }
    }

    private fun setupRecyclerView() {
        studentAdapter = StudentAdapter(
            students = studentList,
            onDeleteClicked = { student -> // Hàm lambda xử lý xóa
                deleteStudent(student)
            },
            onItemClicked = { student -> // Hàm lambda xử lý chọn phần tử
                selectStudentForUpdate(student)
            }
        )
        recyclerViewStudents.layoutManager = LinearLayoutManager(this)
        recyclerViewStudents.adapter = studentAdapter
    }

    // Thêm dữ liệu mẫu
    private fun addSampleData() {
        studentList.add(Student("20200001", "Nguyễn Văn A"))
        studentList.add(Student("20200002", "Trần Thị B"))
        studentList.add(Student("20200003", "Lê Văn C"))
        studentList.add(Student("20200004", "Phạm Thị D"))
        studentList.add(Student("20200005", "Hoàng Văn E"))
        studentList.add(Student("20200006", "Vũ Thị F"))
        studentList.add(Student("20200007", "Đặng Văn G"))
        studentList.add(Student("20200008", "Bùi Thị H"))
        studentList.add(Student("20200009", "Hồ Văn I"))
        studentAdapter.notifyDataSetChanged()
    }

    // --- Logic chính ---

    // Chức năng 1: Thêm mới sinh viên
    private fun addStudent() {
        val id = etStudentId.text.toString().trim()
        val name = etFullName.text.toString().trim()

        if (id.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ MSSV và Họ tên.", Toast.LENGTH_SHORT).show()
            return
        }

        // Kiểm tra trùng lặp MSSV
        if (studentList.any { it.studentId == id }) {
            Toast.makeText(this, "MSSV $id đã tồn tại.", Toast.LENGTH_SHORT).show()
            return
        }

        val newStudent = Student(id, name)
        studentList.add(newStudent)
        studentAdapter.notifyItemInserted(studentList.size - 1) // Thông báo cho Adapter cập nhật view

        // Xóa nội dung trên EditText
        clearInputFields()
        Toast.makeText(this, "Đã thêm sinh viên $name.", Toast.LENGTH_SHORT).show()
    }

    // Chức năng 2: Xóa sinh viên
    private fun deleteStudent(student: Student) {
        val position = studentList.indexOf(student)
        if (position != -1) {
            studentList.removeAt(position)
            studentAdapter.notifyItemRemoved(position) // Thông báo cho Adapter cập nhật view
            Toast.makeText(this, "Đã xóa sinh viên ${student.fullName}.", Toast.LENGTH_SHORT).show()

            // Nếu sinh viên đang được chọn bị xóa, clear input
            if (selectedStudent == student) {
                clearInputFields()
                selectedStudent = null
            }
        }
    }

    // Chức năng 3.1: Nhấn vào phần tử để hiển thị thông tin
    private fun selectStudentForUpdate(student: Student) {
        selectedStudent = student
        etStudentId.setText(student.studentId)
        etFullName.setText(student.fullName)

        // Không cho phép sửa MSSV khi đang ở chế độ Update
        etStudentId.isEnabled = false
        btnAdd.isEnabled = false
        btnUpdate.isEnabled = true
        Toast.makeText(this, "Đã chọn ${student.fullName} để cập nhật.", Toast.LENGTH_SHORT).show()
    }

    // Chức năng 3.2: Cập nhật thông tin sinh viên
    private fun updateStudent() {
        val studentToUpdate = selectedStudent
        val newName = etFullName.text.toString().trim()

        if (studentToUpdate == null) {
            Toast.makeText(this, "Vui lòng chọn một sinh viên để cập nhật.", Toast.LENGTH_SHORT).show()
            return
        }

        if (newName.isEmpty()) {
            Toast.makeText(this, "Họ tên không được để trống.", Toast.LENGTH_SHORT).show()
            return
        }

        // Cập nhật dữ liệu
        val position = studentList.indexOf(studentToUpdate)
        if (position != -1) {
            studentToUpdate.fullName = newName
            studentAdapter.notifyItemChanged(position) // Thông báo cho Adapter cập nhật view
            Toast.makeText(this, "Cập nhật thành công cho MSSV ${studentToUpdate.studentId}.", Toast.LENGTH_SHORT).show()
        }

        // Reset trạng thái
        clearInputFields()
        selectedStudent = null
    }

    // Hàm hỗ trợ: Xóa dữ liệu và reset trạng thái nhập liệu
    private fun clearInputFields() {
        etStudentId.text.clear()
        etFullName.text.clear()
        etStudentId.isEnabled = true // Mở lại MSSV để thêm mới
        btnAdd.isEnabled = true
        btnUpdate.isEnabled = false // Tắt nút Update
    }
}