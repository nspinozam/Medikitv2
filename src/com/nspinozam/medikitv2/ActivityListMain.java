package com.nspinozam.medikitv2;

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
	
	Context ctx;
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_user);
		ctx = this;
		activity = this;
		ActivityListUser.BACK = true;
		SharedPreferences prefs =
			     getSharedPreferences("MedikitPreferences",Context.MODE_PRIVATE);
			 
			int id = prefs.getInt("IdUsuario", 6);
			Log.i("id",String.valueOf(id));
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
		//TODO acción para refrescar la lista
	}

}
