package notification;

import models.Receta;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver
{
	 private Receta receta;
	 private int idNotificacion;
	@Override
	 public void onReceive(Context context, Intent intent)
	{
		try{
			receta = (Receta)intent.getExtras().getSerializable("Receta");
			idNotificacion = intent.getExtras().getInt("idNotificacion");
			Intent service = new Intent(context, MyAlarmService.class);
			service.putExtra("Receta", receta);
			service.putExtra("idNotificacion", idNotificacion);
			Log.i("Recibido id:", String.valueOf(idNotificacion));
			context.startService(service);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	   
	   
	 }
	
}
