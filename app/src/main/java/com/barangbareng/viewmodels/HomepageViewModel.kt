package com.barangbareng.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barangbareng.models.Banner
import com.barangbareng.models.Categories
import com.barangbareng.models.Product
import com.barangbareng.network.Resource
import com.barangbareng.repository.PreferencesRepository
import com.barangbareng.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomepageViewModel @Inject constructor(
    val remoteRepository: RemoteRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val loading: MutableLiveData<Boolean>
        get() = _loading


    private val _banner: MutableLiveData<List<Banner>> by lazy {
        MutableLiveData<List<Banner>>(arrayListOf())
    }

    val banner: MutableLiveData<List<Banner>>
        get() = _banner

    private val _categories: MutableLiveData<List<Categories>> by lazy {
        MutableLiveData<List<Categories>>(arrayListOf())
    }

    val categories: MutableLiveData<List<Categories>>
        get() = _categories

    private val _recommendation: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>(arrayListOf())
    }

    val recommendation: MutableLiveData<List<Product>>
        get() = _recommendation

    private val _newestProduct: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>(arrayListOf())
    }

    val newestProduct: MutableLiveData<List<Product>>
        get() = _newestProduct

    fun getListBanner() {
        viewModelScope.launch {
            _loading.value = true
            _banner.value = null
            when (val response = remoteRepository.getBanner()) {
                is Resource.Success -> {
                    val data = response.data
                    val listBanner: MutableList<Banner> = arrayListOf()

                    data?.result?.forEach {
                        listBanner.add(
                            Banner(
                                id = it?.id ?: "",
                                priority = it?.priority ?: 0,
                                url = it?.url ?: ""
                            )
                        )
                    }

                    _banner.value = listBanner
                    _loading.value = false
                }
                is Resource.Error -> {
                    _loading.value = false
                }
            }
        }
    }

    fun getListCategories() {
        viewModelScope.launch {
            _loading.value = true
            _categories.value = null
            when (val response = remoteRepository.getCategories()) {
                is Resource.Success -> {
                    val data = response.data
                    val listCategories: MutableList<Categories> = arrayListOf()

                    data?.result?.forEach {
                        listCategories.add(
                            Categories(
                                id = it?.id ?: "",
                                title = it?.title ?: "",
                                icon = it?.icon ?: ""
                            )
                        )
                    }

                    _categories.value = listCategories
                    _loading.value = false
                }
                is Resource.Error -> {
                    _loading.value = false
                }
            }
        }
    }

    fun getRecommendationProduct() {
        viewModelScope.launch {
            _loading.value = true
            _recommendation.value = null
            when (val response = remoteRepository.getRecommendationProduct()) {
                is Resource.Success -> {
                    val data = response.data
                    val listProduct: MutableList<Product> = arrayListOf()


                    data?.result?.forEach {
                        listProduct.add(
                            Product(
                                id = it?.id ?: "",
                                name = it?.name ?: "",
                                price = it?.price ?: "",
                                description = it?.description ?: "",
                                url = it?.url ?: "",
                                photo = it?.photo ?: "",
                            )
                        )
                    }

                    _recommendation.value = listProduct
                    _loading.value = false
                }
                is Resource.Error -> {
                    _loading.value = false
                }
            }
        }
    }

    fun getNewestProduct() {
        viewModelScope.launch {
            _loading.value = true
            _newestProduct.value = null
            when (val response = remoteRepository.getNewProduct()) {
                is Resource.Success -> {
                    val data = response.data
                    val listProduct: MutableList<Product> = arrayListOf()

                    data?.result?.forEach {
                        listProduct.add(
                            Product(
                                id = it?.id ?: "",
                                name = it?.name ?: "",
                                price = it?.price ?: "",
                                description = it?.description ?: "",
                                url = it?.url ?: "",
                                photo = it?.photo ?: "",
                            )
                        )
                    }

                    _newestProduct.value = listProduct
                    _loading.value = false
                }
                is Resource.Error -> {
                    _loading.value = false
                }
            }
        }
    }

}