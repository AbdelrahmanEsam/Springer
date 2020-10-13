package com.apptikar.springer.tutorial

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.apptikar.springer.R
import com.apptikar.springer.common.BaseActivity
import com.apptikar.springer.databinding.ActivityTutorialBinding
import com.apptikar.springer.login.Loginandsign

class Tutorial : BaseActivity() {
    private  lateinit  var mypageradapter: Mypageradapter
    private lateinit var binding: ActivityTutorialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tutorial)
        pager()
        binding.tutorialnext.setOnClickListener {
            if (binding.viewPager.currentItem < 2) {
                binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1, true)
            } else {
                intent(this, Loginandsign::class.java, true)
            }
        }
        binding.viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position == 2) {
                    binding.tutorialnext.visibility = View.INVISIBLE
                    binding.tutorialskip.visibility = View.INVISIBLE
                } else {
                    binding.tutorialnext.visibility = View.VISIBLE
                    binding.tutorialskip.visibility = View.VISIBLE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        binding.tutorialskip.setOnClickListener { intent(this, Loginandsign::class.java, true) }
    }

    fun pager() {
        mypageradapter = Mypageradapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        binding.viewPager.adapter = mypageradapter
        binding.indicator.setViewPager(binding.viewPager)
    }
}