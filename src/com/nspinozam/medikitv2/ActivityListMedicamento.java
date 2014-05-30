package com.nspinozam.medikitv2;

import java.util.ArrayList;

import models.Medicamento;
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
public class ActivityListMedicamento extends Activity implements Parcelable{
	public Context ctx;
	Activity activity;
	public Bundle saved;
	ActionMode mMode;
	int index = 0;
	public Core core;
	ArrayList<Medicamento> listaMedicamentos = new ArrayList<Medicamento>();
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
		listaMedicamentos = core.MedicamentosList(ctx);
		ArrayAdapter<Medicamento> adapter = new ArrayAdapter<Medicamento>(this,
                android.R.layout.simple_list_item_1, listaMedicamentos);
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
			AlertDialog newUser = onCreateDialog(saved,1,null);
			newUser.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public AlertDialog onCreateDialog(Bundle savedInstanceState, final int accion, final Medicamento medD) {	
	    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	    LayoutInflater inflater = activity.getLayoutInflater();
	    final View view = inflater.inflate(R.layout.dialog_addmedicamento, null);
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
	    final EditText tv_nuevoNombreComercial;
	    final EditText tv_nuevoNombreGenerico;
	    if(accion == 1){
	    	dialog.setTitle("Agrega un nuevo Medicamento");
		    dialog.show();
		    tv_nuevoNombreComercial = (EditText)view.findViewById(R.id.dialog_addmedicamento_name);
		    tv_nuevoNombreGenerico = (EditText)view.findViewById(R.id.dialog_addmedicamento_nombre_generico);
	    }
	    else{
	    	dialog.setTitle("Modifica el Medicamento");
		    dialog.show();
		    tv_nuevoNombreComercial = (EditText)view.findViewById(R.id.dialog_addmedicamento_name);
		    tv_nuevoNombreGenerico = (EditText)view.findViewById(R.id.dialog_addmedicamento_nombre_generico);
		    tv_nuevoNombreComercial.setText(medD.nombreComercial);
		    tv_nuevoNombreGenerico.setText(medD.nombreGenerico);
	    }
	    
	    // Se sobre escribe la acción del onclick para mantener el Dialog si hay error
	    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
	          {            
	              @Override
	              public void onClick(View v)
	              {
	                  Boolean wantToCloseDialog = false;
	                //TODO Agregar un hilo
	                  //Acción del botón aceptar
	            	  
	                  tv_nuevoNombreComercial.addTextChangedListener(new TextWatcher() {
	            		    @Override
	            		    public void onTextChanged(CharSequence s, int start, int before, int count) {
	            		    	if (tv_nuevoNombreComercial.getText().toString().length() <= 0)
	            		    		tv_nuevoNombreComercial.setError(null);
	            		    }
	            		    @Override
	            		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	            		    	tv_nuevoNombreComercial.setError(null);
	            		    }
	            		    @Override
	            		    public void afterTextChanged(Editable s) {
	            		    if (tv_nuevoNombreComercial.getText().toString().length() <= 0)
	            		    	tv_nuevoNombreComercial.setError("Agrega el nombre del Medicamento");
	            		    }
	            		 });
	                  
	                  tv_nuevoNombreGenerico.addTextChangedListener(new TextWatcher() {
	            		    @Override
	            		    public void onTextChanged(CharSequence s, int start, int before, int count) {
	            		    	if (tv_nuevoNombreGenerico.getText().toString().length() <= 0)
	            		    		tv_nuevoNombreGenerico.setError(null);
	            		    }
	            		    @Override
	            		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	            		    	tv_nuevoNombreGenerico.setError(null);
	            		    }
	            		    @Override
	            		    public void afterTextChanged(Editable s) {
	            		    if (tv_nuevoNombreGenerico.getText().toString().length() <= 0)
	            		    	tv_nuevoNombreGenerico.setError("Agrega el nombre del Medicamento");
	            		    }
	            		 });
	                  
	                   final String nombreComercial = tv_nuevoNombreComercial.getText().toString();
	                   final String nombreGenerico = tv_nuevoNombreGenerico.getText().toString();
	                   long res=0;
	                   if(!nombreComercial.equals("") && !nombreGenerico.equals("")){
	                	   String resTexto = "";
	                	   String resTexto2 = "";
	                	   switch (accion) {
						case 1:
							Medicamento medic = new Medicamento(nombreComercial, nombreGenerico);
		                	res = core.agregarMedicamento(medic, ctx);
							resTexto = "agregado";
							resTexto2 = "agregar";
							ActivityAgregarReceta.medicamento = medic;
							break;
						case 2:
							medD.nombreComercial=nombreComercial;
							medD.nombreGenerico=nombreGenerico;
							res = core.ActualizarMedicamento(medD, ctx);
							resTexto = "modificado";
							resTexto2 = "modificar";
							ActivityAgregarReceta.medicamento = medD;
							break;
						}
                	   //verificar error
                	   if(res >= 0){
                		   wantToCloseDialog = true;
                		   Toast.makeText(ctx, "Medicamento "+ resTexto+" con éxito", Toast.LENGTH_LONG).show();
                		   activity.finish();
                		   //onResume();
                	   }
                	   else{
                		   Toast.makeText(ctx, "No se pudo "+ resTexto2+ "el Medicamento", Toast.LENGTH_LONG).show();
                		   activity.finish();
                		   //onResume();
                	   }
	                   }else{
	                	   tv_nuevoNombreComercial.setError("Este espacio no puede estar vacío");
	                	   tv_nuevoNombreGenerico.setError("Este espacio no puede estar vacío");
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
		listaMedicamentos = core.MedicamentosList(ctx);
		ArrayAdapter<Medicamento> adapter = new ArrayAdapter<Medicamento>(this,
                android.R.layout.simple_list_item_1, listaMedicamentos);
		list.setAdapter(adapter);
		list.setOnItemClickListener(onClick);
        //list.setOnItemLongClickListener(onLongClick);
	}
	
	private OnItemClickListener onClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Medicamento med = listaMedicamentos.get(arg2);
			ActivityAgregarReceta.medicamento = med;
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
				res = borrarUsuario(index);
				if(res>-1){
					Toast.makeText(getApplicationContext(), "Medicamento eliminado con éxito!", Toast.LENGTH_SHORT).show();
				}
				mode.finish();
				return true;
			case R.id.modificar_usuario_context:
				res = modificarUsuario(index);
				if(res>-1){
					Toast.makeText(getApplicationContext(), "Medicamento modificado con éxito!", Toast.LENGTH_SHORT).show();
				}
				mode.finish();
				return true;
			}
			return false;
		}
	};
	
	/**
	 * Función para borrar el paciente
	 * @param index
	 */
	private int borrarUsuario(int index) {
		//res = resultado de la operación-1 == error
		int res = -1;
		Medicamento med = listaMedicamentos.get(index);							
		res = core.EliminarMedicamento(med, ctx);
		
		Intent intent = getIntent();
	    overridePendingTransition(0, 0);
	    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    finish();
	    overridePendingTransition(0, 0);
	    startActivity(intent);
	    return res;
	}
	

	private int modificarUsuario(int index) {
		//res = resultado de la operación-1 == error
		int res = -1;
		Medicamento usuario = listaMedicamentos.get(index);
		AlertDialog newUser = onCreateDialog(saved,2,usuario);
		newUser.show();
		
		
	    return res;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
