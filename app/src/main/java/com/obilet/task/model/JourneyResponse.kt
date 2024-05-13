package com.obilet.task.model

import com.google.gson.annotations.SerializedName
/**
 * Data class for BusJourneys return
 */
data class JourneyResponseJourney(
    val status: String,
    val data: List<Journey>,
    val message: String?,
    val userMessage: String?,
    @SerializedName("api-request-id") val apiRequestId: String?,
    val controller: String
)
data class Journey (
    val id: Int,
    @SerializedName("partner-id") val partnerId: Int,
    @SerializedName("partner-name") val partnerName: String,
    @SerializedName("route-id") val routeId: Int,
    @SerializedName("bus-type") val busType: String,
    @SerializedName("total-seats") val totalSeats: Int,
    @SerializedName("available-seats") val availableSeats: Int,
    val journey: JourneyInfo,
    val features: List<Feature>,
    @SerializedName("origin-location") val originLocation: String,
    @SerializedName("destination-location") val destinationLocation: String,
    @SerializedName("is-active") val isActive: Boolean,
    @SerializedName("origin-location-id") val originLocationId: Int,
    @SerializedName("destination-location-id") val destinationLocationId: Int,
    @SerializedName("is-promoted") val isPromoted: Boolean,
    @SerializedName("cancellation-offset") val cancellationOffset: Int,
    @SerializedName("has-bus-shuttle") val hasBusShuttle: Boolean,
    @SerializedName("disable-sales-without-gov-id") val disableSalesWithoutGovId: Boolean,
    @SerializedName("partner-rating") val partnerRating: Double
)

data class JourneyInfo(
    val kind: String,
    val code: String,
    val stops: List<Stop>,
    val origin: String,
    val destination: String,
    val departure: String,
    val arrival: String,
    val currency: String,
    val duration: String,
    @SerializedName("original-price") val originalPrice: Int,
    @SerializedName("internet-price") val internetPrice: Int,
    val booking: Any?, // Null olabilir
    @SerializedName("bus-name") val busName: String,
    val policy: Policy?,
    val description: String?,
    val available: Any? // Null olabilir
)

data class Stop(
    val name: String,
    val station: String,
    val time: String,
    @SerializedName("is-origin") val isOrigin: Boolean,
    @SerializedName("is-destination") val isDestination: Boolean
)

data class Policy(
    @SerializedName("max-seats") val maxSeats: Int?,
    @SerializedName("max-single") val maxSingle: Int?,
    @SerializedName("max-single-males") val maxSingleMales: Int?,
    @SerializedName("max-single-females") val maxSingleFemales: Int?,
    @SerializedName("mixed-genders") val mixedGenders: Boolean,
    @SerializedName("gov-id") val govId: Boolean,
    val lht: Boolean
)

data class Feature(
    val id: Int,
    val priority: Int,
    val name: String,
    val description: String?,
    @SerializedName("is-promoted") val isPromoted: Boolean,
    @SerializedName("back-color") val backColor: String?,
    @SerializedName("fore-color") val foreColor: String?
)
