package com.nspinozam.medikitv2;

import java.util.ArrayList;

import models.Medicamento;
import models.Receta;
import models.Usuario;
import database.Core;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ActivityListMain extends Activity{
	public Core core;
	Context ctx;
	Activity activity;
	ListView list;
	public static int id;
	ActionMode mMode;
	ActionBar actionBar;
	int index = 0;
	ArrayList<String> listaRecetasStr = new ArrayList<String>();
	ArrayList listaRecetas = new ArrayList();//TODO revisar si hay que hacer una clase nueva para agregar las recetas o con la existente ya basta
	public static SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_user);
		ctx = this;
		core = new Core();
		activity = this;
		actionBar = getActionBar();
		ActivityListUser.BACK = true;
		list = (ListView) findViewById(R.id.drugsListView); 
		prefs = getSharedPreferences("MedikitPreferences",Context.MODE_PRIVATE);
		id = prefs.getInt("IdUsuario", 9999);
		//Cargar lista de pacientes
		listaRecetas = core.RecetaListInicial(id,ctx);
		listaRecetasStr=crearArray(listaRecetas);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listaRecetasStr);
		list.setAdapter(adapter);
		String name = core.consultarNombreP(id, ctx);
		actionBar.setTitle(name);
	}
	
	private ArrayList<String> crearArray(ArrayList lista) {
		ArrayList<String>retorno = new ArrayList<String>();
		for (int i = 0; i < lista.size(); i++) {
			ArrayList temp = (ArrayList) lista.get(i);
			String str = temp.get(3).toString()+": "+temp.get(1).toString() + " "+temp.get(2).toString();
			retorno.add(str);
		}
		return retorno;
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
		
		listaRecetas = core.RecetaListInicial(id,ctx);
		listaRecetasStr=crearArray(listaRecetas);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listaRecetasStr);
		list.setAdapter(adapter);
		list.setOnItemClickListener(onClick);
		list.setOnItemLongClickListener(onLongClick);
	}
	
	private OnItemClickListener onClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			index = arg2;
			ArrayList arr = (ArrayList) listaRecetas.get(arg2);
			Intent i = new Intent(ctx, ActivityVerEditarReceta.class);
			int idR = (Integer) ((ArrayList)listaRecetas.get(index)).get(0);
			i.putExtra("idReceta", (long)idR);
			i.putExtra("accion", 0);
			startActivity(i);
		}
	};
	
	
	private OnItemLongClickListener onLongClick = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if(mMode!=null) {
				return false;
			}
			else {	
				index = arg2;
				mMode = startActionMode(mCallback);				
			}
			return true;
		}
	};
	
private ActionMode.Callback mCallback = new ActionMode.Callback() {
		
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return true;
		}
		
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mMode = null;
		}
		
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			mode.setTitle("1 Selected");
			getMenuInflater().inflate(R.menu.usuario_context_menu, menu);
			return true;
		}
				
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			int res = -1;
			switch(item.getItemId()) {
			case R.id.borrar_usuario_context:
				final int idx = (Integer) ((ArrayList)listaRecetas.get(index)).get(0);
				AlertDialog.Builder alertDialogB = new AlertDialog.Builder(ctx);
				alertDialogB.setTitle("Eliminar Receta");
				alertDialogB.setMessage("Realmente desea eliminar la receta?")
				.setCancelable(false)
				.setPositiveButton("Si",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int idY) {
						int resT = core.EliminarReceta(idx, ctx);
						refresh();
						if(resT>-1){
							Toast.makeText(getApplicationContext(), "Receta eliminada con éxito!", Toast.LENGTH_SHORT).show();
						}
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
				AlertDialog alertDialog = alertDialogB.create();
				alertDialog.show();
				mode.finish();
				return true;
			case R.id.modificar_usuario_context:
				ArrayList arr = (ArrayList) listaRecetas.get(index);
				Intent i = new Intent(ctx, ActivityVerEditarReceta.class);
				int idR = (Integer) ((ArrayList)listaRecetas.get(index)).get(0);
				i.putExtra("idReceta", (long)idR);
				i.putExtra("accion", 1);
				mode.finish();
				startActivity(i);
				
				return true;
			}
			return false;
		}
	};
	public void refresh(){
		Intent intent = getIntent();
	    overridePendingTransition(0, 0);
	    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    finish();
	    overridePendingTransition(0, 0);
	    startActivity(intent);
	}

}
