package com.obilet.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.obilet.task.R
import com.obilet.task.databinding.ItemTravelBinding
import com.obilet.task.model.JourneyInfo


/**
 * For bus travel RecyclerView
 */
class Travels (val travelList:ArrayList<JourneyInfo>): RecyclerView.Adapter<Travels.TravelsViewHolder>()
{



    class TravelsViewHolder(var view: ItemTravelBinding) : RecyclerView.ViewHolder(view.root){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelsViewHolder {



        val inflater= LayoutInflater.from(parent.context);

        val view= DataBindingUtil.inflate<ItemTravelBinding>(inflater, R.layout.item_travel,parent,false)
        return TravelsViewHolder(view);

    }

    override fun onBindViewHolder(holder: TravelsViewHolder, position: Int) {

        val timeDeparture = travelList[position].departure.substringAfter('T').substringBeforeLast(':')
        val timeArrival = travelList[position].arrival.substringAfter('T').substringBeforeLast(':')
        val dest=travelList[position].origin+"-"+travelList[position].destination
        holder.view.journey=travelList[position]
        holder.view.departure=timeDeparture
        holder.view.arrival=timeArrival
        holder.view.destinationRoute=dest





    }

    override fun getItemCount(): Int {
        return travelList.size;
    }


    fun updateQueryList(newLocationList:List<JourneyInfo>){
        travelList.clear();
        travelList.addAll(newLocationList);


        notifyDataSetChanged()
    }



}