package com.apptikar.springer.proccessactivites
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.apptikar.springer.BuildConfig
import com.apptikar.springer.R
import com.apptikar.springer.User
import com.apptikar.springer.common.BaseActivity
import com.apptikar.springer.common.MyApplication.Companion.available
import com.apptikar.springer.common.MyApplication.Companion.mAuth
import com.apptikar.springer.databinding.ActivityFragmentholderBinding
import com.apptikar.springer.login.Login
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint



class FragmentHolder : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {
   lateinit var mInterstitialAd: InterstitialAd
  lateinit var binding: ActivityFragmentholderBinding
     lateinit var nav : NavController
    var name: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fragmentholder)
        setContentView(binding.root)
        nav = Navigation.findNavController(this, R.id.navhostfragmentcontainer)
        binding.navview.signout.setOnClickListener(this)
        binding.navview.profilelayout.setOnClickListener(this)
        binding.navview.resultlayout.setOnClickListener(this)
        binding.navview.invitelayout.setOnClickListener(this)
        binding.navview.helplayout.setOnClickListener(this)
        binding.navview.feedbacklayout.setOnClickListener(this)
        binding.navview.ratelayout.setOnClickListener(this)
        MobileAds.initialize(this)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-9755212852136016/7374695701"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        binding.toolbarTitle.text = "Home"
        window.exitTransition = null
        window.enterTransition = null
        name = Mold::class.java.name
        for (user1 in mAuth!!.currentUser!!.providerData) {
            if (user1.providerId == "facebook.com") {
                initial()
                if (mAuth!!.currentUser!!.photoUrl != null) {
                    glide(
                        "https://graph.facebook.com/" + com.facebook.Profile.getCurrentProfile().id + "/picture?type=large",
                        binding.navview.personalImageView
                    )
                    glide(
                        "https://graph.facebook.com/" + com.facebook.Profile.getCurrentProfile().id + "/picture?type=large",
                        binding.personalimagetoolbar
                    )
                }
                firebase(false)
            } else if (user1.providerId == "google.com") {
                initial()
                if (mAuth!!.currentUser!!.photoUrl != null) {
                    viewModel.editUri.value = mAuth!!.currentUser!!.photoUrl.toString()
                    glide(viewModel.editUri.value, binding.navview.personalImageView)
                    glide(viewModel.editUri.value, binding.personalimagetoolbar)
                }
                firebase(false)
            } else if (user1.providerId != "google.com" && user1.providerId != "facebook.com") {
                binding.progressBar.visibility = View.VISIBLE
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                viewModel.editEmail.value = mAuth!!.currentUser!!.email
                binding.navview.emailview.text = viewModel.editEmail.value
                if (mAuth!!.currentUser!!.displayName != null) {
                    viewModel.editName.value = mAuth!!.currentUser!!.displayName
                    binding.navview.nameview.text = viewModel.editName.value
                }
                firebase(true)
            }
        }
        navigation()
    }

    private fun initial() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        binding.progressBar.visibility = View.VISIBLE
        viewModel.editEmail.value = mAuth!!.currentUser!!.email
        binding.navview.emailview.text = viewModel.editEmail.value
        viewModel.editName.value = mAuth!!.currentUser!!.displayName
        binding.navview.nameview.text = viewModel.editName.value
    }

    private fun firebase(userkind: Boolean) {
        FirebaseDatabase.getInstance().getReference("users").child(mAuth!!.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    if (user != null) {
                        binding.progressBar.visibility = View.INVISIBLE
                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        if (!user.age!!.isNullOrEmpty()) {
                            viewModel.editAge.value = user.age
                        }
                        if (!user.gender!!.isNullOrEmpty()) {
                            viewModel.editGender.value = user.gender
                        }
                        if (userkind) {
                            if (!user.image!!.isNullOrEmpty()) {
                                viewModel.editUri.value = user.image
                                glide(viewModel.editUri.value, binding.navview.personalImageView)
                                glide(viewModel.editUri.value, binding.personalimagetoolbar)
                            }
                            if (!mAuth!!.currentUser!!.displayName.isNullOrEmpty()) {
                                viewModel.editName.value = mAuth!!.currentUser!!.displayName
                                binding.navview.nameview.text = viewModel.editName.value
                            }
                        }
                    } else {
                        FirebaseDatabase.getInstance().getReference("users")
                            .child(mAuth!!.currentUser!!.uid).setValue(
                            User(
                                "",
                                "",
                                ""
                            )
                        )
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }

    fun fragmentTrans(fragment: Int) {
        nav.navigate(fragment)
    }






    private fun navigation() {
        binding.navigationview.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            binding.toolbar,
            R.string.open_navigation_drawer,
            R.string.close_navigation_drawer
        )
        toggle.drawerArrowDrawable.color = getColor(R.color.white)
        binding.drawer.addDrawerListener(toggle)
        binding.drawer.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {
                if (currentFocus != null) {
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                }

            }
        })



        toggle.syncState()
    }





    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        when (nav.currentDestination?.id) {
            R.id.mold -> {
                binding.toolbarTitle.text = "Home"
            }
            R.id.measure -> {
                binding.toolbarTitle.text = "Home"
            }
            R.id.results -> {
                binding.toolbarTitle.text = "Result"
            }
            R.id.profile -> {
                binding.toolbarTitle.text = "Account"
            }

            R.id.editprofile -> {
                binding.toolbarTitle.text = "Edit"
            }

            R.id.allresults -> {
                binding.toolbarTitle.text = "All Results"
            }

            R.id.resultshow -> {
                binding.toolbarTitle.text = viewModel.modelResults.value!!.moldorDieName
            }
            R.id.help -> {
                binding.toolbarTitle.text = "Help"
            }




        }

        binding.drawer.closeDrawers()
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.signout -> {
                mAuth!!.signOut()
                intent(this, Login::class.java, true)
            }
            R.id.helplayout -> {

                fragmentTrans(R.id.action_global_to_help)
                binding.drawer.closeDrawers()
            }


            R.id.profilelayout -> if (available) {
                fragmentTrans(R.id.action_global_to_profile)
                binding.drawer.closeDrawers()
            } else {
                longToastError("No internet Connection")
            }
            R.id.invitelayout -> try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                var shareMessage = "\nLet me recommend you this application\n\n"
                shareMessage =
                    """${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}""".trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (ignored: Exception) {
            }
            R.id.ratelayout -> if (available) {
                val appPackageName = packageName
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$appPackageName")
                        )
                    )
                } catch (anfe: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                        )
                    )
                }
            } else {
                longToastError("No internet Connection")
            }
            R.id.feedbacklayout -> if (available) {
                val appPackageName = packageName
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$appPackageName")
                        )
                    )
                } catch (anfe: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                        )
                    )
                }
            } else {
                longToastError("No internet Connection")

            }
            R.id.resultlayout -> {
                if (available) {
                    fragmentTrans(R.id.action_global_to_allresults)
                    if (mInterstitialAd.isLoaded) {
                        mInterstitialAd.show()
                    } else {
                        mInterstitialAd.adListener = object : AdListener() {
                            override fun onAdLoaded() {}
                            override fun onAdFailedToLoad(errorCode: Int) {}
                            override fun onAdOpened() {
                            }

                            override fun onAdClicked() {
                                // Code to be executed when the user clicks on an ad.
                            }

                            override fun onAdLeftApplication() {
                                // Code to be executed when the user has left the app.
                            }

                            override fun onAdClosed() {}
                        }

                    }
                } else {
                    longToastError("No internet Connection")
                }
                binding.drawer.closeDrawers()
            }
        }
    }
}