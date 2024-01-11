package com.barangbareng.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barangbareng.models.Product
import com.barangbareng.network.Resource
import com.barangbareng.repository.PreferencesRepository
import com.barangbareng.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    val remoteRepository: RemoteRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val loading: MutableLiveData<Boolean>
        get() = _loading

    private val _allProduct: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>(arrayListOf())
    }

    val allProduct: MutableLiveData<List<Product>>
        get() = _allProduct

    private val _searchProduct: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>(arrayListOf())
    }

    val searchProduct: MutableLiveData<List<Product>>
        get() = _searchProduct


    fun getAllProduct() {
        viewModelScope.launch {
            _loading.value = true
            _allProduct.value = null
            when (val response = remoteRepository.getAllProduct()) {
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

                    _allProduct.value = listProduct
                    _loading.value = false
                }
                is Resource.Error -> {
                    _loading.value = false
                }
            }
        }
    }

    fun getSearchProduct(keyword: String) {
        viewModelScope.launch {
            _loading.value = true
            _searchProduct.value = null
            when (val response = remoteRepository.getSearchProduct(keyword)) {
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

                    _searchProduct.value = listProduct
                    _loading.value = false
                }
                is Resource.Error -> {
                    _loading.value = false
                }
            }
        }
    }
}