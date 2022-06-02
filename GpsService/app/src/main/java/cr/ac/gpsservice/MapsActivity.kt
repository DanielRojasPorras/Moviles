package cr.ac.gpsservice

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import cr.ac.gpsservice.databinding.ActivityMapsBinding
import cr.ac.gpsservice.db.LocationDatabase
import cr.ac.gpsservice.entity.Location

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val SOLICITA_GPS = 1
    private lateinit var mLocationClient : FusedLocationProviderClient // proveedor de localización google
    private lateinit var mLocationRequest : LocationRequest
    private lateinit var mLocationCallback: LocationCallback

    private lateinit var locationDatabase: LocationDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationDatabase = LocationDatabase.getInstance(this)
        mLocationCallback = object : LocationCallback() {
            // determina que se hace cuando hay una ubicacion gps
            override fun onLocationResult(locationResult: LocationResult) {
                if (mMap != null && locationResult.equals(null)) {
                    return
                } // Dibujar en el mapa los puntos
                for (location in locationResult.locations) {

                    val sydney = LatLng(location.latitude, location.longitude)
                    mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

                    locationDatabase.locationDao.insert(Location(null, location.latitude, location.longitude))
                }
            }
        }

        mLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 10000 // If not here
        mLocationRequest.fastestInterval = 5000  // If it can it'll do it here
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        validaPermisos()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        iniciaServicio()

        recuperarPuntos()
    }

    /**
     * Obtener los puntos de la base de datos y mostrarlos en el mapa
     */
    fun recuperarPuntos(){

        for(location in locationDatabase.locationDao.query()){
            val sidney = LatLng(location.latitude, location.longitude)
            mMap.addMarker(MarkerOptions().position(sidney).title("Marker in Africa"))
        }
    }

    /**
     * Hace un filtro del broadcast/accion GPS (cr.ac.gpsservices.GPS_EVENT)
     * E inicia el servicio (startService) GpsService
     */
    fun iniciaServicio(){

    }

    /**
     * Valida los permisos(ACCES_FINE) y si no los tiene los solicita con el
     */
    fun validaPermisos(){
        // ¿tengo permisos de gps?

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            // NO TENGO PERMISOS
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                SOLICITA_GPS
            )
        } else {
            mLocationClient.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback, null
            )
        }
    }


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            SOLICITA_GPS -> {
                if ( grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationClient.requestLocationUpdates(
                        mLocationRequest,
                        mLocationCallback, null
                    )

                }
                else { // el usuario dio permisos de GPS
                    System.exit(1)
                }
            }
        }

    }
}