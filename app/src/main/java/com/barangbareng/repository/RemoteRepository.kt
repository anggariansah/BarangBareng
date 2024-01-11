package com.barangbareng.repository

import com.barangbareng.models.Banner
import com.barangbareng.models.Categories
import com.barangbareng.models.Product
import com.barangbareng.network.Resource

interface RemoteRepository {
    suspend fun getBanner(): Resource<Banner>
    suspend fun getCategories(): Resource<Categories>
    suspend fun getNewProduct(): Resource<Product>
    suspend fun getRecommendationProduct(): Resource<Product>
    suspend fun getProductCategories(id : String): Resource<Product>
    suspend fun getAllProduct(): Resource<Product>
    suspend fun getSearchProduct(keyword : String): Resource<Product>
    suspend fun getDetailProduct(id : String): Resource<Product?>
}