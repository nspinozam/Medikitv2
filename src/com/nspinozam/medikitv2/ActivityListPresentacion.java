package com.nspinozam.medikitv2;

import java.util.ArrayList;

import models.Medicamento;
import models.Presentacion;
import models.Usuario;
import database.Core;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

@SuppressLint("ParcelCreator")
public class ActivityListPresentacion extends Activity implements Parcelable{
	public Context ctx;
	Activity activity;
	public Bundle saved;
	ActionMode mMode;
	int index = 0;
	public Core core;
	ArrayList<Presentacion> listaPresentacion = new ArrayList<Presentacion>();
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		saved = savedInstanceState;
		setContentView(R.layout.activity_list_user);
		core = new Core();
		ctx = this;
		activity = this;
		list = (ListView) findViewById(R.id.drugsListView);  
		
		//Cargar lista de pacientes
		listaPresentacion = core.PresentacionesList(ctx);
		ArrayAdapter<Presentacion> adapter = new ArrayAdapter<Presentacion>(this,
                android.R.layout.simple_list_item_1, listaPresentacion);
		list.setAdapter(adapter);
		list.setOnItemClickListener(onClick);
        list.setOnItemLongClickListener(onLongClick);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_list_receta, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.add_newreceta) {
			//Agregar usuario
			AlertDialog newPres = onCreateDialog(saved,1,null);
			newPres.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public AlertDialog onCreateDialog(Bundle savedInstanceState, final int accion, final Presentacion presD) {	
	    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	    LayoutInflater inflater = activity.getLayoutInflater();
	    final View view = inflater.inflate(R.layout.dialog_addpresentacion, null);
	    // Se agrega la vista al dialogo
	    builder.setView(view)
	    //Se agregan los botones
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
	    //Se construye el Dialog
	    final AlertDialog dialog = builder.create();
	    final EditText tv_nuevoNombre;
	    final EditText tv_nuevaNota;
	    if(accion == 1){
	    	dialog.setTitle("Agrega una nueva Presentación");
		    dialog.show();
		    tv_nuevoNombre = (EditText)view.findViewById(R.id.dialog_addpresentacion_name);
		    tv_nuevaNota = (EditText)view.findViewById(R.id.dialog_addpresentacion_nota);
	    }
	    else{
	    	dialog.setTitle("Modifica la Presentación");
		    dialog.show();
		    tv_nuevoNombre = (EditText)view.findViewById(R.id.dialog_addpresentacion_name);
		    tv_nuevaNota = (EditText)view.findViewById(R.id.dialog_addpresentacion_nota);
		    tv_nuevoNombre.setText(presD.nombrePresentacion);
		    tv_nuevaNota.setText(presD.notaPresentacion);
	    }
	    
	    // Se sobre escribe la acci�n del onclick para mantener el Dialog si hay error
	    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
	          {            
	              @Override
	              public void onClick(View v)
	              {
	                  Boolean wantToCloseDialog = false;
	                //TODO Agregar un hilo
	                  //Acci�n del bot�n aceptar
	            	  
	                  tv_nuevoNombre.addTextChangedListener(new TextWatcher() {
	            		    @Override
	            		    public void onTextChanged(CharSequence s, int start, int before, int count) {
	            		    	if (tv_nuevoNombre.getText().toString().length() <= 0)
	            		    		tv_nuevoNombre.setError(null);
	            		    }
	            		    @Override
	            		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	            		    	tv_nuevoNombre.setError(null);
	            		    }
	            		    @Override
	            		    public void afterTextChanged(Editable s) {
	            		    if (tv_nuevoNombre.getText().toString().length() <= 0)
	            		    	tv_nuevoNombre.setError("Agrega el nombre del Medicamento");
	            		    }
	            		 });
	                 
	                  
	                   final String nombrePres = tv_nuevoNombre.getText().toString();
	                   final String notaPres = tv_nuevaNota.getText().toString();
	                   long res=0;
	                   if(!nombrePres.equals("")){
	                	   String resTexto = "";
	                	   String resTexto2 = "";
	                	   switch (accion) {
						case 1:
							Presentacion pres = new Presentacion(nombrePres, notaPres);
		                	res = core.agregarPresentacion(pres, ctx);
							resTexto = "agregada";
							resTexto2 = "agregar";
							pres.idPresentacion = (int)res;
							ActivityAgregarReceta.presentacion = pres;
							ActivityVerEditarReceta.presentacion = pres;
							break;
						case 2:
							presD.nombrePresentacion=nombrePres;
							presD.notaPresentacion=notaPres;
							res = core.ActualizarPresentacion(presD, ctx);
							resTexto = "modificado";
							resTexto2 = "modificar";
							ActivityAgregarReceta.presentacion = presD;
							ActivityVerEditarReceta.presentacion = presD;
							break;
						}
                	   //verificar error
                	   if(res >= 0){
                		   wantToCloseDialog = true;
                		   Toast.makeText(ctx, "Presentación "+ resTexto+" con éxito", Toast.LENGTH_LONG).show();
                		   activity.finish();
                		   //onResume();
                	   }
                	   else{
                		   Toast.makeText(ctx, "No se pudo "+ resTexto2+ "la Presentación", Toast.LENGTH_LONG).show();
                		   activity.finish();
                		   //onResume();
                	   }
	                   }else{
	                	   tv_nuevoNombre.setError("Este espacio no puede estar vacío");
	                   }
	                  if(wantToCloseDialog)
	                      dialog.dismiss();
	              }
	          });
	    return dialog;
	}

	@Override
	public void onResume(){
		super.onResume();
		core = new Core();
		ctx = this;
		activity = this;
		list = (ListView) findViewById(R.id.drugsListView);  
		
		//Cargar lista de pacientes
		listaPresentacion = core.PresentacionesList(ctx);
		ArrayAdapter<Presentacion> adapter = new ArrayAdapter<Presentacion>(this,
                android.R.layout.simple_list_item_1, listaPresentacion);
		list.setAdapter(adapter);
		list.setOnItemClickListener(onClick);
	}
	
	private OnItemClickListener onClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Presentacion pres = listaPresentacion.get(arg2);
			ActivityAgregarReceta.presentacion = pres;
			ActivityVerEditarReceta.presentacion = pres;
			activity.finish();
		}
	};
	
	
	private OnItemLongClickListener onLongClick = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if(mMode!=null)
				return false;
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
				AlertDialog.Builder alertDialogB = new AlertDialog.Builder(ctx);
				alertDialogB.setTitle("Eliminar Presentación");
				alertDialogB.setMessage("Realmente desea eliminar la presentación?")
				.setCancelable(false)
				.setPositiveButton("Si",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						int resT = borrarPresentacion(index);
						if(resT>-1){
							Toast.makeText(getApplicationContext(), "Presentación eliminada con éxito!", Toast.LENGTH_SHORT).show();
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
				res = modificarPresentacion(index);
				if(res>-1){
					Toast.makeText(getApplicationContext(), "Presentación modificada con éxito!", Toast.LENGTH_SHORT).show();
				}
				mode.finish();
				return true;
			}
			return false;
		}
	};
	
	/**
	 * Funci�n para borrar el paciente
	 * @param index
	 */
	private int borrarPresentacion(int index) {
		//res = resultado de la operaci�n-1 == error
		int res = -1;
		Presentacion pres = listaPresentacion.get(index);							
		res = core.EliminarPresentacion(pres, ctx);
		
		Intent intent = getIntent();
	    overridePendingTransition(0, 0);
	    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    finish();
	    overridePendingTransition(0, 0);
	    startActivity(intent);
	    return res;
	}
	

	private int modificarPresentacion(int index) {
		//res = resultado de la operaci�n-1 == error
		int res = -1;
		Presentacion pres = listaPresentacion.get(index);
		AlertDialog newUser = onCreateDialog(saved,2,pres);
		newUser.show();
		
		
	    return res;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
	}

}
