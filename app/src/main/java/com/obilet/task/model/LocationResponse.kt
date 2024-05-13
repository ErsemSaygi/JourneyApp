package com.obilet.task.model

import com.google.gson.annotations.SerializedName
/**
 * Data class for BusLocations return
 */

data class LocationResponse(
    val status: String,
    val data: List<Location>,
    val message: String?,
    val userMessage: String?,
    val apiRequestId: String?,
    val controller: String?
)

data class Location(
    @SerializedName("id") val id: Int,
    @SerializedName("parent-id") val parentId: Int,
    val type: String,
    val name: String,
    @SerializedName("geo-location") val geoLocation: GeoLocation,
    @SerializedName("tz-code") val tzCode: String,
    @SerializedName("weather-code") val weatherCode: String?,
    val rank: Int,
    @SerializedName("reference-code") val referenceCode: String?,
    val keywords: String
)

data class GeoLocation(
    val latitude: Double,
    val longitude: Double,
    val zoom: Int
)

