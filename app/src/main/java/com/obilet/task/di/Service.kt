package com.obilet.task.di

import com.obilet.task.data.remote.TravelAPI
import com.obilet.task.model.*
import com.obilet.task.utilities.Constants
import com.obilet.task.utilities.DateHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideTravelAPI(): TravelAPI {
        val BASE_URL = Constants.url
        val client = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Basic JEcYcEMyantZV095WVc3G2JtVjNZbWx1")
                        .build()
                    return chain.proceed(request)
                }
            })
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TravelAPI::class.java)
    }


}

class Service @Inject constructor(
    private val travelAPI: TravelAPI,
    private val dateHelper: DateHelper
) {
    fun getSession(): Single<User> {
        val postData = PostData(
            type = 3,
            connection = mapOf(),
            application = mapOf("version" to "3.1.0.0", "equipment-id" to "DD2A0857-7C7D-4376-A83B-E045435E82BB")
        )
        return travelAPI.getSession(postData)
    }

    fun getBusLocations(date: String, sessionId: String, deviceId: String): Single<LocationResponse> {
        val postData = PostDataBusLocations(
            data = "",
            date = date,
            device_session = mapOf("session-id" to sessionId, "device-id" to deviceId),
            language = "tr-TR"
        )
        return travelAPI.getBusLocations(postData)
    }

    fun getBusLocationsWithQuery(date: String, data: String,sessionId:String,deviceId:String): Single<LocationResponse> {
        val postData = PostDataBusLocations(
            data = data,
            date = date,
            device_session = mapOf("session-id" to sessionId, "device-id" to deviceId),
            language = "tr-TR"
        )
        return travelAPI.getBusLocationsWithQuery(postData)
    }

    fun getTravels(date: String,originId:Int,sessionId: String,destinationId:Int,deviceId: String,dateTravel:String): Single<JourneyResponseJourney> {
        val postData = PostDataBusLocations(
            data = mapOf("origin-id" to originId, "destination-id" to destinationId, "departure-date" to dateHelper.convertTravelDate(dateTravel)),
            date = date,
            device_session = mapOf("session-id" to sessionId, "device-id" to deviceId),
            language = "tr-TR"
        )
        return travelAPI.getTravels(postData)
    }
}
