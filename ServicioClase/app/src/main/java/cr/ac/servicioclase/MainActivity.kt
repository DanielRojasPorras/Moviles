package cr.ac.servicioclase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

lateinit var  textView: TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var filter = IntentFilter()
        var progreso = Progreso()
        filter.addAction(TimeService.FIN)
        filter.addAction(TimeService.PROGRESO)

        registerReceiver(progreso,filter)

        var button = findViewById<Button>(R.id.button)
        textView = findViewById(R.id.textView)
        button.setOnClickListener{
            val timeService = Intent(this, TimeService::class.java)
            timeService.putExtra("iteraciones",10)
            startService(timeService)
        }

    }
    class Progreso : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
           if( p1?.action.equals(TimeService.PROGRESO)){
               val valor : Int = p1?.getIntExtra("progreso", 0)!!
               textView.setText(valor.toString())

           }
            if(p1?.action.equals(TimeService.FIN)){
                textView.setText("Progreso Finalizado")
           }
        }

    }
}