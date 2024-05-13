package com.obilet.task.model

import com.google.gson.annotations.SerializedName
/**
 * Data class for service requests
  */
data class PostData(
    @SerializedName("type") val type: Int,
    @SerializedName("connection") val connection:  Map<String, String>,
    @SerializedName("application") val application: Map<String, String>
)

data class PostDataBusLocations(
    @SerializedName("data") val data:Any,
    @SerializedName("device-session") val device_session: Map<String, String>,
    @SerializedName("date") val date: String,
    @SerializedName("language") val language: String="tr-TR"
)

data class PostTravels(
    @SerializedName("data") val data:Map<String, String>,
    @SerializedName("device-session") val device_session: Map<String, String>,
    @SerializedName("date") val date: String,
    @SerializedName("language") val language: String="tr-TR"
)

data class User(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: UserData,
    @SerializedName("message") val message: String?,
    @SerializedName("user-message") val userMessage: String?,
    @SerializedName("api-request-id") val apiRequestId: String?,
    @SerializedName("controller") val controller: String?
)

data class UserData(
    @SerializedName("session-id") val sessionId: String,
    @SerializedName("device-id") val deviceId: String
)
