package com.obilet.task.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.obilet.task.di.Service
import com.obilet.task.model.JourneyInfo
import com.obilet.task.model.JourneyResponseJourney
import com.obilet.task.utilities.DateHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FragmentTravelsViewModel @Inject constructor(application: Application, private val dateHelper: DateHelper):BaseViewModel(application) {

    private val travelAPIService= Service()
    private val disposable= CompositeDisposable()
    var travelList= MutableLiveData<List<JourneyInfo>>()


    //For journeys RxJava
    fun getTravelFromAPI(){

        disposable.add(
            travelAPIService.getTravels(dateHelper.getDate())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<JourneyResponseJourney>() {
                    override fun onSuccess(travels: JourneyResponseJourney) {
                        print(travels)

                        val journeyInfoList = mutableListOf<Pair<JourneyInfo, String>>()


                        travels.data.forEach { journey ->
                            journeyInfoList.add(Pair(journey.journey, journey.journey.departure))
                        }


                        journeyInfoList.sortBy { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(it.second) }


                        val sortedJourneyInfoList = mutableListOf<JourneyInfo>()
                        journeyInfoList.forEach { sortedJourneyInfoList.add(it.first) }

                        travelList.value=sortedJourneyInfoList



                    }

                    override fun onError(e: Throwable) {

                        e.printStackTrace()
                    }
                })
        )

    }
}