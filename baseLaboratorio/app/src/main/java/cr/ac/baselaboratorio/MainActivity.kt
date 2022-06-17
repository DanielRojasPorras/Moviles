package cr.ac.baselaboratorio

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    companion object{
        var OPEN_DIRECTORY_REQUEST_CODE = 1
    }
    private lateinit var buttonPlay: Button
    private lateinit var buttonPause: Button
    private lateinit var buttonStop: Button
    private lateinit var buttonBefore: Button
    private lateinit var buttonNext: Button

    lateinit var rootTree : DocumentFile
    var mediaPlayer = MediaPlayer()
    var contador = 1
    var activo = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(intent, OPEN_DIRECTORY_REQUEST_CODE)

        buttonPlay = findViewById(R.id.buttonPlay)
        buttonPause = findViewById(R.id.buttonPause)
        buttonStop = findViewById(R.id.buttonStop)
        buttonNext = findViewById(R.id.buttonNext)
        buttonBefore = findViewById(R.id.buttonBefore)
        setOnClickListeners(this)
    }
    private fun setOnClickListeners(context: Context) {
        buttonPlay.setOnClickListener {

            if(activo == contador){
                mediaPlayer.start()
                Toast.makeText(context,"Archivo: " + rootTree.listFiles()?.get(contador).name, Toast.LENGTH_SHORT).show()
            }
            else {
                try {
//
                    Toast.makeText(context,"Archivo: " + rootTree.listFiles()?.get(2).name, Toast.LENGTH_SHORT).show()
                    mediaPlayer.setDataSource(this, rootTree.listFiles()?.get(contador).uri)
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                    activo = contador
                } catch (e: Exception) {
                    Log.e(
                        "Error",
                        "No puede ejecutar el archivo" + rootTree.listFiles()?.get(contador).uri
                    )
                }
            }
            Toast.makeText(context, "Reproduciendo...", Toast.LENGTH_SHORT).show()
        }

        buttonPause.setOnClickListener {
            mediaPlayer.pause()
            Toast.makeText(context, "En pausa...", Toast.LENGTH_SHORT).show()
        }

        buttonStop.setOnClickListener {
            mediaPlayer.stop()
            contador = 1
            activo = 0
//            mediaPlayer = MediaPlayer.create(context, R.raw.estimados_vecinos)
            Toast.makeText(context, "Parando...", Toast.LENGTH_SHORT).show()
        }
        buttonNext.setOnClickListener {
              mediaPlayer.stop()
              mediaPlayer = MediaPlayer()
//            mediaPlayer = MediaPlayer.create(context, R.raw.estimados_vecinos)
//            Toast.makeText(context, "Parando...", Toast.LENGTH_SHORT).show()
            if(contador == rootTree.listFiles()?.size ){
                contador = 1
                activo = 0
            }else {
                contador = contador + 1
                activo = contador
            }
            try {

                Toast.makeText(context,"Archivo: " + rootTree.listFiles()?.get(contador).name, Toast.LENGTH_SHORT).show()
                mediaPlayer.setDataSource(this, rootTree.listFiles()?.get(contador).uri)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: Exception) {
                Log.e(
                    "Error",
                    "No puede ejecutar el archivo" + rootTree.listFiles()?.get(contador).uri
                )
            }
        }
        buttonBefore.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer = MediaPlayer()
//            mediaPlayer = MediaPlayer.create(context, R.raw.estimados_vecinos)
//            Toast.makeText(context, "Parando...", Toast.LENGTH_SHORT).show()
//            if(contador>1){
                contador = contador - 1
                activo=contador
//            }
//            else{
//                contador = rootTree.listFiles()?.size
//                activo=contador
//            }
            try {
                //
                Toast.makeText(context,"Archivo: " + rootTree.listFiles()?.get(contador).name, Toast.LENGTH_SHORT).show()
                mediaPlayer.setDataSource(this, rootTree.listFiles()?.get(contador).uri)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: Exception) {
                Log.e(
                    "Error",
                    "No puede ejecutar el archivo" + rootTree.listFiles()?.get(contador).uri
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == OPEN_DIRECTORY_REQUEST_CODE ){
            if(resultCode == Activity.RESULT_OK){
                var directoryUri = data?.data ?: return
               // Log.e("Directorio",directoryUri.toString())
                rootTree = DocumentFile.fromTreeUri(this, directoryUri)!!
            }
        }
    }

}