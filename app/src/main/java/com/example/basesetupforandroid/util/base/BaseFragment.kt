package com.example.basesetupforandroid.util.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.basesetupforandroid.data.model.EventModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BaseFragment<BINDING : ViewDataBinding>(@LayoutRes val layoutResId: Int) : Fragment() {

    internal lateinit var binding: BINDING
    internal lateinit var mContext: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EventBus.getDefault().register(this);
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        mContext = requireActivity()
        return binding.root
    }

    @Subscribe(sticky = true)
    internal open fun onEventFired(event: EventModel) {}

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this);
        binding.unbind()
    }
}