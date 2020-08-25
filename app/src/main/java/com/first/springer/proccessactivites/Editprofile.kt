package com.first.springer.proccessactivites

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.FacebookSdk
import com.facebook.Profile
import com.first.springer.R
import com.first.springer.User
import com.first.springer.common.Basefragment
import com.first.springer.databinding.FragmentEditprofileBinding
import com.first.springer.login.Login
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.regex.Pattern

/**
 * A simple [Fragment] subclass.
 */
class Editprofile : Basefragment() {
    private var user: UserInfo? = null
    private lateinit var binding: FragmentEditprofileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_editprofile, container, false
        )
        val view = binding.getRoot()
        binding.youremailedit.isEnabled = false
        binding.viewModel = (activity as Fragmentholder).viewModel
        (activity as Fragmentholder?)!!.binding.toolbarTitle!!.text = "Edit profile"
        binding.selectimage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            requireActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"), 200)
        }
        if ((activity as Fragmentholder?)!!.viewModel!!.EditGender.value != null) {
            if ((activity as Fragmentholder?)!!.viewModel!!.EditGender.value == "male") {
                binding.maleselect.setImageResource(R.drawable.lightring)
                binding.femaleselect.setImageResource(R.drawable.bordercircle)
            } else if ((activity as Fragmentholder?)!!.viewModel!!.EditGender.value == "female") {
                binding.maleselect.setImageResource(R.drawable.bordercircle)
                binding.femaleselect.setImageResource(R.drawable.lightring)
            }
        }
        binding.maleselect.setOnClickListener {
            binding.maleselect.setImageResource(R.drawable.lightring)
            binding.femaleselect.setImageResource(R.drawable.bordercircle)
            (activity as Fragmentholder?)!!.viewModel!!.EditGender.setValue("male")
        }
        binding.femaleselect.setOnClickListener {
            binding.maleselect.setImageResource(R.drawable.bordercircle)
            binding.femaleselect.setImageResource(R.drawable.lightring)
            (activity as Fragmentholder?)!!.viewModel!!.EditGender.setValue("female")
        }
        for (user in FirebaseAuth.getInstance().currentUser!!.providerData) {
            this.user = user
            if (user.providerId == "facebook.com") {
                Glide.with(this)
                    .load("https://graph.facebook.com/" + Profile.getCurrentProfile().id + "/picture?type=large")
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.selectimage)
                binding.yournameedit.isEnabled = false
                binding.yourpassdit.isEnabled = false
                binding.selectimage.isClickable = false
            } else if (user.providerId == "google.com") {
             glide(mAuth!!.currentUser!!.photoUrl.toString(),binding.selectimage)
                binding.yournameedit.isEnabled = false
                binding.yourpassdit.isEnabled = false
                binding.selectimage.isClickable = false
            } else {
                if (!TextUtils.isEmpty((activity as Fragmentholder?)!!.viewModel!!.EditUri.value)) {
                    glide((activity as Fragmentholder)!!.viewModel!!.EditUri.value,binding.selectimage)
                } else {
                    Glide.with(this)
                        .load(R.drawable.noimage)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(binding.selectimage)
                }
            }
        }
        binding.save.setOnClickListener {
            if (user!!.providerId == "facebook.com") {
                val user = User()
                user.image = ""
                if (binding.youragedit.editText!!.text.toString().trim().isNotEmpty()) {
                    user.age =
                        (Objects.requireNonNull(activity) as Fragmentholder).viewModel!!.EditAge.value
                } else {
                    user.age = ""
                }
                if ((activity as Fragmentholder).viewModel!!.EditGender.value!!.trim().isNotEmpty())
                {
                    user.gender = (activity as Fragmentholder?)!!.viewModel!!.EditGender.value
                } else {
                    user.gender = ""
                }
                FirebaseDatabase.getInstance().getReference("users")
                    .child(mAuth!!.currentUser!!.uid).setValue(user)
                    .addOnCompleteListener { task: Task<Void?>? ->
                        Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show()
                        activity!!.onBackPressed()
                    }
            } else if (user!!.providerId == "google.com") {
                val user = User()
                user.image = ""
                if (binding.youragedit.editText!!.text.toString().trim().isNotEmpty())
                {
                    user.age =
                        (Objects.requireNonNull(activity) as Fragmentholder).viewModel!!.EditAge.value
                } else {
                    user.age = ""
                }
                if ((activity as Fragmentholder).viewModel!!.EditGender.value!!.trim().isNotEmpty())
                {
                    user.gender = (activity as Fragmentholder?)!!.viewModel!!.EditGender.value
                } else {
                    user.gender = ""
                }
                FirebaseDatabase.getInstance().getReference("users")
                    .child(mAuth!!.currentUser!!.uid).setValue(user)
                    .addOnCompleteListener {
                        Longtoast("saved")
                        activity!!.onBackPressed()
                    }
            } else if (user!!.providerId != "google.com" && user!!.providerId != "facebook.com") {
                var bitmap: Bitmap? = null
                try {
                    bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(context!!.contentResolver, Uri.parse((activity as Fragmentholder).viewModel!!.EditUri.value))
                    } else {
                        val source = ImageDecoder.createSource(context!!.contentResolver, Uri.parse((activity as Fragmentholder).viewModel!!.EditUri.value))
                        ImageDecoder.decodeBitmap(source)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                if (bitmap != null) {
                    val externalStorageDirectory = FacebookSdk.getApplicationContext().getExternalFilesDir(null)
                    deleteDirectory(externalStorageDirectory!!)
                    val l = System.currentTimeMillis()
                    save(l, bitmap)
                    glide((activity as Fragmentholder?)!!.viewModel!!.EditUri.value,binding.selectimage)
                }
                if (binding.yourpassdit.editText!!.text.toString().trim().isNotEmpty()) {
                    val regex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
                    val pattern = Pattern.compile(regex)
                    if (!pattern.matcher(
                            binding.yourpassdit.editText!!.text.toString().trim()).matches()
                    ) {
                        Longtoast("Password is too weak")
                    } else { mAuth!!.currentUser!!.updatePassword(
                                binding.yourpassdit.editText!!.text.toString().trim())
                            .addOnCompleteListener { task: Task<Void?> ->
                                if (task.isSuccessful) {
                                    mAuth!!.signOut()
                                    val intent = Intent(activity, Login::class.java)
                                    startActivity(intent)
                                    activity!!.finish()
                                } else {
                                    Longtoast("Failed to update password!")
                                }
                            }
                    }
                }
                val user = User()
                if (binding.youragedit.editText!!.text.toString().trim().isNotEmpty()) {
                    user.age =
                        (Objects.requireNonNull(activity) as Fragmentholder).viewModel!!.EditAge.value
                } else {
                    user.age = ""
                }
                if ((activity as Fragmentholder).viewModel!!.EditGender.value!!.trim().isNotEmpty()) {
                    user.gender = (activity as Fragmentholder?)!!.viewModel!!.EditGender.value
                } else {
                    user.gender = ""
                }
                if ((activity as Fragmentholder?)!!.viewModel!!.EditUri.value!!.trim().isNotEmpty()
                ) {
                    user.image = (activity as Fragmentholder?)!!.viewModel!!.EditUri.value
                } else {
                    user.image = ""
                }
                if (binding.yournameedit.editText!!.text.toString().trim().isNotEmpty()) {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName((activity as Fragmentholder?)!!.viewModel!!.EditName.value)
                        .build()
                    mAuth!!.currentUser!!.updateProfile(profileUpdates)
                }
                FirebaseDatabase.getInstance().getReference("users")
                    .child(mAuth!!.currentUser!!.uid).setValue(user)
                activity!!.onBackPressed()
            }
        }
        return view
    }

    fun setImage(uri: Uri?) { glide(uri!!.toString(),binding.selectimage) }

    private fun save(i: Long, bitmapImage: Bitmap) {
        val externalStorageDirectory = FacebookSdk.getApplicationContext().getExternalFilesDir(null)
        val file = File(externalStorageDirectory, "$i.jpg")
        try {
            val out = FileOutputStream(file, false)
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        (activity as Fragmentholder).viewModel!!.EditUri.value = file.absolutePath
    }

    private fun deleteDirectory(file: File) {
        if (file.exists()) {
            if (file.isDirectory) {
                val files = file.listFiles()
                for (i in files!!.indices) {
                    if (files[i].isDirectory) {
                        deleteDirectory(files[i])
                    } else {
                        files[i].delete()
                    }
                }
            }
            file.delete()
        }
    }
}