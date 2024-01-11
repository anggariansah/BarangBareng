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
class ProductDetailViewModel @Inject constructor(
    val remoteRepository: RemoteRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val loading: MutableLiveData<Boolean>
        get() = _loading

    private val _product: MutableLiveData<Product> by lazy {
        MutableLiveData<Product>(null)
    }

    val product: MutableLiveData<Product>
        get() = _product

    fun getProduct(id: String) {
        viewModelScope.launch {
            _loading.value = true
            _product.value = null
            when (val response = remoteRepository.getDetailProduct(id)) {
                is Resource.Success -> {
                    val data = response.data

                    data?.detail?.let {
                        _product.value = Product(
                            id = it?.id ?: "",
                            name = it?.name ?: "",
                            price = it?.price ?: "",
                            description = it?.description ?: "",
                            url = it?.url ?: "",
                            photo = it?.photo ?: "",
                        )
                    }
                    _loading.value = false
                }
                is Resource.Error -> {
                    _loading.value = false
                }
            }
        }
    }

}