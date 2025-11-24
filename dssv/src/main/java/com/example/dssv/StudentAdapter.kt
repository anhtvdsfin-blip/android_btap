package com.example.dssv
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val students: ArrayList<Student>,
    private val onDeleteClicked: (Student) -> Unit,
    private val onItemClicked: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFullName: TextView = itemView.findViewById(R.id.tvFullName)
        val tvStudentId: TextView = itemView.findViewById(R.id.tvStudentId)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
        val studentInfoLayout: LinearLayout = itemView.findViewById(R.id.studentInfoLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.tvFullName.text = student.fullName
        holder.tvStudentId.text = student.studentId

        // Xử lý sự kiện nhấn nút Xóa
        holder.btnDelete.setOnClickListener {
            onDeleteClicked(student)
        }

        // Xử lý sự kiện nhấn vào phần tử (để cập nhật)
        holder.studentInfoLayout.setOnClickListener {
            onItemClicked(student)
        }
    }

    override fun getItemCount(): Int = students.size
}