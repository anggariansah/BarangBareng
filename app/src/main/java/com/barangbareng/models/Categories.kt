package com.barangbareng.models

data class Categories(
    val id: String = "",
    val title: String = "",
    val icon: String = "",
    val result: List<Categories?> = arrayListOf()
) {
    companion object{
        fun empty() : Categories = Categories()
    }
}