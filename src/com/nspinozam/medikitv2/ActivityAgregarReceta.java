package com.nspinozam.medikitv2;


import database.Core;
import models.Medicamento;
import models.Usuario;
import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ActivityAgregarReceta extends Activity{
	Button btn_nombre;
	Button btn_presentacion;
	boolean actualizar;
	Context ctx;
	
	public static Medicamento medicamento;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_receta_n);
		ctx = this;
		//Elementos de UI
        btn_nombre = (Button)findViewById(R.id.btn_n);
        btn_presentacion = (Button)findViewById(R.id.btn_presentacion);
        btn_nombre.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(ctx, ActivityListMedicamento.class);
				startActivity(i);
			}
		});
        
        btn_presentacion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ctx, ActivityListPresentacion.class);
				startActivity(i);
			}
		});
       
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		try {
			btn_nombre.setText(medicamento.toString());
		} catch (Exception e) {
			
		}
		
	}
	

}
