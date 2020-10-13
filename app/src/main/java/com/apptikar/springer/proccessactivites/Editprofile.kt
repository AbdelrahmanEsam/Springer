package com.apptikar.springer.proccessactivites

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.FacebookSdk
import com.facebook.Profile
import com.apptikar.springer.R
import com.apptikar.springer.User
import com.apptikar.springer.common.BaseFragment
import com.apptikar.springer.common.CustomDialogClass
import com.apptikar.springer.common.MyApplication.Companion.mAuth
import com.apptikar.springer.databinding.FragmentEditprofileBinding
import com.apptikar.springer.login.Login
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_editprofile.*
import java.io.File
import java.io.FileOutputStream
import java.util.regex.Pattern


class Editprofile : BaseFragment() {
    private var user: UserInfo? = null
    private lateinit var binding: FragmentEditprofileBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_editprofile, container, false)
        val view = binding.root
        binding.youremailedit.isEnabled = false
        binding.viewModel = (activity as FragmentHolder).viewModel
        (activity as FragmentHolder).binding.toolbarTitle.text = "Edit"
        binding.selectimage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            requireActivity().startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                200
            )
        }


        if ((activity as FragmentHolder?)!!.viewModel.editGender.value != null) {
            if ((activity as FragmentHolder).viewModel.editGender.value == "male") {
                binding.maleselect.setImageResource(R.drawable.lightring)
                binding.femaleselect.setImageResource(R.drawable.bordercircle)
            } else if ((activity as FragmentHolder).viewModel.editGender.value == "female") {
                binding.maleselect.setImageResource(R.drawable.bordercircle)
                binding.femaleselect.setImageResource(R.drawable.lightring)
            }
        }
        binding.maleselect.setOnClickListener {
            binding.maleselect.setImageResource(R.drawable.lightring)
            binding.femaleselect.setImageResource(R.drawable.bordercircle)
            (activity as FragmentHolder?)!!.viewModel.editGender.setValue("male")
        }
        binding.femaleselect.setOnClickListener {
            binding.maleselect.setImageResource(R.drawable.bordercircle)
            binding.femaleselect.setImageResource(R.drawable.lightring)
            (activity as FragmentHolder).viewModel.editGender.setValue("female")
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
                glide(mAuth!!.currentUser!!.photoUrl.toString(), binding.selectimage)
                binding.yournameedit.isEnabled = false
                binding.yourpassdit.isEnabled = false
                binding.selectimage.isClickable = false
            } else {
                if (!TextUtils.isEmpty((activity as FragmentHolder?)!!.viewModel.editUri.value)) {
                    glide((activity as FragmentHolder).viewModel.editUri.value, binding.selectimage)
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
                        (activity as FragmentHolder).viewModel.editAge.value
                } else {
                    user.age = ""
                }
                if ((activity as FragmentHolder).viewModel.editGender.value!!.trim().isNotEmpty()) {
                    user.gender = (activity as FragmentHolder?)!!.viewModel.editGender.value
                } else {
                    user.gender = ""
                }
                FirebaseDatabase.getInstance().getReference("users")
                    .child(mAuth!!.currentUser!!.uid).setValue(user)
                    .addOnCompleteListener { task: Task<Void?>? ->
                        Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show()
                        requireActivity().onBackPressed()
                    }
            } else if (user!!.providerId == "google.com") {
                val user = User()
                user.image = ""
                if (binding.youragedit.editText!!.text.toString().trim().isNotEmpty()) {
                    user.age =
                        (activity as FragmentHolder).viewModel.editAge.value
                } else {
                    user.age = ""
                }
                if ((activity as FragmentHolder).viewModel.editGender.value!!.trim().isNotEmpty()) {
                    user.gender = (activity as FragmentHolder?)!!.viewModel.editGender.value
                } else {
                    user.gender = ""
                }
                FirebaseDatabase.getInstance().getReference("users")
                    .child(mAuth!!.currentUser!!.uid).setValue(user)
                    .addOnCompleteListener {
                        longToast("saved")
                        requireActivity().onBackPressed()
                    }
            } else if (user!!.providerId != "google.com" && user!!.providerId != "facebook.com") {
                var bitmap: Bitmap? = null
                try {
                    bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, Uri.parse((activity as FragmentHolder).viewModel.editUri.value))
                    } else {
                        val source = ImageDecoder.createSource(requireContext().contentResolver, Uri.parse((activity as FragmentHolder).viewModel.editUri.value))
                        ImageDecoder.decodeBitmap(source)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                if (bitmap != null) {
                    val externalStorageDirectory = requireActivity().applicationContext.getExternalFilesDir(null)
                    deleteDirectory(externalStorageDirectory!!)
                    val l = System.currentTimeMillis()
                    save(l, bitmap)
                    glide(
                        (activity as FragmentHolder).viewModel.editUri.value, binding.selectimage)
                }

                val user = User()
                if (binding.youragedit.editText!!.text.toString().trim().isNotEmpty()) {
                    user.age = (activity as FragmentHolder).viewModel.editAge.value
                } else {
                    user.age = ""
                }
                if (!(activity as FragmentHolder).viewModel.editGender.value?.trim().isNullOrEmpty()
                ) {
                    user.gender = (activity as FragmentHolder).viewModel.editGender.value
                } else {
                    user.gender = ""
                }
                if (!(activity as FragmentHolder).viewModel.editUri.value?.trim().isNullOrEmpty()
                ) {
                    user.image = (activity as FragmentHolder).viewModel.editUri.value
                } else {
                    user.image = ""
                }
                if (binding.yournameedit.editText!!.text.toString().trim().isNotEmpty()) {
                    val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName((activity as FragmentHolder).viewModel.editName.value).build()
                    mAuth!!.currentUser!!.updateProfile(profileUpdates)
                }

                    if (binding.yourpassdit.editText!!.text.toString().trim().isNotEmpty()) {
                        val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$"
                        val pattern = Pattern.compile(regex)
                        if (!pattern.matcher(binding.yourpassdit.editText!!.text.toString().trim()).matches()
                        ) {
                            longToast("Password is too weak")
                        } else {
                           showDialog(user)
                        }
                    } else {
                        FirebaseDatabase.getInstance().getReference("users").child(mAuth!!.currentUser!!.uid).setValue(user).addOnCompleteListener {
                            (requireActivity() as FragmentHolder).binding.navview.nameview.text = (activity as FragmentHolder).viewModel.editName.value
                            requireActivity().onBackPressed()
                        }


                    }



            }

        }
        return view
    }

   private fun showDialog(user: User) {

        lateinit var credential: AuthCredential
        val customDialogClass =  CustomDialogClass(requireContext())
        customDialogClass.show()
        val lp = WindowManager.LayoutParams()
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        customDialogClass.window?.attributes = lp
        customDialogClass.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        customDialogClass.window?.setDimAmount(0.5f)
        val enterPassword = customDialogClass.findViewById<AppCompatButton>(R.id.enterpassword)
        val closeDialog = customDialogClass.findViewById<ImageView>(R.id.closeimageedit)
        val passwordEdit = customDialogClass.findViewById<TextInputLayout>(R.id.passwordedit)
        enterPassword.setOnClickListener {
            if (!passwordEdit.editText?.text.isNullOrEmpty()) {
                credential = EmailAuthProvider.getCredential(mAuth?.currentUser?.email!!, passwordEdit.editText?.text.toString())
                mAuth?.currentUser?.reauthenticate(credential)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        mAuth?.currentUser!!.updatePassword(yourpassdit.editText!!.text.toString().trim()).addOnCompleteListener { task: Task<Void?> ->
                            if (task.isSuccessful) {
                                FirebaseDatabase.getInstance().getReference("users").child(mAuth?.currentUser!!.uid).setValue(user).addOnCompleteListener {
                                    (activity as FragmentHolder).binding.navview.nameview.text = (activity as FragmentHolder).viewModel.editName.value
                                    customDialogClass.dismiss()
                                    mAuth?.signOut()
                                    val intent =Intent(requireContext(), Login::class.java)
                                    startActivity(intent)
                                    (activity as FragmentHolder).finish()
                                }

                            } else {
                                longToast("Failed to update password! " + task.exception?.message)

                            }
                        }
                    } else {
                        longToastError(it.exception?.message.toString())
                    }
                }

            }
        }
        closeDialog.setOnClickListener {
            customDialogClass.dismiss()
        }

    }

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
        (activity as FragmentHolder).viewModel.editUri.value = file.absolutePath
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