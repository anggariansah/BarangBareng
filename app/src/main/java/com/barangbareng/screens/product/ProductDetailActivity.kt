package com.barangbareng.screens.product

import android.os.Bundle
import androidx.activity.viewModels
import com.barangbareng.R
import com.barangbareng.base.BaseActivity
import com.barangbareng.databinding.ActivityProductDetailBinding
import com.barangbareng.extensions.sendWhatsapp
import com.barangbareng.models.Banner
import com.barangbareng.models.Product
import com.barangbareng.viewmodels.ProductDetailViewModel
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.view_toolbar_activity.view.*

@AndroidEntryPoint
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_product_detail
    private val viewModel by viewModels<ProductDetailViewModel>()
    private var id: String = ""

    override fun ActivityProductDetailBinding.initializeView(savedInstanceState: Bundle?) {
        setupToolbar()
        setupBanner()
        getExtras()
        setupFetcher()
        setupObserver()

        btnSendRent.setOnClickListener {
            sendWhatsapp()
        }
    }

    companion object {
        const val KEY_ID = "KEY_ID"
    }

    private fun getExtras() {
        id = intent?.extras?.getString(KEY_ID) ?: ""
    }

    private fun ActivityProductDetailBinding.setupObserver() {
        with(viewModel) {
            product.observe {
                if (this != null) {
                    setupDetail(this)
                }
            }
        }
    }

    private fun setupFetcher() {
        viewModel.getProduct(id)
    }

    private fun ActivityProductDetailBinding.setupDetail(product: Product) {
        tvTitleProductDetail.text = product.name
        tvPriceProductDetail.text = product.price
        tvDescProductDetail.text = product.description
    }

    private fun ActivityProductDetailBinding.setupBanner() {
        val banners: MutableList<Banner> = arrayListOf()
        banners.add(
            Banner(
                id = "Sewa",
                url = "https://2.bp.blogspot.com/-Nfc0RxNO1qk/Vtq1zNBVg2I/AAAAAAAAAD0/7jV95dFQwAo/s1600/IMG-20160123-WA0000.jpg"
            )
        )

        banners.add(
            Banner(
                id = "Barang",
                url = "https://images.tokopedia.net/img/cache/700/VqbcmM/2020/9/21/bd43aaa0-73b0-49cb-9236-4c6b0ca53557.png"
            )
        )

        val imageListener = ImageListener { position, imageView ->
            val banner = banners[position]
            Glide.with(this@ProductDetailActivity)
                .load(banner.url)
                .into(imageView)
            imageView.setOnClickListener {

            }
        }
        bannerButtonCarouselview.setImageListener(imageListener)
        bannerButtonCarouselview.pageCount = banners.size

    }

    private fun ActivityProductDetailBinding.setupToolbar() {
        with(productDetailToolbar) {
            setupActionBar(
                toolbar_support,
                toolbar_support.toolbar_text_title,
                productDetailAppBar
            )
        }
        toolbarTitle?.text = "Detail Produk"
    }

}