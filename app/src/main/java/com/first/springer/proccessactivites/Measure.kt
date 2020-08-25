package com.first.springer.proccessactivites

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.first.springer.R
import com.first.springer.common.Basefragment
import com.first.springer.common.MyApplication.Companion.available
import com.first.springer.databinding.FragmentMeasureBinding
import com.google.android.material.textfield.TextInputLayout
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Measure : Basefragment() {
    private lateinit  var binding: FragmentMeasureBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_measure, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = (activity as Fragmentholder).viewModel
        val view = binding.root
        shorttoast("a7a")
        requireActivity().window.exitTransition = null
        requireActivity().window.enterTransition = null
        if ((activity as Fragmentholder).mInterstitialAd.isLoaded) {
            (activity as Fragmentholder).mInterstitialAd.show()
        }
        binding.goButton.setOnClickListener {
            if (available) {
                if ((activity as Fragmentholder).viewModel.SpringHousing.value!!.isNotEmpty()
                    && (activity as Fragmentholder).viewModel.EjectionStroke.value!!.isNotEmpty() && (activity as Fragmentholder).viewModel.SpringNoOfSpace.value!!.isNotEmpty()
                    && (activity as Fragmentholder).viewModel.SpringLength.value!!.isNotEmpty() && (activity as Fragmentholder).viewModel.SpringSpace.value!!.isNotEmpty()
                   )
                {
                    if ((activity as Fragmentholder).viewModel.SpringHousing.value != "." && (activity as Fragmentholder).viewModel.EjectionStroke.value != "." &&
                        (activity as Fragmentholder).viewModel.SpringLength.value != "." && (activity as Fragmentholder).viewModel.SpringSpace.value != "."
                       ) {
                        if ((activity as Fragmentholder).viewModel.SpringSpace.value!!.toDouble() > 0 && (activity as Fragmentholder).viewModel.SpringHousing.value!!.toDouble() > 0 &&
                            (activity as Fragmentholder).viewModel.SpringLength.value!!.toDouble() > 0 && (activity as Fragmentholder).viewModel.SpringNoOfSpace.value!!.toDouble() > 0 &&
                            (activity as Fragmentholder).viewModel.EjectionStroke.value!!.toDouble() > 0
                           ) {
                            if ((activity as Fragmentholder).viewModel.SpringSpace.value!!.toDouble() < 100 &&
                                    (activity as Fragmentholder).viewModel.SpringHousing.value!!.toDouble() <= (activity as Fragmentholder).viewModel.SpringLength.value!!.toDouble()
                                && (activity as Fragmentholder).viewModel.SpringLength.value!!.toDouble() < 5000 && (activity as Fragmentholder).viewModel.EjectionStroke.value!!.toDouble() < 500
                               ) {
                                if (90 <= 100 * ((activity as Fragmentholder).viewModel.SpringHousing.value!!.toDouble() / (activity as Fragmentholder).viewModel.SpringLength.value!!.toDouble())
                                    && (activity as Fragmentholder).viewModel.SpringHousing.value!!.toDouble() / (activity as Fragmentholder).viewModel.SpringLength.value!!.toDouble() * 100 <= 99
                                ) {
                                    (activity as Fragmentholder).viewModel.result()
                                    (activity as Fragmentholder).fragmenttrans(Results())
                                } else {
                                    Longtoast("Spring Housing must be >= 90 % and <= 99 % of Spring Length")
                                }
                            } else {
                                if ((activity as Fragmentholder).viewModel!!.SpringLength.value!!.toDouble() >= 5000
                                ) {
                                    binding.springlength.error = "Spring space must  be < 5000 mm"
                                }
                                ontextchanged(binding.springlength)
                                ontextchanged(binding.springejectionstroke)
                                ontextchanged(binding.springspaces)
                                ontextchanged(binding.springnospaces)
                                ontextchanged(binding.springhousing)
                                if ((activity as Fragmentholder).viewModel.EjectionStroke.value!!.toDouble() >= 500
                                ) {
                                    binding.springejectionstroke.error =
                                        "Spring space must  be < 500 mm"
                                }
                                if ((activity as Fragmentholder).viewModel.SpringSpace.value!!.toDouble() >= 100
                                ) {
                                    binding.springspaces.error = "Spring space must  be < 100 mm"
                                }
                                if ((activity as Fragmentholder).viewModel.SpringHousing.value!!.toDouble() > (activity as Fragmentholder).viewModel.SpringLength.value!!.toDouble()
                                ) {
                                    binding.springhousing.error =
                                        "Spring Housing must be < Spring Length "
                                }
                            }
                        } else {
                            Longtoast("you Can't Enter any Field with 0")
                        }
                    } else {
                        Longtoast("you Can't Enter any Field with Dot")
                    }
                } else {
                    Longtoast("All Fields is required")
                }
            } else {
                Longtoast("No Internet access")
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