package com.barangbareng.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.barangbareng.BR
import com.barangbareng.R
import com.barangbareng.adapters.RecyclerViewAdapter
import com.barangbareng.base.BaseActivity
import com.barangbareng.databinding.ActivityMainBinding
import com.barangbareng.databinding.ItemCategoriesProductBinding
import com.barangbareng.databinding.ItemHomeProductBinding
import com.barangbareng.models.Banner
import com.barangbareng.models.Categories
import com.barangbareng.models.Product
import com.barangbareng.screens.product.CategoryProductActivity
import com.barangbareng.screens.product.ProductDetailActivity
import com.barangbareng.screens.product.SearchProductActivity
import com.barangbareng.utils.Constants
import com.barangbareng.viewmodels.HomepageViewModel
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main
    private lateinit var newAdapter: RecyclerViewAdapter<Product, ItemHomeProductBinding>
    private lateinit var recomendationAdapter: RecyclerViewAdapter<Product, ItemHomeProductBinding>
    private lateinit var categoriesAdapter: RecyclerViewAdapter<Categories, ItemCategoriesProductBinding>
    private val emptyProduct: Product by lazy { Product.empty() }
    private val emptyCategories: Categories by lazy { Categories.empty() }
    private val viewModel by viewModels<HomepageViewModel>()

    override fun ActivityMainBinding.initializeView(savedInstanceState: Bundle?) {
        setupFetcher()
        setupListener()
        setupListNewProduct()
        setupListRecommendation()
        setupCategoriesProduct()
        setupObserver()
    }

    private fun ActivityMainBinding.setupObserver() {
        with(viewModel) {
            banner.observe {
                if (this != null) {
                    setupBanner(this)
                }
            }

            recommendation.observe {
                if (this != null) {
                    recomendationAdapter.updateList(this)
                }
            }

            newestProduct.observe {
                if (this != null) {
                    newAdapter.updateList(this)
                }
            }

            categories.observe {
                if (this != null) {
                    categoriesAdapter.updateList(this)
                }
            }
        }
    }

    private fun setupFetcher() {
        viewModel.getListBanner()
        viewModel.getRecommendationProduct()
        viewModel.getNewestProduct()
        viewModel.getListCategories()
    }

    private fun ActivityMainBinding.setupListener() {
        edtSearchProductMain.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchProductActivity::class.java)
            startActivity(intent)
        }

        categoriesElectronicMain.setOnClickListener {
            val intent = Intent(this@MainActivity, CategoryProductActivity::class.java)
            intent.putExtra(CategoryProductActivity.KEY_CATEGORIES, Constants.ELECTRONIC)
            startActivity(intent)
        }

        categoriesSportMain.setOnClickListener {
            val intent = Intent(this@MainActivity, CategoryProductActivity::class.java)
            intent.putExtra(CategoryProductActivity.KEY_CATEGORIES, Constants.SPORT)
            startActivity(intent)
        }

        categoriesShirtMain.setOnClickListener {
            val intent = Intent(this@MainActivity, CategoryProductActivity::class.java)
            intent.putExtra(CategoryProductActivity.KEY_CATEGORIES, Constants.SHIRT)
            startActivity(intent)
        }

        categoriesMusicMain.setOnClickListener {
            val intent = Intent(this@MainActivity, CategoryProductActivity::class.java)
            intent.putExtra(CategoryProductActivity.KEY_CATEGORIES, Constants.MUSIC)
            startActivity(intent)
        }

        categoriesOutdoorMain.setOnClickListener {
            val intent = Intent(this@MainActivity, CategoryProductActivity::class.java)
            intent.putExtra(CategoryProductActivity.KEY_CATEGORIES, Constants.OUTDOOR)
            startActivity(intent)
        }

        categoriesEventMain.setOnClickListener {
            val intent = Intent(this@MainActivity, CategoryProductActivity::class.java)
            intent.putExtra(CategoryProductActivity.KEY_CATEGORIES, Constants.EVENT)
            startActivity(intent)
        }
    }

    private fun ActivityMainBinding.setupListNewProduct() {
        newAdapter = RecyclerViewAdapter(
            arrayOf(emptyProduct).toList(),
            R.layout.item_home_product,
            BR.product
        ) { itemProduct, product ->

            itemProduct.itemProductContainer.setOnClickListener {
                val intent = Intent(this@MainActivity, ProductDetailActivity::class.java)
                intent.putExtra(CategoryProductActivity.KEY_ID, product.id)
                startActivity(intent)
            }
        }
        val linearLayoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        rvNewProductMain.apply {
            layoutManager = linearLayoutManager
            adapter = newAdapter
        }
    }

    private fun ActivityMainBinding.setupListRecommendation() {
        recomendationAdapter = RecyclerViewAdapter(
            arrayOf(emptyProduct).toList(),
            R.layout.item_home_product,
            BR.product
        ) { itemProduct, product ->

            itemProduct.itemProductContainer.setOnClickListener {
                val intent = Intent(this@MainActivity, ProductDetailActivity::class.java)
                intent.putExtra(CategoryProductActivity.KEY_ID, product.id)
                startActivity(intent)
            }
        }
        val linearLayoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        rvRecommendationMain.apply {
            layoutManager = linearLayoutManager
            adapter = recomendationAdapter
        }
    }

    private fun ActivityMainBinding.setupCategoriesProduct() {
        categoriesAdapter = RecyclerViewAdapter(
            arrayOf(emptyCategories).toList(),
            R.layout.item_categories_product,
            BR.categories
        ) { itemProduct, product ->

            Glide.with(this@MainActivity)
                .load("http://10.0.2.2/barangbareng_be/" + product.icon)
                .into(itemProduct.itemIconCategories)

            itemProduct.linearItemCategoriesContainer.setOnClickListener {
                val intent = Intent(this@MainActivity, CategoryProductActivity::class.java)
                intent.putExtra(CategoryProductActivity.KEY_ID, product.id)
                intent.putExtra(CategoryProductActivity.KEY_CATEGORIES, product.title)
                startActivity(intent)
            }
        }
        rvCategoriesProduct.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            adapter = categoriesAdapter
        }
    }

    private fun ActivityMainBinding.setupBanner(listBanner: List<Banner>) {
        val imageListener = ImageListener { position, imageView ->
            val banner = listBanner[position]
            Glide.with(this@MainActivity)
                .load("http://10.0.2.2/barangbareng_be/" + banner.url)
                .into(imageView)
            imageView.setOnClickListener {

            }
        }
        bannerButtonCarouselview.setImageListener(imageListener)
        bannerButtonCarouselview.pageCount = listBanner.size
    }

}