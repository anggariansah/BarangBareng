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
class ProductCategoriesViewModel @Inject constructor(
    val remoteRepository: RemoteRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val loading: MutableLiveData<Boolean>
        get() = _loading


    private val _categoriesProduct: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>(arrayListOf())
    }

    val categoriesProduct: MutableLiveData<List<Product>>
        get() = _categoriesProduct

    fun getProductCategories(id: String) {
        viewModelScope.launch {
            _loading.value = true
            _categoriesProduct.value = null
            when (val response = remoteRepository.getProductCategories(id)) {
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

                    _categoriesProduct.value = listProduct
                    _loading.value = false
                }
                is Resource.Error -> {
                    _loading.value = false
                }
            }
        }
    }

}