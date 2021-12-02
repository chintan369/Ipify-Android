package com.creative.androidipfy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.creative.androidipfy.databinding.ActivityMainBinding
import com.creative.ipfyandroid.Ipfy

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtCurrentIpAddress.text = "N/A"

        val ipiFy = Ipfy.init(this)

        ipiFy.getPublicIpObserver().observe(this, { ipData ->
            if(ipData.currentIpAddress != null){
                binding.txtCurrentIpAddress.text = ipData.currentIpAddress
            }
            else{
                binding.txtCurrentIpAddress.text = "N/A"
            }
        })

    }
}