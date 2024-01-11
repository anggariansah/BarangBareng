package com.barangbareng.screens.product

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.barangbareng.BR
import com.barangbareng.R
import com.barangbareng.adapters.RecyclerViewAdapter
import com.barangbareng.base.BaseActivity
import com.barangbareng.databinding.ActivitySearchProductBinding
import com.barangbareng.databinding.ItemHomeProductBinding
import com.barangbareng.extensions.afterTextChanged
import com.barangbareng.extensions.showKeyboard
import com.barangbareng.models.Product
import com.barangbareng.viewmodels.ProductSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchProductActivity : BaseActivity<ActivitySearchProductBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_search_product
    private lateinit var adapterSearch: RecyclerViewAdapter<Product, ItemHomeProductBinding>
    private val emptyProduct: Product by lazy { Product.empty() }
    private val viewModel by viewModels<ProductSearchViewModel>()

    override fun ActivitySearchProductBinding.initializeView(savedInstanceState: Bundle?) {
        showKeyboard()
        setupListRecommendation()
        setupFetcher()
        setupObserver()
        setupDetail()
    }

    private fun ActivitySearchProductBinding.setupDetail() {
        ivBackSearchProduct.setOnClickListener {
            finish()
        }

        edtSearchProduct.afterTextChanged {
            viewModel.getSearchProduct(this)
        }
    }

    private fun setupObserver() {
        with(viewModel) {
            allProduct.observe {
                if (this != null) {
                    adapterSearch.updateList(this)
                }
            }

            searchProduct.observe {
                if (this != null) {
                    adapterSearch.updateList(this)
                }
            }
        }
    }

    private fun setupFetcher() {
        viewModel.getAllProduct()
    }

    private fun ActivitySearchProductBinding.setupListRecommendation() {
        adapterSearch = RecyclerViewAdapter(
            arrayOf(emptyProduct).toList(),
            R.layout.item_home_product,
            BR.product
        ) { itemProduct, product ->

            itemProduct.itemProductContainer.setOnClickListener {
                val intent = Intent(this@SearchProductActivity, ProductDetailActivity::class.java)
                intent.putExtra(CategoryProductActivity.KEY_ID, product.id)
                startActivity(intent)
            }
        }
        rvSearchProduct.apply {
            layoutManager = GridLayoutManager(this@SearchProductActivity, 2)
            adapter = adapterSearch
        }
    }

}