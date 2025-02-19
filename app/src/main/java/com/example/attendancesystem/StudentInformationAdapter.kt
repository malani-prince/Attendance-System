package com.example.attendancesystem

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attendancesystem.databinding.RowItemAttendanceDetailsBinding

class StudentInformationAdapter(private var arrayStudentDetailsL: ArrayList<StudentInfo>) : RecyclerView.Adapter<StudentInformationAdapter.Holder>() {
    
    inner class Holder(val binding: RowItemAttendanceDetailsBinding) : RecyclerView.ViewHolder(binding.root)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentInformationAdapter.Holder = Holder(RowItemAttendanceDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    
    override fun onBindViewHolder(holder: StudentInformationAdapter.Holder, position: Int) {
        val student = arrayStudentDetailsL[position]
        
        holder.binding.apply {
            setText(tvSem, student.semester)
            setText(tvDepartment, student.department)
            setText(tvDivision, student.division)
            setText(tvTotalStudent, student.totalStudents.toString())
            setText(tvPresentStudent, student.presentStudents.toString())
            setText(tvAbsentStudent, student.absentStudents.toString())
        }
    }
    
    override fun getItemCount(): Int = arrayStudentDetailsL.size
    
    
}