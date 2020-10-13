package com.apptikar.springer.tutorial

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class Mypageradapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {
    var fragments = arrayOf(Tutorial1(), Tutorial2(), Tutorial3())
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}