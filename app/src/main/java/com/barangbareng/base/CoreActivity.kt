package com.barangbareng.base

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.barangbareng.R
import com.google.android.material.appbar.AppBarLayout

abstract class CoreActivity<binding : ViewDataBinding> : AppCompatActivity(),
    BaseView.Activity<binding> {

    companion object {
        const val PROPERTY_ELEVATION = "elevation"
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int
    open lateinit var binding: binding
    var toolbarTitle: AppCompatTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = this
        binding = DataBindingUtil.setContentView(activity, getLayoutId())
        binding.apply {
            this.lifecycleOwner = activity
            this.initializeView(savedInstanceState)
        }
    }

    fun <T> LiveData<T>.observe(function: T.() -> Unit) {
        this.observe(this@CoreActivity, Observer {
            function.invoke(it)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    protected open fun setupActionBar(
        toolbar: Toolbar,
        toolbarTitle: AppCompatTextView,
        appBarLayout: AppBarLayout
    ) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }
        this@CoreActivity.toolbarTitle = toolbarTitle
        val stateListAnimator = StateListAnimator()
        stateListAnimator.addState(
            IntArray(0),
            ObjectAnimator.ofFloat(appBarLayout, PROPERTY_ELEVATION, 0F)
        )
        appBarLayout.stateListAnimator = stateListAnimator
        toolbar.navigationIcon = AppCompatResources.getDrawable(this, R.drawable.ic_arrow_back_black)
        toolbar.contentInsetStartWithNavigation = 0
    }

    protected open fun getVersionNameInfo(): String = try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }

    protected open fun getVersionCodeInfo(): String = try {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            packageInfo.longVersionCode.toString()
        } else {
            packageInfo.versionCode.toString()
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }
}