package com.barangbareng.repository

import com.barangbareng.models.Banner
import com.barangbareng.models.Categories
import com.barangbareng.models.Product
import com.barangbareng.network.ApiRequest
import com.barangbareng.network.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    val apiRequest: ApiRequest,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RemoteRepository {

    override suspend fun getBanner(): Resource<Banner> =
        withContext(ioDispatcher) {
            return@withContext try {
                val response = apiRequest.getBanner()
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Resource.Success(result)
                } else {
                    Resource.Error("An Error occurred")
                }
            } catch (e: Exception) {
                Resource.Error("An $e occurred")
            }
        }

    override suspend fun getCategories(): Resource<Categories> =
        withContext(ioDispatcher) {
            return@withContext try {
                val response = apiRequest.getCategories()
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Resource.Success(result)
                } else {
                    Resource.Error("An Error occurred")
                }
            } catch (e: Exception) {
                Resource.Error("An $e occurred")
            }
        }

    override suspend fun getNewProduct(): Resource<Product> =
        withContext(ioDispatcher) {
            return@withContext try {
                val response = apiRequest.getNewProduct()
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Resource.Success(result)
                } else {
                    Resource.Error("An Error occurred")
                }
            } catch (e: Exception) {
                Resource.Error("An $e occurred")
            }
        }

    override suspend fun getRecommendationProduct(): Resource<Product> =
        withContext(ioDispatcher) {
            return@withContext try {
                val response = apiRequest.getRecommendationProduct()
                val result = response.body()
                println("INI HASIL =" + result)
                if (response.isSuccessful && result != null) {
                    Resource.Success(result)
                } else {
                    Resource.Error("An Error occurred")
                }
            } catch (e: Exception) {
                Resource.Error("An $e occurred")
            }
        }

    override suspend fun getProductCategories(id: String): Resource<Product> =
        withContext(ioDispatcher) {
            return@withContext try {
                val response = apiRequest.getProductCategories(id)
                val result = response.body()
                println("INI HASIL =" + result)
                if (response.isSuccessful && result != null) {
                    Resource.Success(result)
                } else {
                    Resource.Error("An Error occurred")
                }
            } catch (e: Exception) {
                Resource.Error("An $e occurred")
            }
        }

    override suspend fun getAllProduct(): Resource<Product> =
        withContext(ioDispatcher) {
            return@withContext try {
                val response = apiRequest.getAllProduct()
                val result = response.body()
                println("INI HASIL =" + result)
                if (response.isSuccessful && result != null) {
                    Resource.Success(result)
                } else {
                    Resource.Error("An Error occurred")
                }
            } catch (e: Exception) {
                Resource.Error("An $e occurred")
            }
        }

    override suspend fun getSearchProduct(keyword: String): Resource<Product> =
        withContext(ioDispatcher) {
            return@withContext try {
                val response = apiRequest.getSearchProduct(keyword)
                val result = response.body()
                println("INI HASIL =" + result)
                if (response.isSuccessful && result != null) {
                    Resource.Success(result)
                } else {
                    Resource.Error("An Error occurred")
                }
            } catch (e: Exception) {
                Resource.Error("An $e occurred")
            }
        }

    override suspend fun getDetailProduct(id: String): Resource<Product?> =
        withContext(ioDispatcher) {
            return@withContext try {
                val response = apiRequest.getDetailProduct(id)
                val result = response.body()
                println("INI RESULT DETAIL =" + result)
                if (response.isSuccessful && result != null) {
                    Resource.Success(result)
                } else {
                    Resource.Error("An Error occurred")
                }
            } catch (e: Exception) {
                Resource.Error("An $e occurred")
            }
        }

}