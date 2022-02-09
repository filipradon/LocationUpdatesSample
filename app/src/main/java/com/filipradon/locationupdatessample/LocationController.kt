package com.filipradon.locationupdatessample

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationController(context: Context) {

    companion object {
        @Volatile private var INSTANCE: LocationController? = null

        fun getInstance(context: Context): LocationController {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocationController(context).also { INSTANCE = it }
            }
        }
    }

    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest by lazy {
        LocationRequest.create()
            .setInterval(INTERVAL_MILLIS)
            .setFastestInterval(FASTEST_INTERVAL_MILLIS)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            Log.d("elo", "onLocationResult! ${locationResult.lastLocation}")
        }

        override fun onLocationAvailability(locationAvailability: LocationAvailability) {
            super.onLocationAvailability(locationAvailability)
        }
    }
    var isStarted: Boolean = false

    @SuppressLint("MissingPermission")
    fun start() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
            .addOnSuccessListener {
                Log.d("elo", "requestLocationUpdates success!")
            }
        isStarted = true
    }

    fun stop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            .addOnSuccessListener {
                Log.d("elo", "removeLocationUpdates success!")
            }
        isStarted = false
    }

}