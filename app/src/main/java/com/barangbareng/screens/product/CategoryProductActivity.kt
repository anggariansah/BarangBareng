package com.barangbareng.screens.product

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.barangbareng.BR
import com.barangbareng.R
import com.barangbareng.adapters.RecyclerViewAdapter
import com.barangbareng.base.BaseActivity
import com.barangbareng.databinding.ActivityProductCategoriesBinding
import com.barangbareng.databinding.ItemHomeProductBinding
import com.barangbareng.models.Product
import com.barangbareng.viewmodels.ProductCategoriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_toolbar_activity.view.*

@AndroidEntryPoint
class CategoryProductActivity : BaseActivity<ActivityProductCategoriesBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_product_categories
    private lateinit var adapterCategories: RecyclerViewAdapter<Product, ItemHomeProductBinding>
    private val emptyProduct: Product by lazy { Product.empty() }
    private var categories: String = ""
    private var id: String = ""
    private val viewModel by viewModels<ProductCategoriesViewModel>()

    override fun ActivityProductCategoriesBinding.initializeView(savedInstanceState: Bundle?) {
        getExtras()
        setupToolbar()
        setupFetcher()
        setupObserver()
        setupAdapter()
    }

    companion object {
        const val KEY_CATEGORIES = "KEY_CATEGORIES"
        const val KEY_ID = "KEY_ID"
    }

    private fun getExtras() {
        categories = intent?.extras?.getString(KEY_CATEGORIES) ?: ""
        id = intent?.extras?.getString(KEY_ID) ?: ""
    }

    private fun setupFetcher() {
        viewModel.getProductCategories(id)
    }

    private fun setupObserver() {
        with(viewModel) {
            categoriesProduct.observe {
                if (this != null) {
                    adapterCategories.updateList(this)
                }
            }
        }
    }

    private fun ActivityProductCategoriesBinding.setupToolbar() {
        with(productDetailToolbar) {
            setupActionBar(
                toolbar_support,
                toolbar_support.toolbar_text_title,
                productDetailAppBar
            )
        }
        toolbarTitle?.text = "Kategori $categories"
    }

    private fun ActivityProductCategoriesBinding.setupAdapter() {
        adapterCategories = RecyclerViewAdapter(
            arrayOf(emptyProduct).toList(),
            R.layout.item_home_product,
            BR.product
        ) { itemProduct, product ->

            itemProduct.itemProductContainer.setOnClickListener {
                val intent = Intent(this@CategoryProductActivity, ProductDetailActivity::class.java)
                intent.putExtra(CategoryProductActivity.KEY_ID, product.id)
                startActivity(intent)
            }
        }

        rvCategoriesProduct.apply {
            layoutManager = GridLayoutManager(this@CategoryProductActivity, 2)
            adapter = adapterCategories
        }
    }

}