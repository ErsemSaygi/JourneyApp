package com.obilet.task.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.obilet.task.view.FragmentBusIndex
import com.obilet.task.view.FragmentFlightIndex

/**
 * Adapter that binds ViewPagers to fragments
 */

class DashBoardPagerAdapter (fragment: Fragment): FragmentStateAdapter(fragment){
    override fun getItemCount(): Int =2

    override fun createFragment(position: Int): Fragment {
        return  when(position){
            0-> FragmentBusIndex()
            else-> FragmentFlightIndex()


        }
    }

}