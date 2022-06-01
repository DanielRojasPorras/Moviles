package cr.ac.gpsservice.service

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback

class GpsService : IntentService("GpsService") {
    private lateinit var mLocationCallback: LocationCallback
    private lateinit var fusedLocationClient : FusedLocationProviderClient // proveedor de localizacion google

    companion object{
        val GPS = "cr.ac.gpsservice.GPS_ENVENT"
    }
    override fun onHandleIntent(intent: Intent?) {
        getLocation()
    }

    @SuppressLint("MissingPermission")
    /*
    *
    * */
    fun getLocation(){

    }


}