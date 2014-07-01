package notification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import models.Receta;
import database.Core;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.MonthDisplayHelper;

public class NotificationButtonAction extends BroadcastReceiver {
	 private PendingIntent pendingIntent;
	 private Receta receta;
	 int idNotificacion;
	 private Context ctx;
	 private static long MILLISPARADIEZ = 600000;// Milliseconds in 10 minutes.
	 private static long MILLISPARADIA = 86400000;// Milliseconds in 1 day.
	 Core insCore = new Core();
	@Override
	 public void onReceive(Context context, Intent intent) {
		receta = (Receta)intent.getExtras().getSerializable("Receta");
		idNotificacion = intent.getExtras().getInt("idNotificacion");
		Log.i("ButtonAction idNotificacion", String.valueOf(idNotificacion));
		ctx = context;
		String tipo = intent.getType();
		NotificationManager manager =
			    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancel(Integer.valueOf(idNotificacion));
		if(tipo.equals("take-it")){
			manager.cancel(Integer.valueOf(idNotificacion));
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Calendar finish_date = Calendar.getInstance();
			try {
				finish_date.setTime(sdf.parse(receta.fechaI));
				finish_date.add(Calendar.DATE, receta.duracionDias);
				finish_date.set(Calendar.MONTH, finish_date.MONTH-1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar today = Calendar.getInstance();
			//Si la fecha de fin no ha terminado
			if(finish_date.compareTo(today)==-1) {
				long timeInMillis = today.getTimeInMillis()+MILLISPARADIA;
				Intent myIntent = new Intent(ctx, MyReceiver.class);
			    myIntent.putExtra("Receta", receta);
			    myIntent.putExtra("idNotificacion",idNotificacion);
			    Log.i("Buttton idNotificacion send", String.valueOf(idNotificacion));
				pendingIntent = PendingIntent.getBroadcast(ctx, Integer.valueOf(idNotificacion), myIntent,0);
			    @SuppressWarnings("static-access")
				AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(ctx.ALARM_SERVICE);
			    alarmManager.set(AlarmManager.RTC, timeInMillis, pendingIntent);
			}
			//borar de la DB
			else {
				insCore.EliminarReceta(receta.idReceta, ctx);
				manager.cancel(Integer.valueOf(idNotificacion));
			}
			Log.i("Notif Status", "tomado");
			
		}
		else if(tipo.equals("ten-more")){
			manager.cancel(Integer.valueOf(idNotificacion));
			//get time
			Calendar recipe_calendar = Calendar.getInstance();
			long timeInMillis = recipe_calendar.getTimeInMillis()+MILLISPARADIEZ;
			Intent myIntent = new Intent(ctx, MyReceiver.class);
		    myIntent.putExtra("Receta", receta);
		    myIntent.putExtra("idNotificacion", idNotificacion);
		    Log.i("Button idNotificacion send", String.valueOf(idNotificacion));
			pendingIntent = PendingIntent.getBroadcast(ctx, Integer.valueOf(idNotificacion), myIntent,0);
		    AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(ctx.ALARM_SERVICE);
		    alarmManager.set(AlarmManager.RTC, timeInMillis, pendingIntent);
			Log.i("Notif Status", "ten-more");
		}
	   
	 }
	
}
