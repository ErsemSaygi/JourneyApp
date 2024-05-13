package com.obilet.task.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.obilet.task.di.Service
import com.obilet.task.model.Location
import com.obilet.task.model.LocationResponse
import com.obilet.task.utilities.DateHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FragmentTravelQueryViewModel
@Inject constructor(
    application: Application,
    private val dateHelper: DateHelper,
    private val travelAPIService: Service):BaseViewModel(application) {

    var busLocationsQuery= MutableLiveData<List<Location>>()
    private val disposable= CompositeDisposable()

    val progressLoading= MutableLiveData<Boolean>()
    val clothesError= MutableLiveData<Boolean>()




    //For search city RxJava
    fun searchCity(searchedText: String) {
        val currentDate = dateHelper.getDate()
        disposable.add(
            travelAPIService.getBusLocationsWithQuery(currentDate,searchedText)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<LocationResponse>() {
                    override fun onSuccess(busLocation: LocationResponse) {
                        busLocationsQuery.value=busLocation.data
                        progressLoading.value=false
                    }

                    override fun onError(e: Throwable) {
                        progressLoading.value = false
                        clothesError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }
}