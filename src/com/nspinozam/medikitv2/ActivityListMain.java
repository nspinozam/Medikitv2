package com.nspinozam.medikitv2;

import java.util.ArrayList;

import models.Receta;
import models.Usuario;
import database.Core;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityListMain extends Activity{
	public Core core;
	Context ctx;
	Activity activity;
	ListView list;
	int id;
	ArrayList<Receta> listaRecetas = new ArrayList<Receta>();//TODO revisar si hay que hacer una clase nueva para agregar las recetas o con la existente ya basta
	public static SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_user);
		ctx = this;
		core = new Core();
		activity = this;
		ActivityListUser.BACK = true;
		list = (ListView) findViewById(R.id.drugsListView); 
		prefs = getSharedPreferences("MedikitPreferences",Context.MODE_PRIVATE);
		id = prefs.getInt("IdUsuario", 9999);
		Log.i("id",String.valueOf(id));
		//Cargar lista de pacientes
		listaRecetas = core.RecetaListInicial(id,ctx);
		/*ArrayAdapter<Receta> adapter = new ArrayAdapter<Receta>(this,
                android.R.layout.simple_list_item_1, listaRecetas);
		list.setAdapter(adapter);*/
		Log.i("RecetaList", listaRecetas.toString());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.activity_list_receta, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.add_newreceta) {
			//Agregar receta
			Intent toAgregarReceta = new Intent(this, ActivityAgregarReceta.class);
			startActivity(toAgregarReceta);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
    public void onDestroy() {
        super.onDestroy();
        ActivityListUser.BACK = false;
    }
	

	@Override
	public void onResume(){
		super.onResume();
		core = new Core();
		ctx = this;
		activity = this;
		list = (ListView) findViewById(R.id.drugsListView);
		
		//Cargar lista de pacientes
		listaRecetas = core.RecetaListInicial(id,ctx);
		/*ArrayAdapter<Receta> adapter = new ArrayAdapter<Receta>(this,
                android.R.layout.simple_list_item_1, listaRecetas);
		list.setAdapter(adapter);*/
		//Log.i("RecetaList", listaRecetas.toString());
		//list.setOnItemClickListener(onClick);
	}

}
