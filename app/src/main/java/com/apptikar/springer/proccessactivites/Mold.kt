package com.apptikar.springer.proccessactivites
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.apptikar.springer.R
import com.apptikar.springer.common.BaseFragment
import com.apptikar.springer.common.MyApplication.Companion.available
import com.apptikar.springer.databinding.FragmentMoldBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */

class Mold : BaseFragment() {
    private lateinit var binding: FragmentMoldBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mold, container, false)
        val view = binding.root
        binding.viewModel = (activity as FragmentHolder).viewModel
        (activity as FragmentHolder).viewModel.moldOrDie.value ="Mold"
        if ((activity as FragmentHolder).viewModel.color.value != null) {
            when ((activity as FragmentHolder).viewModel.color.value) {
                "orange" -> {
                    binding.orangering.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
                    binding.orangeringcheck.visibility = View.VISIBLE
                }
                "red" -> {
                    binding.redring.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
                    binding.redringcheck.visibility = View.VISIBLE
                }
                "blue" -> {
                    binding.bluering.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
                    binding.blueringcheck.visibility = View.VISIBLE
                }
                "yellow" -> {
                    binding.yellowring.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
                    binding.yellowringcheck.visibility = View.VISIBLE
                }
                "green" -> {
                    binding.greenring.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
                    binding.greenringcheck.visibility = View.VISIBLE
                }
                "gray" -> {
                    binding.grayring.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
                    binding.grayringcheck.visibility = View.VISIBLE
                }
                "white" -> {
                    binding.whitering.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
                    binding.whiteringcheck.visibility = View.VISIBLE
                }
                "lightgreen" -> {
                    binding.lightgreenring.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
                    binding.lightgreenringcheck.visibility = View.VISIBLE
                }
            }
        }
        binding.camera.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 500) }


        binding.moldbutton.setOnClickListener {
            binding.moldbutton.setTextColor(Color.parseColor("#2D2C2E"))
            binding.diebutton.setTextColor(Color.parseColor("#00918E"))
            binding.moldbutton.setBackgroundResource(R.drawable.buttons4)
            binding.diebutton.setBackgroundResource(R.drawable.buttons2)
            binding.molddiename.hint = "Mold Name"
            binding.molddiecode.hint = "Mold Code"
            val mold = "Mold"
            val die = "Die"
            val spanString = SpannableString(mold)
            spanString.setSpan(StyleSpan(Typeface.BOLD), 0, spanString.length, 0)
            val spanString2 = SpannableString(die)
            spanString2.setSpan(StyleSpan(Typeface.NORMAL), 0, spanString2.length, 0)
            binding.moldbutton.text = spanString
            binding.diebutton.text = spanString2
            binding.moldtext.text = "Choose Your Mold :"
            (activity as FragmentHolder).viewModel.moldOrDie.setValue("Mold")
        }
        binding.diebutton.setOnClickListener {
            binding.moldbutton.setTextColor(Color.parseColor("#00918E"))
            binding.diebutton.setTextColor(Color.parseColor("#2D2C2E"))
            binding.moldbutton.setBackgroundResource(R.drawable.buttons2)
            binding.diebutton.setBackgroundResource(R.drawable.buttons4)
            binding.molddiename.hint = "Die Name"
            binding.molddiecode.hint = "Die Code"
            val mold = "Mold"
            val die = "Die"
            val spanString = SpannableString(mold)
            spanString.setSpan(StyleSpan(Typeface.NORMAL), 0, spanString.length, 0)
            val spanString2 = SpannableString(die)
            spanString2.setSpan(StyleSpan(Typeface.BOLD), 0, spanString2.length, 0)
            binding.moldbutton.text = spanString
            binding.diebutton.text = spanString2
            binding.moldtext.text = "Choose Your Die :"
            (activity as FragmentHolder).viewModel.moldOrDie.setValue("Die")
        }
        binding.next.setOnClickListener {
            if (available) {
                if (!(activity as FragmentHolder).viewModel.moldOrDieName.value.isNullOrEmpty() && ! (activity as FragmentHolder).viewModel.moldOrDieCode.value.isNullOrEmpty() && !(activity as FragmentHolder).viewModel.color.value.isNullOrEmpty()) {
                    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        (activity as FragmentHolder).nav.navigate(R.id.action_mold_to_measure)
                    } else {
                        val extras = FragmentNavigatorExtras(binding.greenlinear to "greentrans")

                        if ((activity as FragmentHolder).viewModel.moldOrDieName.value!!.length < 10) {
                            if ((activity as FragmentHolder).viewModel.moldOrDieCode.value!!.length < 6) {
                                (activity as FragmentHolder).nav.navigate(
                                    R.id.action_mold_to_measure,
                                    null,
                                    null,
                                    extras
                                )
                            } else {
                                longToastError(" Mold code  must be less than  6 chars . ");
                            }
                        }else
                        {
                            longToastError(" Mold name must be less than  10 chars . ");
                        }

                    }
                } else {
                    longToastError("All fields is required")
                }
            } else {
                longToastError("No Internet Connection")
            }
        }
        binding.orangering.setOnClickListener {

            (activity as FragmentHolder).viewModel.color.value = "orange"
            invisble()
            binding.orangering.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
            binding.orangeringcheck.visibility = View.VISIBLE
        }
        binding.redring.setOnClickListener {
            (activity as FragmentHolder).viewModel.color.value = "red"
            invisble()
            binding.redring.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
            binding.redringcheck.visibility = View.VISIBLE
        }
        binding.bluering.setOnClickListener {
            (activity as FragmentHolder).viewModel.color.value = "blue"
            invisble()
            binding.bluering.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
            binding.blueringcheck.visibility = View.VISIBLE
        }
        binding.yellowring.setOnClickListener {
            (activity as FragmentHolder).viewModel.color.value = "yellow"
            invisble()
            binding.yellowring.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
            binding.yellowringcheck.visibility = View.VISIBLE
        }
        binding.greenring.setOnClickListener {
            (activity as FragmentHolder).viewModel.color.value = "green"
            invisble()
            binding.greenring.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
            binding.greenringcheck.visibility = View.VISIBLE
        }
        binding.grayring.setOnClickListener {
            (activity as FragmentHolder).viewModel.color.value = "gray"
            invisble()
            binding.grayring.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
            binding.grayringcheck.visibility = View.VISIBLE
        }
        binding.whitering.setOnClickListener {
            (activity as FragmentHolder).viewModel.color.value = "white"
            invisble()
            binding.whitering.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
            binding.whiteringcheck.visibility = View.VISIBLE
        }
        binding.lightgreenring.setOnClickListener {
            invisble()
            binding.lightgreenring.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
            binding.lightgreenringcheck.visibility = View.VISIBLE
            (activity as FragmentHolder).viewModel.color.value = "lightgreen"
        }
        return view
    }

private fun invisble()
{
    binding.lightgreenring.colorFilter = null
    binding.whitering.colorFilter = null
    binding.grayring.colorFilter = null
    binding.greenring.colorFilter = null
    binding.yellowring.colorFilter = null
    binding.bluering.colorFilter = null
    binding.redring.colorFilter = null
    binding.orangering.colorFilter = null
    binding.lightgreenringcheck.visibility = View.INVISIBLE
    binding.whiteringcheck.visibility = View.INVISIBLE
    binding.grayringcheck.visibility =  View.INVISIBLE
    binding.greenringcheck.visibility =  View.INVISIBLE
    binding.yellowringcheck.visibility =  View.INVISIBLE
    binding.blueringcheck.visibility =  View.INVISIBLE
    binding.redringcheck.visibility =  View.INVISIBLE
    binding.orangeringcheck.visibility =  View.INVISIBLE
}





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 500 && resultCode == Activity.RESULT_OK) {
            (activity as FragmentHolder).viewModel.resultUri.value = data!!.data.toString()
        }
    }
}