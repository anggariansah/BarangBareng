package com.barangbareng.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding

interface BaseView {
    interface Activity<binding : ViewDataBinding> {
        fun binding.initializeView(savedInstanceState: Bundle?) {}
    }

    interface Fragment<binding : ViewDataBinding> {
        fun binding.initializeView() {}
        fun onBackPressed() {}
    }
}