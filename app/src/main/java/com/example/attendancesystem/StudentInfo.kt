package com.example.attendancesystem

data class StudentInfo(
    val semester: String,
    val department: String,
    val division: String,
    val totalStudents: Int,
    val presentStudents: Int,
    val absentStudents: Int
)
