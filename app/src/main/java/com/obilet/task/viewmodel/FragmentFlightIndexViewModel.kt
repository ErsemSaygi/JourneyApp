package com.obilet.task.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.obilet.task.model.Location
import com.obilet.task.utilities.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class FragmentFlightIndexViewModel
@Inject constructor(
    application: Application,
):
    BaseViewModel(application) {

    var busLocations = MutableLiveData<List<Location>>()
    var passengerType = MutableLiveData<String>()




    //For bus locations RxJava
     fun getSession() {
        busLocations = MutableLiveData(Constants.listLocation)


    }


}

