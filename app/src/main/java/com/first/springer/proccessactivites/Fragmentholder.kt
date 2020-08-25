package com.first.springer.proccessactivites
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.first.springer.BuildConfig
import com.first.springer.ModelResults
import com.first.springer.R
import com.first.springer.User
import com.first.springer.common.BaseActivity
import com.first.springer.common.MyApplication
import com.first.springer.common.MyApplication.Companion.available
import com.first.springer.databinding.ActivityFragmentholderBinding
import com.first.springer.login.Login
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class Fragmentholder : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {
    var modelResults: ModelResults? = null
   lateinit var mInterstitialAd: InterstitialAd
  lateinit var binding: ActivityFragmentholderBinding
     lateinit var nav : NavController
    var name: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fragmentholder)
        setContentView(binding.root)
        binding.navview!!.signout.setOnClickListener(this)
        binding.navview!!.profilelayout.setOnClickListener(this)
        binding.navview!!.resultlayout.setOnClickListener(this)
        binding.navview!!.invitelayout.setOnClickListener(this)
        binding.navview!!.helplayout.setOnClickListener(this)
        binding.navview!!.feedbacklayout.setOnClickListener(this)
        binding.navview!!.ratelayout.setOnClickListener(this)
        MobileAds.initialize(this@Fragmentholder)
        binding.toolbarTitle.text = "Home"
        mInterstitialAd = InterstitialAd(this@Fragmentholder)
        mInterstitialAd.adUnitId = "ca-app-pub-9755212852136016/7374695701"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        window.exitTransition = null
        window.enterTransition = null
        supportFragmentManager.beginTransaction().add(R.id.fragmentcontainer, Mold()).commit()
        name = Mold::class.java.name
        for (user1 in mAuth.currentUser!!.providerData) {
            if (user1.providerId == "facebook.com") {
                initial()
                if (mAuth.currentUser!!.photoUrl != null) {
                    glide("https://graph.facebook.com/" + com.facebook.Profile.getCurrentProfile().id + "/picture?type=large", binding.navview!!.personalImageView)
                    glide("https://graph.facebook.com/" + com.facebook.Profile.getCurrentProfile().id + "/picture?type=large", binding.personalimagetoolbar)
                }
                firebase(false)
            } else if (user1.providerId == "google.com") {
                initial()
                if (mAuth.currentUser!!.photoUrl != null) {
                    viewModel.EditUri.value = mAuth.currentUser!!.photoUrl.toString()
                    glide(viewModel.EditUri.value, binding.navview!!.personalImageView)
                    glide(viewModel.EditUri.value, binding.personalimagetoolbar)
                }
                firebase(false)
            } else if (user1.providerId != "google.com" && user1.providerId != "facebook.com") {
                binding.progressBar.visibility = View.VISIBLE
                window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                viewModel.EditEmail.value = mAuth.currentUser!!.email
                binding.navview!!.emailview.text = viewModel.EditEmail.value
                if (mAuth.currentUser!!.displayName != null) {
                    viewModel.EditName.value = mAuth.currentUser!!.displayName
                    binding.navview!!.nameview.text = viewModel.EditName.value
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
        viewModel.EditEmail.value = mAuth.currentUser!!.email
        binding.navview!!.emailview.text = viewModel.EditEmail.value
        viewModel.EditName.value = mAuth.currentUser!!.displayName
        binding.navview!!.nameview.text = viewModel.EditName.value
    }

    private fun firebase(userkind: Boolean) {
        FirebaseDatabase.getInstance().getReference("users").child(mAuth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(
                        User::class.java
                    )
                    if (user != null) {
                        binding.progressBar.visibility = View.INVISIBLE
                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        if (user.age!!.isNotEmpty()) {
                            viewModel.EditAge.value = user.age
                        }
                        if (user.gender!!.isNotEmpty()) {
                            viewModel.EditGender.value = user.gender
                        }
                        if (userkind) {
                            if (user.image!!.isNotEmpty()) {
                                viewModel.EditUri.value = user.image
                                glide(
                                    viewModel.EditUri.value,
                                    binding.navview!!.personalImageView
                                )
                                glide(viewModel.EditUri.value, binding.personalimagetoolbar)
                            }
                            if (mAuth.currentUser!!.displayName!!.isNotEmpty()) {
                                viewModel.EditName.value = mAuth.currentUser!!.displayName
                                binding.navview!!.nameview.text = viewModel.EditName.value
                            }
                        }
                    } else {
                        FirebaseDatabase.getInstance().getReference("users").child(
                            mAuth.currentUser!!.uid
                        ).setValue(User("", "", ""))
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }

    fun fragmenttrans(fragment: Fragment?) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, fragment!!).addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val currentFragment = fragmentManager.findFragmentById(R.id.fragmentcontainer)
            val name = currentFragment!!.javaClass.name
          when {
            Mold::class.java.name == name -> {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                fragmenttrans(Mold())
                binding.toolbarTitle.text = "Home"
                binding.drawer.closeDrawers()
            }
            Profile::class.java.name == name -> {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                fragmenttrans(Mold())
                this.name = Mold::class.java.name
                binding.toolbarTitle.text = "Home"
                binding.drawer.closeDrawers()
            }
            Editprofile::class.java.name == name -> {
                super.onBackPressed()
                this.name = Profile::class.java.name
                binding.toolbarTitle.text = "Account"
                binding.drawer.closeDrawers()
            }
            Allresults::class.java.name == name -> {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                fragmenttrans(Mold())
                this.name = Mold::class.java.name
                binding.toolbarTitle.text = "Home"
                binding.drawer.closeDrawers()
            }
            Resultshow::class.java.name == name -> {
                super.onBackPressed()
                this.name = Allresults::class.java.name
                binding.toolbarTitle.text = "All Results"
                binding.drawer.closeDrawers()
            }
            Help::class.java.name == name -> {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                fragmenttrans(Mold())
                this.name = Mold::class.java.name
                binding.toolbarTitle.text = "Home"
                binding.drawer.closeDrawers()
            }
            Measure::class.java.name == name -> {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                fragmenttrans(Mold())
            }
            Results::class.java.name == name -> {
                fragmentManager.beginTransaction()
                    .remove(currentFragment).commit()
                super.onBackPressed()
            }
            else -> {
                super.onBackPressed()
            }
        }

    }

    private fun navigation() {
        binding.navigationview.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, binding.drawer, binding.toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer)
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragmentcontainer)
            val name = fragment!!.javaClass.name
            when {
                Mold::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Mold()).commit() }
                Profile::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Profile()).commit()
                }
                Editprofile::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Editprofile()).commit()
                }
                Allresults::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Allresults()).commit()
                }
                Resultshow::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Resultshow()).commit()
                }
                Measure::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Measure()).commit()
                }
                Results::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Results()).commit()
                }
                Help::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Help()).commit()
                }
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragmentcontainer)
            val name = fragment!!.javaClass.name
            when {
                Mold::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Mold()).commit()
                }
                Profile::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Profile()).commit()
                }
                Editprofile::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Editprofile()).commit()
                }
                Allresults::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Allresults()).commit()
                }
                Resultshow::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Resultshow()).commit()
                }
                Measure::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Measure()).commit()
                }
                Results::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Results()).commit()
                }
                Help::class.java.name == name -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, Help()).commit()
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return false
    }



    override fun onClick(v: View) {
        when (v.id) {
            R.id.signout -> {
                mAuth.signOut()
                Intent(this@Fragmentholder, Login::class.java, true)
            }
            R.id.helplayout -> if (Help::class.java.name != name) {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                name = Help::class.java.name
                fragmenttrans(Help())
                binding.drawer.closeDrawers()
            } else {
                binding.drawer.closeDrawers()
            }
            R.id.profilelayout -> if (available) {
                if (Profile::class.java.name != name) {
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    name = Profile::class.java.name
                    fragmenttrans(Profile())
                    binding.drawer.closeDrawers()
                } else {
                    binding.drawer.closeDrawers()
                }
            } else {
                Longtoasterror("No internet Connection")
            }
            R.id.invitelayout -> try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                var shareMessage = "\nLet me recommend you this application\n\n"
                shareMessage = """${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}""".trimIndent()
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (ignored: Exception) {
            }
            R.id.ratelayout -> if (available) {
                val appPackageName = packageName
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                } catch (anfe: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                }
            } else {
                Longtoasterror("No internet Connection")
            }
            R.id.feedbacklayout -> if (!available) {
                Longtoasterror("No internet Connection")
            } else {
                val appPackageName = packageName
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                } catch (anfe: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                }
            }
            R.id.resultlayout -> {
                if (available) {
                    if (Allresults::class.java.name != name) {
                        name = Allresults::class.java.name
                        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                        fragmenttrans(Allresults())
                        binding.drawer.closeDrawers()
                    } else {
                        binding.drawer.closeDrawers()
                    }
                    if (mInterstitialAd.isLoaded) {
                        mInterstitialAd.show()
                    } else {
                            mInterstitialAd.adListener = object : AdListener() {
                                override fun onAdLoaded() {}
                                override fun onAdFailedToLoad(errorCode: Int) {}
                                override fun onAdOpened() {
                                    // Code to be executed when the ad is displayed.
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
                    Longtoasterror("No internet Connection")
                }
                binding.drawer.closeDrawers()
            }
        }
    }
}