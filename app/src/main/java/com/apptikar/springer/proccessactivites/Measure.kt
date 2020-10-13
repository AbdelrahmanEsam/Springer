package com.apptikar.springer.proccessactivites

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.transition.ChangeBounds
import com.apptikar.springer.R
import com.apptikar.springer.common.BaseFragment
import com.apptikar.springer.common.MyApplication.Companion.available
import com.apptikar.springer.databinding.FragmentMeasureBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */

class Measure : BaseFragment() {
    private lateinit  var binding: FragmentMeasureBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_measure, container, false)
        sharedElementEnterTransition = ChangeBounds().apply { duration = 250 }
        sharedElementReturnTransition= ChangeBounds().apply { duration = 250 }
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = (activity as FragmentHolder).viewModel
        val view = binding.root
        requireActivity().window.exitTransition = null
        requireActivity().window.enterTransition = null
        if ((activity as FragmentHolder).mInterstitialAd.isLoaded) {
            (activity as FragmentHolder).mInterstitialAd.show()
        }
        binding.goButton.setOnClickListener {
            if (available) {
                if ((activity as FragmentHolder).viewModel.springHousing.value!!.isNotEmpty()
                    && (activity as FragmentHolder).viewModel.ejectionStroke.value!!.isNotEmpty() && (activity as FragmentHolder).viewModel.springNoOfSpace.value!!.isNotEmpty()
                    && (activity as FragmentHolder).viewModel.springLength.value!!.isNotEmpty() && (activity as FragmentHolder).viewModel.springSpace.value!!.isNotEmpty()
                   )
                {
                    if ((activity as FragmentHolder).viewModel.springHousing.value != "." && (activity as FragmentHolder).viewModel.ejectionStroke.value != "." &&
                        (activity as FragmentHolder).viewModel.springLength.value != "." && (activity as FragmentHolder).viewModel.springSpace.value != "."
                       ) {
                        if ((activity as FragmentHolder).viewModel.springSpace.value!!.toDouble() > 0 && (activity as FragmentHolder).viewModel.springHousing.value!!.toDouble() > 0 &&
                            (activity as FragmentHolder).viewModel.springLength.value!!.toDouble() > 0 && (activity as FragmentHolder).viewModel.springNoOfSpace.value!!.toDouble() > 0 &&
                            (activity as FragmentHolder).viewModel.ejectionStroke.value!!.toDouble() > 0
                           ) {
                            if ((activity as FragmentHolder).viewModel.springSpace.value!!.toDouble() < 100 &&
                                    (activity as FragmentHolder).viewModel.springHousing.value!!.toDouble() <= (activity as FragmentHolder).viewModel.springLength.value!!.toDouble()
                                && (activity as FragmentHolder).viewModel.springLength.value!!.toDouble() < 5000 && (activity as FragmentHolder).viewModel.ejectionStroke.value!!.toDouble() < 500
                               ) {
                                if (90 <= 100 * ((activity as FragmentHolder).viewModel.springHousing.value!!.toDouble() / (activity as FragmentHolder).viewModel.springLength.value!!.toDouble())
                                    && (activity as FragmentHolder).viewModel.springHousing.value!!.toDouble() / (activity as FragmentHolder).viewModel.springLength.value!!.toDouble() * 100 <= 99
                                ) {
                                    (activity as FragmentHolder).viewModel.result()
                                    (activity as FragmentHolder).fragmentTrans(R.id.action_measure_to_results)
                                } else {
                                    longToast("Spring Housing must be >= 90 % and <= 99 % of Spring Length")
                                }
                            } else {
                                if ((activity as FragmentHolder).viewModel.springLength.value!!.toDouble() >= 5000
                                ) {
                                    binding.springlength.error = "Spring space must  be < 5000 mm"
                                }
                                ontextchanged(binding.springlength)
                                ontextchanged(binding.springejectionstroke)
                                ontextchanged(binding.springspaces)
                                ontextchanged(binding.springnospaces)
                                ontextchanged(binding.springhousing)
                                if ((activity as FragmentHolder).viewModel.ejectionStroke.value!!.toDouble() >= 500
                                ) {
                                    binding.springejectionstroke.error =
                                        "Spring space must  be < 500 mm"
                                }
                                if ((activity as FragmentHolder).viewModel.springSpace.value!!.toDouble() >= 100
                                ) {
                                    binding.springspaces.error = "Spring space must  be < 100 mm"
                                }
                                if ((activity as FragmentHolder).viewModel.springHousing.value!!.toDouble() > (activity as FragmentHolder).viewModel.springLength.value!!.toDouble()
                                ) {
                                    binding.springhousing.error =
                                        "Spring Housing must be < Spring Length "
                                }
                            }
                        } else {
                            longToast("you Can't Enter any Field with 0")
                        }
                    } else {
                        longToast("you Can't Enter any Field with Dot")
                    }
                } else {
                    longToast("All Fields is required")
                }
            } else {
                longToast("No Internet access")
            }
        }
        return view
    }

    private fun ontextchanged(layout: TextInputLayout) {
        layout.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                layout.error = null
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }
}