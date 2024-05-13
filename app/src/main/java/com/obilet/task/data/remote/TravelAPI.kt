package com.obilet.task.data.remote

import com.obilet.task.model.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface TravelAPI {


    @POST("client/getsession")
    fun getSession(
        @Body requestBody: PostData
    ): Single<User>

    @POST("location/getbuslocations")
    fun getBusLocations(
        @Body requestBody: PostDataBusLocations
    ): Single<LocationResponse>

    @POST("location/getbuslocations")
    fun getBusLocationsWithQuery(
        @Body requestBody: PostDataBusLocations
    ): Single<LocationResponse>

    @POST("journey/getbusjourneys")
    fun getTravels(
        @Body requestBody: PostDataBusLocations
    ): Single<JourneyResponseJourney>

}