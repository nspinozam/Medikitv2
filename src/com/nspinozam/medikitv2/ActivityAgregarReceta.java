package com.nspinozam.medikitv2;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import notification.MyReceiver;
import database.Core;
import models.Medicamento;
import models.Presentacion;
import models.Receta;
import models.Usuario;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

//TODO Si se cambia la orientación se "borran" los datos

public class ActivityAgregarReceta extends Activity{
	Button btn_nombre;
	Button btn_presentacion;
	Button btn_horaI;
	Button btn_fechaI;
	EditText cantidad_dosis;
	EditText et_cantidad_dias;
	EditText et_veces_dia;
	EditText et_duracion;
	EditText et_nota;
	EditText GLOBAL;
	boolean actualizar;
	LinearLayout mainLayout;
	Activity activity;
	Receta receta;
	Context ctx;
	PendingIntent pendingIntent;
	Utilities U = new Utilities();
	long idReceta = 0;
	ArrayList idHoras = new ArrayList();
	private Core core = new Core();
	private ArrayList horasReceta  = new ArrayList();;
	public Bundle saved;
	private int horas, minutos;
	Calendar now_calendar = Calendar.getInstance();
	
	public static Medicamento medicamento;
	public static Presentacion presentacion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_receta_n);
		this.getWindow().setSoftInputMode(
			    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		activity = this;
		saved = savedInstanceState;
		//TODO probar con medicamento=null, para quitar que cuando se va acrear uno nuevo, le salga el último por defecto
		ctx = this;
		//Elementos de UI
        btn_nombre = (Button)findViewById(R.id.btn_n);
        btn_presentacion = (Button)findViewById(R.id.btn_presentacion);
        btn_horaI = (Button)findViewById(R.id.btn_hora_inicio);
        btn_fechaI = (Button)findViewById(R.id.btn_fecha_inicio);
        et_cantidad_dias = (EditText)findViewById(R.id.et_cantidad_dias);
        et_veces_dia = (EditText)findViewById(R.id.et_veces_dia);
        et_nota = (EditText)findViewById(R.id.et_nota);
        et_duracion = (EditText)findViewById(R.id.et_cantidad_tiempo);
        cantidad_dosis = (EditText)findViewById(R.id.et_cantidad);
        
        btn_nombre.setOnClickListener(ocNombre);
        btn_presentacion.setOnClickListener(ocPresentacion);
        btn_fechaI.setOnClickListener(ocFecha);
        btn_horaI.setOnClickListener(ocHora);
       
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		try {
			btn_nombre.setText(medicamento.toString());
			btn_presentacion.setText(presentacion.toString());
		} catch (Exception e) {
		}
		
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		medicamento = null;
		presentacion = null;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.activity_agregar_receta, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.save_receta) {
			int ok = validarNulos();
			if(ok == 0){}
			else if(ok==1){
				Toast.makeText(ctx, "La duración no puede ser menor al lapso de tiempo", Toast.LENGTH_LONG).show();
			} else{
				Toast.makeText(ctx, "No se permiten espacios vacíos", Toast.LENGTH_LONG).show();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//////////////////  Button Actions   //////////////////////
	//Boton Nombre
	private OnClickListener ocNombre = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(ctx, ActivityListMedicamento.class);
			startActivity(i);
		}
	};
	
	//Boton Presentacion
	private OnClickListener ocPresentacion = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(ctx, ActivityListPresentacion.class);
			startActivity(i);
		}
	};
	
	//Boton Fecha Inicio
	private OnClickListener ocFecha = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			showDatePickerDialog();
		}
	};
	
	//Boton Hora Inicio
	private OnClickListener ocHora = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			showTimePickerDialog();
		}
	};

	//Si ningún espacio está vacío, se crea la instacia de la receta y se retorna true
	@SuppressWarnings("rawtypes")
	public int validarNulos(){
		int idUsuario = ActivityListMain.id;
		if(!btn_nombre.getText().toString().equals("") && !btn_fechaI.getText().toString().equals("") 
				&& !btn_horaI.getText().toString().equals("")&& !btn_presentacion.getText().toString().equals("")
				&& !cantidad_dosis.getText().toString().equals("") && !et_cantidad_dias.getText().toString().equals("")
				&& !et_veces_dia.getText().toString().equals("") && !et_duracion.getText().toString().equals("")){
			int cantidadConsumo = Integer.valueOf(cantidad_dosis.getText().toString());
			String fechaI = btn_fechaI.getText().toString();
			String HoraI = btn_horaI.getText().toString();
			int duracionDias = Integer.valueOf(et_duracion.getText().toString());
			int cadaDias = Integer.valueOf(et_cantidad_dias.getText().toString());
			int vecesDia = Integer.valueOf(et_veces_dia.getText().toString());
			String nota = et_nota.getText().toString();
			if(duracionDias>=cadaDias){
				receta = new Receta(idUsuario, medicamento.idMedicamento, cantidadConsumo, presentacion.idPresentacion,
						fechaI, HoraI, duracionDias, cadaDias, vecesDia, nota);
				ArrayList data = calculoHoras(vecesDia, HoraI);
				onCreateDialog(saved,data);
				return 0;
			}
			else{
				return 1;
			}
		}else{
			return 9;
		}
		
	}
	//Crea la receta
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList calculoHoras(int vecesDia, String horaI) {
		ArrayList temp = new ArrayList();
		int hora;
		Date date = null;
		String horaInicio = horaI;
		ArrayList array = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.US);
		String[] horaSplit = horaInicio.split(" ");
		Log.i("HoraSplit", horaSplit.toString());
		horaInicio = horaSplit[0]+" "+horaSplit[1];
		hora = 24/vecesDia; 
		Calendar calendar = Calendar.getInstance();
		try {
			date = sdf.parse(horaInicio);
			calendar.setTime(date);
			temp.add(1);
			temp.add(sdf.format(date));
			array.add(temp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		for (int i = 1; i < vecesDia; i++) {
				calendar.add(Calendar.HOUR, hora);
				ArrayList temp2 = new ArrayList();
				temp2.add(i+1);
				temp2.add(sdf.format(calendar.getTime()));
				array.add(temp2);
			}
		return array;
	}

	///////////////// PICKERS //////////////////////
	public void showTimePickerDialog() {
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
    	TimePickerDialog timePickerDialog = new TimePickerDialog(ctx, methodTimePickerDialog, hour, minute, false); 
    	timePickerDialog.show();
    	
    }
	
	private OnTimeSetListener methodTimePickerDialog = new OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				String minuto;
	            String horario;
	            if (hourOfDay > 11) {
	            	horario = "PM";
	            } 
	            else {
	            	horario = "AM";
	            }
	        	if (minute < 10) {
	        		minuto = "0" + minute;
	        	} 
	        	else {
	        		minuto = "" + minute;
	        	}
	        	if(GLOBAL==null){
	        		btn_horaI.setText(U.horas(hourOfDay) + ":" + minuto + " " + horario);
	        	}
	        	else{
	        		GLOBAL.setText(U.horas(hourOfDay) + ":" + minuto + " " + horario);
	        		GLOBAL=null;
	        	}
			}
		};
	
	public void showDatePickerDialog() {
		final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
    	DatePickerDialog datePickerDialog = new DatePickerDialog(ctx, methodDatePickerDialog, year, month, day); 
    	datePickerDialog.show();
    }
	
	private OnDateSetListener methodDatePickerDialog = new OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			//btn_fechaI.setText(String.valueOf(dayOfMonth)+"-"+U.NombreMes(monthOfYear)+"-"+year);
			btn_fechaI.setText(String.valueOf(dayOfMonth)+"-"+(monthOfYear+1)+"-"+year);
		}
	};
	
	////////////////////// CREACIÓN DEL DIALOGO ///////////////////////
	@SuppressWarnings("rawtypes")
	public AlertDialog onCreateDialog(Bundle savedInstanceState, final ArrayList array) {	
	    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	    LayoutInflater inflater = activity.getLayoutInflater();
	    final View view = inflater.inflate(R.layout.dialog_addhours, null);
	    mainLayout = (LinearLayout)view.findViewById(R.id.dialog_layout);
	    builder.setView(view)
	           .setPositiveButton(R.string.agregar, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {}})
	               
	           .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id){
	            	   dialog.dismiss();
	            	   onResume();
	               }
	           });
	    final int N = array.size();
		final EditText[] myTextViews = new EditText[N]; 
		for (int i = 0; i < N; i++) {
			ArrayList contenedor = (ArrayList) array.get(i);
		    final EditText rowTextView = new EditText(this);
		    rowTextView.setText(contenedor.get(1).toString());
		    rowTextView.setFocusable(false);
		    rowTextView.requestFocus();
		    rowTextView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					GLOBAL = rowTextView;
					showTimePickerDialog();	
				}
			});
		    mainLayout.addView(rowTextView);
		    myTextViews[i] = rowTextView;
		}
	    //Se construye el Dialog
	    final AlertDialog dialog = builder.create();
	    dialog.setTitle("Define las horas de notificación");
	    dialog.show();
	    // Se sobre escribe la acción del onclick para mantener el Dialog si hay error
	    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
	          {            
	              @SuppressWarnings("unchecked")
				@Override
	              public void onClick(View v)
	              {
	                  Boolean wantToCloseDialog = true;
	                  for (int i = 0; i < myTextViews.length; i++) {
	                	  ArrayList contenedor = (ArrayList) array.get(i);
	                	  contenedor.remove(1);
	                	  contenedor.add(myTextViews[i].getText().toString());
					}
	                  if(wantToCloseDialog){
	                	  horasReceta = array;
	                      agregarDB();
	                      Calendar recipe_calendar = Calendar.getInstance();
	                      receta.nombreMedicina = medicamento.nombreComercial;
	                      for (int i = 0; i < horasReceta.size(); i++) {
	                    	  Log.i("For agregar", "el i = "+i);
	                    	  recipe_calendar = configurarHorasN(recipe_calendar, (ArrayList)horasReceta.get(i));
		                      Intent myIntent = new Intent(ActivityAgregarReceta.this, MyReceiver.class);
		          		      myIntent.putExtra("Receta", receta);
		          		      long idH = (Long) idHoras.get(i);
		          		      myIntent.putExtra("idNotificacion", (int)idH);
		          			  pendingIntent = PendingIntent.getBroadcast(ActivityAgregarReceta.this, (int)idH , myIntent, PendingIntent.FLAG_ONE_SHOT);
		          		      AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		          		      alarmManager.set(AlarmManager.RTC, recipe_calendar.getTimeInMillis(), pendingIntent);
						}
	          		   	  // FINISH ALARM CODE
	          		     dialog.dismiss();
	          			 activity.finish();	
	                  }
	              }
	          });
	   
	    return dialog;
	}
	
	private Calendar configurarHorasN(Calendar recipe_calendar, ArrayList horaInicio) {
		Log.i("Array", horaInicio.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.US);
		try {
			String[] horaSplit = ((String)horaInicio.get(1)).split(" ");
			String horaInicio2 = horaSplit[0]+" "+horaSplit[1];
			Date date_now = new Date();
			Date date_recipe = sdf.parse(horaInicio2);
			date_recipe.setMonth(now_calendar.get(Calendar.MONTH));
			date_recipe.setDate(now_calendar.get(Calendar.DAY_OF_MONTH));
			date_recipe.setYear(now_calendar.get(Calendar.YEAR)-1900);
			recipe_calendar.setTime(date_recipe);
			
			if(date_recipe.after(date_now)){
				recipe_calendar.setTime(date_recipe);
			}
			else{
				date_recipe.setDate(now_calendar.get(Calendar.DAY_OF_MONTH)+1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return recipe_calendar;
	}
	
	public void agregarDB(){
		idReceta = core.agregarReceta(receta, ctx);
		idHoras = core.agregarHorarios(idReceta, horasReceta, ctx);
		String nombreConsumo = core.consultarNombreC(receta.idTipoConsumo, ctx);
		String nombrePaciente = core.consultarNombreP(receta.idUsuario, ctx);
		String nombreMedicina = core.consultarNombreM(receta.idMedicina, ctx);
		Receta treceta = new Receta((int)idReceta, receta.fechaI, receta.duracionDias, nombreConsumo, nombrePaciente, receta.cantidadConsumo, nombreMedicina);
		receta = treceta;
		activity.finish();
	}
	
	
}
