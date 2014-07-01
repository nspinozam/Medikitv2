package notification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import models.Receta;

import com.nspinozam.medikitv2.R;

import database.Core;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationResult extends Activity
{
	 private PendingIntent pendingIntent;
	 private Receta receta;
	 private int idNotificacion;
	 private Context ctx;
	 private static long MILLISPARADIEZ = 600000;// Milliseconds in 10 minutes.
	 private static long MILLISPARADIA = 86400000;// Milliseconds in 1 day.
	 Core insCore = new Core();
	 NotificationManager mNotificationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_selected);
		ctx = this;
		mNotificationManager =
				    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		TextView tv_infoMedicina = (TextView)findViewById(R.id.med_info);
		TextView tv_infoPaciente = (TextView)findViewById(R.id.patient_info);
		TextView tv_infoCantidad = (TextView)findViewById(R.id.quantity_info);
		// Get Intent data
		Intent intent = getIntent();
		receta = (Receta)intent.getExtras().getSerializable("Receta");
		idNotificacion = intent.getExtras().getInt("idNotificacion");
		Log.i("Result idNotificacion send", String.valueOf(idNotificacion));
		tv_infoPaciente.setText(receta.nombrePaciente);
		tv_infoCantidad.setText(String.valueOf(receta.cantidadConsumo)+" "+receta.nombreConsumo);
		tv_infoMedicina.setText(receta.nombreMedicina);
		
		Button btn_aceptar = (Button)findViewById(R.id.btn_listo);
		btn_aceptar.setOnClickListener(new OnClickListener() {
			//TODO revisar xq no se quita con el listo
			@Override
			public void onClick(View v) {
				mNotificationManager.cancel(Integer.valueOf(receta.idReceta));
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Calendar finish_date = Calendar.getInstance();
				try {
					finish_date.setTime(sdf.parse(receta.fechaI));
					finish_date.add(Calendar.DATE, receta.duracionDias);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar today = Calendar.getInstance();
				//Si la fecha de fin no ha terminado
				if(finish_date.compareTo(today)==-1) {
					long timeInMillis = today.getTimeInMillis()+MILLISPARADIA;
					Intent myIntent = new Intent(NotificationResult.this, MyReceiver.class);
				    myIntent.putExtra("Receta", receta);
				    myIntent.putExtra("idNotificacion", idNotificacion);
				    Log.i("Result idNotificacion send", String.valueOf(idNotificacion));
					pendingIntent = PendingIntent.getBroadcast(NotificationResult.this, Integer.valueOf(receta.idReceta), myIntent,0);
				    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
				    alarmManager.set(AlarmManager.RTC, timeInMillis, pendingIntent);
					finish();
				}
				//borar DB
				else {
					insCore.EliminarReceta(receta.idReceta, ctx);
					finish();
				}
			}
		});
		
		Button btn_posponer = (Button)findViewById(R.id.btn_postponer);
		btn_posponer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mNotificationManager.cancel(Integer.valueOf(receta.idReceta));
				//get time
				Calendar recipe_calendar = Calendar.getInstance();
				long timeInMillis = recipe_calendar.getTimeInMillis()+MILLISPARADIEZ;
				Intent myIntent = new Intent(NotificationResult.this, MyReceiver.class);
			    myIntent.putExtra("Receta", receta);
			    myIntent.putExtra("idNotificacion", idNotificacion);
			    Log.i("Result idNotificacion send", String.valueOf(idNotificacion));
				pendingIntent = PendingIntent.getBroadcast(NotificationResult.this, Integer.valueOf(idNotificacion), myIntent,0);
			    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
			    alarmManager.set(AlarmManager.RTC, timeInMillis, pendingIntent);
			    finish();
			}
		});
	}	
}
