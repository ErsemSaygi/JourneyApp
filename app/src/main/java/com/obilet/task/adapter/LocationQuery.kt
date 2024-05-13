package com.obilet.task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.obilet.task.R
import com.obilet.task.databinding.ItemLocationQueryBinding
import com.obilet.task.model.Location
import com.obilet.task.utilities.ButtonType
import com.obilet.task.utilities.LocationQueryClickListener
import com.obilet.task.view.FragmentTravelQueryDirections


/**
 * City Search results RecyclerView
 */
class LocationQuery (val locationList:ArrayList<Location>): RecyclerView.Adapter<LocationQuery.LocationViewHolder>(),
    LocationQueryClickListener
{

    private var btnType:ButtonType=ButtonType.ORIGIN

    class LocationViewHolder(var view: ItemLocationQueryBinding) : RecyclerView.ViewHolder(view.root){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {



        val inflater= LayoutInflater.from(parent.context);

        val view= DataBindingUtil.inflate<ItemLocationQueryBinding>(inflater, R.layout.item_location_query,parent,false)
        return LocationViewHolder(view);

    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {///itemlarda neler olacağını tanımladık


        holder.view.location=locationList[position]
        holder.view.listener=this

    }

    override fun getItemCount(): Int {
        return locationList.size;
    }


    fun updateQueryList(newLocationList:List<Location>,buttonType: ButtonType){
        locationList.clear();
        locationList.addAll(newLocationList);
        btnType=buttonType

        notifyDataSetChanged()
    }


    override fun onLocationClicked(v: View) {
        val locationQueryIdTextView = v.findViewById<TextView>(R.id.locationQueryId)
        val locationId = locationQueryIdTextView.text.toString()
        val action = FragmentTravelQueryDirections.actionFragmentTravelQueryToDashBoardFragment(btnType,locationId)


        Navigation.findNavController(v).navigate(action)
    }
}