package com.example.basesetupforandroid.util.base

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.basesetupforandroid.data.model.EventModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

@SuppressLint("Registered")
abstract class BaseActivity<BINDING : ViewDataBinding> : AppCompatActivity() {

    private lateinit var mContext: Activity
    lateinit var binding: BINDING
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutResourceId())
        binding.lifecycleOwner = this

        mContext = this@BaseActivity
        clickListeners()
    }

    protected abstract fun getLayoutResourceId(): Int
    protected abstract fun clickListeners()

    @Subscribe
    internal open fun onEventFired(event: EventModel) {}

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPress()
        }
    }

    internal open fun onBackPress() {
        finish()
    }

    fun setTransparentStatusBar() {
        this.let { it ->
            it.window?.let {
                it.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                it.statusBarColor = Color.TRANSPARENT
                it.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }
    }
}