package com.rakuishi.falldetection.presentation.sensor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rakuishi.falldetection.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sensor.*

@AndroidEntryPoint
class SensorFragment : Fragment() {

    companion object {
        fun newInstance() = SensorFragment()
    }

    private val viewModel: SensorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_sensor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.start()
        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.isStoppedLiveData.observe(viewLifecycleOwner, Observer {
            isStoppedChip.isChecked = it
        })
    }
}