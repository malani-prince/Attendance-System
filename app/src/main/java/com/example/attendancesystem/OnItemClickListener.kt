package com.example.attendancesystem

interface OnItemClickListener {
    
    fun onItemClick(value: Any?) {}
    
    fun onItemClick(key: Any?, value: Any?) {}
    
    fun onCardClick(key: Any?, value: Any?) {}
    
}