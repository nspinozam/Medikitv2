package com.nspinozam.medikitv2;


import java.util.Calendar;

import database.Core;
import models.Medicamento;
import models.Presentacion;
import models.Receta;
import models.Usuario;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
	boolean actualizar;
	Receta receta;
	Context ctx;
	Utilities U = new Utilities();
	private int horas, minutos;
	
	public static Medicamento medicamento;
	public static Presentacion presentacion;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_receta_n);
		this.getWindow().setSoftInputMode(
			    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
			if(ok==0){
				Toast.makeText(ctx, "ok", Toast.LENGTH_LONG).show();
			} else if(ok==1){
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
	public int validarNulos(){
		int idUsuario = ActivityListMain.prefs.getInt("IdUsuario", 9999);
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
	
	
	///////////////// PICKERS //////////////////////
	public void showTimePickerDialog() {
    	TimePickerDialog timePickerDialog = new TimePickerDialog(ctx, methodTimePickerDialog, horas, minutos, false); 
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
	        	btn_horaI.setText(U.horas(hourOfDay) + " : " + minuto + " " + horario);
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
	
	
	
}
