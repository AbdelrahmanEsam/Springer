package com.first.springer.proccessactivites
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.facebook.Profile
import com.first.springer.R
import com.first.springer.common.Basefragment
import com.first.springer.common.MyApplication.Companion.available
import com.first.springer.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth


class Profile : Basefragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        val view = binding.root
        binding.viewModel = (activity as Fragmentholder).viewModel
        (activity as Fragmentholder).binding.toolbarTitle.text = "Account"
        if ((activity as Fragmentholder).viewModel!!.EditGender.value != null) {
            if ((activity as Fragmentholder).viewModel!!.EditGender.value == "male") {
                binding.maleselect.setImageResource(R.drawable.lightring)
                binding.femaleselect.setImageResource(R.drawable.bordercircle)
            } else if ((activity as Fragmentholder).viewModel!!.EditGender.value == "female") {
                binding.maleselect.setImageResource(R.drawable.bordercircle)
                binding.femaleselect.setImageResource(R.drawable.lightring)
            }
        }
        for (user in FirebaseAuth.getInstance().currentUser!!.providerData) {
            if (user.providerId == "facebook.com") {
                glide("https://graph.facebook.com/" + Profile.getCurrentProfile().id + "/picture?type=large",binding.selectimage)
            } else if (user.providerId == "google.com") {
                glide(mAuth!!.currentUser!!.photoUrl.toString(),binding.selectimage)
            } else if (user.providerId != "google.com" && user.providerId != "facebook.com") {
                if (!(activity as Fragmentholder).viewModel!!.EditUri.value.isNullOrEmpty()) {
                    glide((activity as Fragmentholder).viewModel!!.EditUri.value,binding.selectimage)
                }
            }
        }
        binding.editicon.setOnClickListener {
            if (available) {
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.transitionin, R.anim.transitionout).addToBackStack(null)
                transaction.replace(R.id.fragmentcontainer, Editprofile())
                transaction.commit()
            } else {
                Longtoast("No internet access")
            }
        }
        return view
    }
}