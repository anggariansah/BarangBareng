package com.barangbareng.models

data class Banner(
    val id: String = "",
    val priority: Int = 0,
    val url: String = "",
    val result: List<Banner?> = arrayListOf()
) {
    companion object{
        fun empty() : Banner = Banner()
    }
}