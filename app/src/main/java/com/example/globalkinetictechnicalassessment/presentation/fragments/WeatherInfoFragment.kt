package com.example.globalkinetictechnicalassessment.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.globalkinetictechnicalassessment.R
import com.example.globalkinetictechnicalassessment.databinding.FragmentWeatherInfoBinding
import com.example.globalkinetictechnicalassessment.models.WeatherInformation
import com.example.globalkinetictechnicalassessment.presentation.IWeatherInformation
import com.example.globalkinetictechnicalassessment.viewmodels.WeatherInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherInfoFragment : Fragment(), IWeatherInformation {

    @Inject
    lateinit var viewModel: WeatherInfoViewModel

    private lateinit var binding: FragmentWeatherInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherInfoBinding.inflate(inflater, container, false)
        viewModel.view = this
        binding.btnRefresh.setOnClickListener {
            viewModel.refreshButtonTapped()
        }
        viewModel.onCreateView()
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        viewModel.handleOnRequestPermissionsResult(context, requestCode, grantResults)
    }

    override fun getFragment(): Fragment {
        return this
    }

    override fun showProgressOverlay() {
        binding.progressOverlay.visibility = View.VISIBLE
    }

    override fun hideProgressOverlay() {
        binding.progressOverlay.visibility = View.GONE
    }

    override fun displayWeatherInformation(weatherInformation: WeatherInformation) {
        binding.tvCityName.text = weatherInformation.name
        binding.tvWeatherDescription.text = weatherInformation.weather?.first()?.description
        val tempCelciusString = weatherInformation.main?.temp.toString()
        binding.tvTemperature.text = getString(R.string.temperature_placeholder, tempCelciusString)
    }

}