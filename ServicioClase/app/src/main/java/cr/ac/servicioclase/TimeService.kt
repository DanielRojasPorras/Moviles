package cr.ac.servicioclase

import android.app.IntentService
import android.content.Intent



/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class - update intent actions and extra parameters.
 */
class TimeService : IntentService("TimeService") {

    companion object{
        val PROGRESO = "cr.ac.servicioclase.PROGRESO"
        var FIN = "cr.ac.servicioclase.FIN"

    }

    override fun onHandleIntent(intent: Intent?) {
       var inter = intent?.getIntExtra("iteraciones",0)

        for(i in 0 .. inter!!){
            tarea()
            var bcIntent = Intent()
            bcIntent.setAction(PROGRESO)
            bcIntent.putExtra("progreso",i*10)
            sendBroadcast(bcIntent)
        }
        var bcIntent = Intent()
        bcIntent.setAction(FIN)
        sendBroadcast(bcIntent)
    }

    fun tarea(){
        //simulamos que esto hace algo
        Thread.sleep(6000)
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionFoo(param1: String?, param2: String?) {
        TODO("Handle action Foo")
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionBaz(param1: String?, param2: String?) {
        TODO("Handle action Baz")
    }
}