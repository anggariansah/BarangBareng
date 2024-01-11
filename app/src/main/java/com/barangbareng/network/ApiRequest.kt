package com.barangbareng.network

import com.barangbareng.models.Banner
import com.barangbareng.models.Categories
import com.barangbareng.models.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {

    @GET("get_list_banner.php")
    suspend fun getBanner(): Response<Banner>

    @GET("get_list_categories.php")
    suspend fun getCategories(): Response<Categories>

    @GET("get_list_product_new.php")
    suspend fun getNewProduct(): Response<Product>

    @GET("get_list_product_recommendation.php")
    suspend fun getRecommendationProduct(): Response<Product>

    @GET("get_list_all_product.php")
    suspend fun getAllProduct(): Response<Product>

    @GET("get_list_product_categories.php")
    suspend fun getProductCategories(
        @Query("id_categories") id: String,
    ): Response<Product>

    @GET("get_list_search_product.php")
    suspend fun getSearchProduct(
        @Query("keyword") keyword: String,
    ): Response<Product?>

    @GET("get_detail_product.php")
    suspend fun getDetailProduct(
        @Query("id") id: String,
    ): Response<Product>

}