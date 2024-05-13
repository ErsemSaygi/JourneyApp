package com.obilet.task.view.utils

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.obilet.task.R
import com.obilet.task.adapter.DashBoardPagerAdapter
import com.obilet.task.databinding.FragmentDashboardBinding
import com.obilet.task.utilities.ButtonType
import com.obilet.task.utilities.SharedPreferencesManager
import com.obilet.task.view.FragmentBusIndexArgs
import com.obilet.task.viewmodel.FragmentBusIndexViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class DashBoardFragment : Fragment() {
    @Inject
    lateinit var sharedPreferences: SharedPreferencesManager
    private lateinit var binding: FragmentDashboardBinding
    private val sharedViewModel: FragmentBusIndexViewModel by activityViewModels()
    private val tabTitles = mutableMapOf(
        "Otobüs Bileti" to R.drawable.ic_bus,
        "Uçak Bileti" to R.drawable.ic_plane
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDashboardBinding.inflate(layoutInflater)
        setUpTabLayoutWithViewPager()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val buttonType = FragmentBusIndexArgs.fromBundle(it).buttonType
            if (buttonType != ButtonType.DEFAULT) {

                val cityId = FragmentBusIndexArgs.fromBundle(it).cityId
                sharedViewModel.btnType.value = buttonType
                sharedViewModel.cityId.value = cityId

                if (buttonType == ButtonType.DESTINATION) {

                    sharedPreferences.putString("destinationId", cityId)
                } else if (buttonType == ButtonType.ORIGIN) {
                    sharedPreferences.putString("originId", cityId)
                }
            }

        }

    }

    //ViewPager setup
    private fun setUpTabLayoutWithViewPager() {
        val titles = ArrayList(tabTitles.keys)

        binding.viewPager.adapter = DashBoardPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
        tabTitles.values.forEachIndexed { index, imageResId ->
            val textView =
                LayoutInflater.from(requireContext()).inflate(R.layout.tab_title, null) as TextView
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(imageResId, 0, 0, 0)
            textView.compoundDrawablePadding =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f, resources.displayMetrics)
                    .roundToInt()
            binding.tabLayout.getTabAt(index)?.customView = textView

        }

    }


}