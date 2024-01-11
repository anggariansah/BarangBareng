package com.barangbareng.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class CoreFragment<binding : ViewDataBinding> : Fragment(), BaseView.Fragment<binding> {

    @LayoutRes
    protected abstract fun getLayoutId(): Int
    open lateinit var binding: binding
    protected open val listenBackPressed = false
    private val callback by lazy {
        object : OnBackPressedCallback(listenBackPressed) {
            override fun handleOnBackPressed() = onBackPressed()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflateConfigurator(inflater, container)

    private fun inflateConfigurator(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            this.initializeView()
        }.root
    }

    fun <T> LiveData<T>.observe(function: T.() -> Unit) {
        this.observe(this@CoreFragment, Observer {
            function.invoke(it)
        })
    }

}