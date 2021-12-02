package com.creative.ipfyandroid

import retrofit2.Call
import retrofit2.http.GET

internal interface IpfyService {

    @GET("?format=json")
    fun getPublicIpAddress(): Call<IpifyResponse>

}