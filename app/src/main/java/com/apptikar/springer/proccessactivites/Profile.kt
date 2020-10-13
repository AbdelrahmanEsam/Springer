package com.apptikar.springer.proccessactivites
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.facebook.Profile
import com.apptikar.springer.R
import com.apptikar.springer.common.BaseFragment
import com.apptikar.springer.common.MyApplication.Companion.available
import com.apptikar.springer.common.MyApplication.Companion.mAuth
import com.apptikar.springer.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth


class Profile : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        val view = binding.root
        binding.viewModel = (activity as FragmentHolder).viewModel
        (activity as FragmentHolder).binding.toolbarTitle.text = "Account"
        if ((activity as FragmentHolder).viewModel.editGender.value != null) {
            if ((activity as FragmentHolder).viewModel.editGender.value == "male") {
                binding.maleselect.setImageResource(R.drawable.lightring)
                binding.femaleselect.setImageResource(R.drawable.bordercircle)
            } else if ((activity as FragmentHolder).viewModel.editGender.value == "female") {
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
                if (!(activity as FragmentHolder).viewModel.editUri.value.isNullOrEmpty()) {
                    glide((activity as FragmentHolder).viewModel.editUri.value,binding.selectimage)
                }
            }
        }
        binding.editicon.setOnClickListener {
            if (available) {
                (activity as FragmentHolder).fragmentTrans(R.id.action_profile_to_editprofile)
            } else {
                longToast("No internet access")
            }
        }
        return view
    }
}