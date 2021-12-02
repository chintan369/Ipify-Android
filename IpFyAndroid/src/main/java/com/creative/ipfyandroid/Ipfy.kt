package com.creative.ipfyandroid

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Ipfy private constructor(private val context: Context, private val type: IpfyClass) {

    private val connectionObserver: ConnectionLiveData = ConnectionLiveData(context)

    private val ipifyService: IpfyService = getIpfyService(type)

    private val ipAddressData = IPAddressData(null, null)

    private val ipAddressLiveData = MutableLiveData<IPAddressData>()

    init {
        connectionObserver.observeForever { isConnectedToInternet ->
            if (isConnectedToInternet) {
                callIpifyForCurrentIP()
            } else {
                resetCurrentIp()
            }
        }
    }

    private fun resetCurrentIp() {
        ipAddressData.lastStoredIpAddress = ipAddressData.currentIpAddress
        ipAddressData.currentIpAddress = null
        ipAddressLiveData.value = ipAddressData
    }

    private fun callIpifyForCurrentIP() {
        ipifyService.getPublicIpAddress().enqueue(object : Callback<IpifyResponse> {
            override fun onResponse(call: Call<IpifyResponse>, response: Response<IpifyResponse>) {
                val ipResponse = response.body()
                if (response.isSuccessful && ipResponse != null) {
                    ipAddressData.lastStoredIpAddress = ipAddressData.currentIpAddress
                    ipAddressData.currentIpAddress = ipResponse.ip
                    ipAddressLiveData.value = ipAddressData
                } else {
                    resetCurrentIp()
                }
            }

            override fun onFailure(call: Call<IpifyResponse>, t: Throwable) {
                resetCurrentIp()
            }
        })
    }

    fun getPublicIpObserver(): MutableLiveData<IPAddressData> {
        return ipAddressLiveData
    }

    private fun getIpfyService(type: IpfyClass): IpfyService {
        val retrofit = Retrofit.Builder()
            .baseUrl(if (type == IpfyClass.UniversalIP) UNIVERSAL_IP_BASE_URL else IPv4_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(IpfyService::class.java)
    }


    companion object {

        private const val UNIVERSAL_IP_BASE_URL = "https://api64.ipify.org/"
        private const val IPv4_BASE_URL = "https://api.ipify.org/"

        @SuppressLint("StaticFieldLeak")
        private var instance: Ipfy? = null

        fun init(context: Context, type: IpfyClass = IpfyClass.IPv4): Ipfy {
            val newInstance = Ipfy(context, type)
            instance = newInstance
            return newInstance
        }

        @JvmName("getInstance1")
        fun getInstance(): Ipfy {
            instance?.let {
                return it
            } ?: kotlin.run {
                throw Exception("Ipfy.init() with application context and type must be called to initialize Ipfy")
            }
        }
    }

}