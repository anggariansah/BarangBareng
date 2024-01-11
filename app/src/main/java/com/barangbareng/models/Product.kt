package com.barangbareng.models

data class Product(
    val id: String = "",
    val name: String = "",
    val price: String = "",
    val description: String = "",
    val url: String = "",
    val address: String = "",
    val photo: String = "",
    val result: List<Product?> = arrayListOf(),
    val detail: Product? = empty()
) {
    companion object {
        fun empty(): Product = Product()
    }
}