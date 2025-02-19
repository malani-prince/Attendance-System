package com.example.attendancesystem

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendancesystem.databinding.FragmentOverViewBinding

class OverViewFragment : Fragment() {
    
    private lateinit var binding: FragmentOverViewBinding
    private lateinit var studentInformationAdapter: StudentInformationAdapter
    private lateinit var arrayStudentData: ArrayList<StudentInfo>
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOverViewBinding.inflate(inflater)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        onClicks()
        setAdapter()
    }
    
    private fun init() {
        arrayStudentData = arrayListOf()
        arrayStudentData.apply {
            add(StudentInfo("8", "Information Technology", "I", 15, 12, 3))
            add(StudentInfo("6", "Computer Science", "B", 20, 18, 2))
            add(StudentInfo("4", "Electronics", "A", 25, 22, 3))
            add(StudentInfo("7", "Mechanical", "C", 18, 16, 2))
            add(StudentInfo("5", "Civil Engineering", "D", 30, 28, 2))
            add(StudentInfo("3", "Electrical", "A", 22, 20, 2))
            add(StudentInfo("8", "Software Engineering", "E", 17, 15, 2))
            add(StudentInfo("6", "AI & Data Science", "F", 21, 19, 2))
            add(StudentInfo("7", "Automobile", "B", 19, 17, 2))
            add(StudentInfo("5", "Robotics", "C", 23, 21, 2))
        }
    }
    
    private fun onClicks() {
        binding.apply {
        }
    }
    
    private fun setAdapter() {
        binding.apply {
            studentInformationAdapter = StudentInformationAdapter(arrayStudentData)
            rvAttendance.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvAttendance.adapter = studentInformationAdapter
        }
    }
    
}