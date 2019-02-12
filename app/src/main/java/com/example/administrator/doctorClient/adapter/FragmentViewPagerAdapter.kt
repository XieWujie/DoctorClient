package com.example.administrator.doctorClient.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentViewPagerAdapter(fm:FragmentManager,val list:List<Fragment>):FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }


}