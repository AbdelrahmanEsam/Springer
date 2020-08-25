package com.first.springer.proccessactivites
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.first.springer.R
import com.first.springer.common.Basefragment
import com.first.springer.common.MyApplication.Companion.available
import com.first.springer.databinding.FragmentMoldBinding
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Mold : Basefragment() {
    private lateinit var binding: FragmentMoldBinding
    private var buttonlist: MutableList<AppCompatImageView> = ArrayList()
    private var checklist: MutableList<AppCompatImageView> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mold, container, false)
        val view = binding.root
        init()
        (activity as Fragmentholder).viewModel = ViewModelProvider(requireActivity()).get(ProccessViewModel::class.java)
        binding.viewModel = (activity as Fragmentholder).viewModel
        (activity as Fragmentholder).viewModel.MoldOrDie.value ="Mold"
        if ((activity as Fragmentholder).viewModel.Color.value != null) {
            when ((activity as Fragmentholder).viewModel.Color.value) {
                "orange" -> {
                    ButtonSelection(0)
                    checkSelection(0)
                }
                "red" -> {
                    ButtonSelection(1)
                    checkSelection(1)
                }
                "blue" -> {
                    ButtonSelection(2)
                    checkSelection(2)
                }
                "yellow" -> {
                    ButtonSelection(3)
                    checkSelection(3)
                }
                "green" -> {
                    ButtonSelection(4)
                    checkSelection(4)
                }
                "gray" -> {
                    ButtonSelection(5)
                    checkSelection(5)
                }
                "white" -> {
                    ButtonSelection(6)
                    checkSelection(6)
                }
                "lightgreen" -> {
                    ButtonSelection(7)
                    checkSelection(7)
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
            (activity as Fragmentholder).viewModel.MoldOrDie.setValue("Mold")
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
            (activity as Fragmentholder).viewModel.MoldOrDie.setValue("Die")
        }
        binding.next.setOnClickListener {
            if (available) {
                if ((activity as Fragmentholder).viewModel.MoldOrDieName.value != null && (activity as Fragmentholder).viewModel.MoldOrDieCode.value != null && (activity as Fragmentholder).viewModel.Color.value != null) {
                    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        val ft = requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Measure()).addToBackStack("measure")
                        ft.commit()
                    } else {
                        val fragment: Fragment = Measure()
                        val ft = activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, fragment).addToBackStack(null).addSharedElement(binding.greenlinear, "greentrans")
                        fragment.sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                        ft.commit()

                    }
                } else {
                    Longtoasterror("All fields is required")
                }
            } else {
                Longtoasterror("No Internet Connection")
            }
        }
        binding.orangering.setOnClickListener {
            (activity as Fragmentholder).viewModel.Color.value = "orange"
            ButtonSelection(0)
            checkSelection(0)
        }
        binding.redring.setOnClickListener {
            (activity as Fragmentholder).viewModel.Color.value = "red"
            ButtonSelection(1)
            checkSelection(1)
        }
        binding.bluering.setOnClickListener {
            (activity as Fragmentholder).viewModel.Color.value = "blue"
            ButtonSelection(2)
            checkSelection(2)
        }
        binding.yellowring.setOnClickListener {
            (activity as Fragmentholder).viewModel.Color.value = "yellow"
            ButtonSelection(3)
            checkSelection(3)
        }
        binding.greenring.setOnClickListener {
            (activity as Fragmentholder).viewModel.Color.value = "green"
            ButtonSelection(4)
            checkSelection(4)
        }
        binding.grayring.setOnClickListener {
            (activity as Fragmentholder).viewModel.Color.value = "gray"
            ButtonSelection(5)
            checkSelection(5)
        }
        binding.whitering.setOnClickListener {
            (activity as Fragmentholder).viewModel.Color.value = "white"
            ButtonSelection(6)
            checkSelection(6)
        }
        binding.lightgreenring.setOnClickListener {
            ButtonSelection(7)
            checkSelection(7)
            (activity as Fragmentholder).viewModel.Color.value = "lightgreen"
        }
        return view
    }


    private fun ButtonSelection(buttonid: Int) {
        val button = buttonlist[buttonid]
        button.setColorFilter(ResourcesCompat.getColor(resources, R.color.TINT, null))
        for (i in buttonlist.indices) {
            if (i != buttonid) {
                val otherbutton = buttonlist[i]
                otherbutton.colorFilter = null
            }
        }
    }

    private fun checkSelection(buttonid: Int) {
        val button = checklist[buttonid]
        button.visibility = View.VISIBLE
        for (i in checklist.indices) {
            if (i != buttonid) {
                val otherbutton = checklist[i]
                otherbutton.visibility = View.INVISIBLE
            }
        }
    }

    private fun init() {
        buttonlist.add(binding.orangering)
        buttonlist.add(binding.redring)
        buttonlist.add(binding.bluering)
        buttonlist.add(binding.yellowring)
        buttonlist.add(binding.greenring)
        buttonlist.add(binding.grayring)
        buttonlist.add(binding.whitering)
        buttonlist.add(binding.lightgreenring)
        checklist.add(binding.orangeringcheck)
        checklist.add(binding.redringcheck)
        checklist.add(binding.blueringcheck)
        checklist.add(binding.yellowringcheck)
        checklist.add(binding.greenringcheck)
        checklist.add(binding.grayringcheck)
        checklist.add(binding.whiteringcheck)
        checklist.add(binding.lightgreenringcheck)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 500 && resultCode == Activity.RESULT_OK) {
            (activity as Fragmentholder).viewModel.ResultUri.value = data!!.data.toString()
        }
    }
}