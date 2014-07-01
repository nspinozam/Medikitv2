package notification;

import com.nspinozam.medikitv2.ActivityAgregarReceta;
import com.nspinozam.medikitv2.R;

import models.Receta;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.app.Notification.Builder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
                           

public class MyAlarmService extends Service {
	 private Receta receta;
	 private int idNotificacion;
     private NotificationManager mManager;
     static String TAKE_IT = "take-it";
     static String TEN_MORE = "ten-more";

     @Override
     public IBinder onBind(Intent arg0)
     {
       // TODO Auto-generated method stub
        return null;
     }

    @Override
    public void onCreate() 
    {
       // TODO Auto-generated method stub  
       super.onCreate();
    }


   @SuppressLint("NewApi")
@Override
   public void onStart(Intent intent, int startId)
   {
       super.onStart(intent, startId); 
       String[] events = new String[3];
       events[0] = "Medication: ";
       events[1] = "Quantity: ";
       events[2] = "Patient: ";
       try{
    	   receta = (Receta)intent.getExtras().getSerializable("Receta");;
    	   idNotificacion = intent.getExtras().getInt("idNotificacion");
    	   events[0]+=receta.nombreMedicina;
    	   events[1]+=receta.cantidadConsumo+" "+receta.nombreConsumo;
    	   events[2]+=receta.nombrePaciente;
       }catch(Exception e){
    	   e.printStackTrace();
       }
       Intent intent1 = new Intent(this.getApplicationContext(),NotificationButtonAction.class);
       Intent normal = new Intent(this.getApplicationContext(), NotificationResult.class);
       Intent intent2 = new Intent(this.getApplicationContext(),NotificationButtonAction.class);
       // set actions to the intent
       intent1.setType(TAKE_IT);
       intent2.setType(TEN_MORE);
       intent1.putExtra("Receta", receta);
       intent1.putExtra("idNotificacion", idNotificacion);
       intent2.putExtra("Receta", receta);
       intent2.putExtra("idNotificacion", idNotificacion);
       // Se action to window
       normal.putExtra("Receta", receta);
       normal.putExtra("idNotificacion", idNotificacion);
       //End Action in window
       PendingIntent pending = PendingIntent.getBroadcast(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
       PendingIntent pending2 = PendingIntent.getBroadcast(this, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);// PendingIntent.FLAG_CANCEL_CURRENT

       Builder mBuilder = new Notification.Builder(this)
    	        .setSmallIcon(R.drawable.ic_launcher)
    	        .setContentTitle("It's time to take your meds!")
    	        .setContentText("Medikit")
    	        .setDefaults(Notification.DEFAULT_ALL)
    	        .addAction(R.drawable.ic_launcher, "Posponer", pending2)
    	        .addAction(R.drawable.ic_launcher,"Aceptar", pending)
    	        .setAutoCancel(true);
       
       Notification.InboxStyle inboxStyle = new Notification.InboxStyle(mBuilder);
       inboxStyle.setBigContentTitle("It's Time to Take Your Meds!");
       for (int i = 0; i < events.length; i++) {  
           inboxStyle.addLine(events[i]);  
       }  
       mBuilder.setStyle(inboxStyle);
       
	   TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	   stackBuilder.addParentStack(ActivityAgregarReceta.class);
	   stackBuilder.addNextIntent(normal);
	   PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            Intent.FLAG_ACTIVITY_NEW_TASK
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(idNotificacion, mBuilder.build());
    }

    @Override
    public void onDestroy() 
    {
        super.onDestroy();
    }
    


}