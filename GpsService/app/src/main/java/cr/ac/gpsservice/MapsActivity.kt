package cr.ac.gpsservice

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import cr.ac.gpsservice.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val SOLICITAR_GPS = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    /*
    * Obtener los puntos de la base datos y mostrarlos en el mapa*/
    fun recuperarPuntos(){

    }
    /*
    * Hace filtro del brodcast gps
    * e inicia el servicio GpsService
    * */
    fun iniciaServicio(){

    }
    /*
    * valida si la app tiene permisos de ACCESS_FINE_LOCATION y ACCESS_COARSE_LOCATION
    * si no tiene permisos solicita al usuario
    * */
    fun validaPermisos(){

    }

    /*
    * validar que se le dieron los permisos a la app, en caso contrario salir
    *
    * */

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

    }
    /*
       * Es la clase para recibir los mensajes de broadcast de gps*/
    class ProgressReceiver : BroadcastReceiver(){
        /*
        * Se obtiene el parametro enviado por el servicio (location)
        * Colaca en el mapa la localizacion
        * Mueve la camara a esa localizacion*/
        override fun onReceive(p0: Context?, p1: Intent?) {

        }

    }
}