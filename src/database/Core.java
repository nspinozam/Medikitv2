package database;

import java.util.ArrayList;

import models.Medicamento;
import models.Presentacion;
import models.Usuario;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaExtractor;

public class Core {

	Resources res;
	public Core() {

	}
	
	/**
	 * Funcion para agregar el usuario a la base de datos.
	 * @param user instancia del usuario
	 * @param context contexto de la aplicación
	 * @return
	 */
	public long agregarUsuario(Usuario user,Context context){
		long id = 0;
		//res = context.getResources();
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		if (db != null) {
			try {
				ContentValues nuevoRegistro = new ContentValues();
				nuevoRegistro.put("nombre", user.nombreUsuario);
				id = db.insert("Usuario	", null, nuevoRegistro);
			} catch (SQLException e) {
				//retorno el error
				return -1;
			}
		}
		db.close();
		return id;
	}
	
	/**
	 * Funcion para actualizar un usuario
	 */
	public int ActualizarUsuario(Usuario user, Context context){
		int res = 0;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("nombre",user.nombreUsuario);
		String[] whereArgs = new String[]{String.valueOf(user.idUsuario)};
		if(db!=null){
			try{
				db.update("Usuario", values, "idUser=?", whereArgs);
				res = 1;
			}catch(SQLException e){
				return -1;
			}
		}
		return res;
	}
	
	/**
	 * Funcion para actualizar un usuario
	 */
	public int EliminarUsuario(Usuario user, Context context){
		int res = 0;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		String[] whereArgs = new String[]{String.valueOf(user.idUsuario)};
		if(db!=null){
			try{
				db.delete("Usuario","idUser=?", whereArgs);
				res = 1;
			}catch(SQLException e){
				return -1;
			}
		}
		return res;
	}
	
	/**
	 * Funcion para agregar un nuevo medicamento a la base de datos
	 * @param medicamento instancia del medicamento
	 * @param context contexto de la aplicación
	 * @return
	 */
	public long agregarMedicamento(Medicamento medicamento,Context context){
		long id = 0;
		res = context.getResources();
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		if (db != null) {
			try {
				ContentValues nuevoRegistro = new ContentValues();
				nuevoRegistro.put("nombreComercial", medicamento.nombreComercial);
				nuevoRegistro.put("nombreGenerico", medicamento.nombreGenerico);
				id = db.insert("Medicamento	", null, nuevoRegistro);
			} catch (SQLException e) {
				//retorno el error
				return -1;
			}
		}
		db.close();
		return id;
	}
	
	/**
	 * Función para modificar los medicamentos
	 */
	public int ActualizarMedicamento(Medicamento medicamento, Context context){
		int res = 0;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("nombreComercial",medicamento.nombreComercial);
		values.put("nombreGenerico", medicamento.nombreGenerico);
		String[] whereArgs = new String[]{String.valueOf(medicamento.idMedicamento)};
		if(db!=null){
			try{
				db.update("Medicamento", values, "idMedicamento=?", whereArgs);
				res = 1;
			}catch(SQLException e){
				return -1;
			}
		}
		return res;
	}
	
	/**
	 * Funcion para actualizar un medicamento
	 */
	public int EliminarMedicamento(Medicamento medicamento, Context context){
		int res = 0;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		String[] whereArgs = new String[]{String.valueOf(medicamento.idMedicamento)};
		if(db!=null){
			try{
				db.delete("Medicamento", "idMedicamento=?", whereArgs);
				res = 1;
			}catch(SQLException e){
				return -1;
			}
		}
		return res;
	}
	
	/**
	 * Funcion para agregar una presentación a la base de datos.
	 * @param presentacion
	 * @param context
	 * @return
	 */
	public long agregarPresentacion(Presentacion presentacion,Context context){
		long id = 0;
		res = context.getResources();
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		if (db != null) {
			try {
				ContentValues nuevoRegistro = new ContentValues();
				nuevoRegistro.put("nombrePresentacion", presentacion.nombrePresentacion);
				nuevoRegistro.put("notaPresentacion", presentacion.notaPresentacion);
				id = db.insert("Presentacion", null, nuevoRegistro);
			} catch (SQLException e) {
				//retorno el error
				return -1;
			}
		}
		db.close();
		return id;
	}
	
	/**
	 * Función para modificar las presentaciones
	 */
	public int ActualizarPresentacion(Presentacion presentacion, Context context){
		int res = 0;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("nombrePresentacion",presentacion.nombrePresentacion);
		values.put("notaPresentacion", presentacion.notaPresentacion);
		String[] whereArgs = new String[]{String.valueOf(presentacion.idPresentacion)};
		if(db!=null){
			try{
				db.update("Presentacion", values, "idPresentacion=?", whereArgs);
				res = 1;
			}catch(SQLException e){
				return -1;
			}
		}
		return res;
	}
	
	/**
	 * Función para eliminar las presentaciones
	 */
	public int EliminarPresentacion(Presentacion presentacion, Context context){
		int res = 0;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		String[] whereArgs = new String[]{String.valueOf(presentacion.idPresentacion)};
		if(db!=null){
			try{
				db.delete("Presentacion", "idPresentacion=?", whereArgs);
				res = 1;
			}catch(SQLException e){
				return -1;
			}
		}
		return res;
	}
	
	/**
	 * Obtiene un arraylist con el nombre e identificador del usuario
	 * 
	 * @param context
	 * @return arraylist con el nombre e identificador del usuario
	 */
	public ArrayList<Usuario> UsuariosList(Context context) {
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getReadableDatabase();

		if (db != null) {
			String[] fields = new String[] { "idUser", "nombre"};
			Cursor query = db.query("Usuario", fields, null, null, null, null,
					null);
			if (query.moveToFirst()) {
				do {
					Usuario temp = new Usuario(query.getString(1));
					temp.idUsuario = query.getInt(0);
					listaUsuarios.add(temp);
				} while (query.moveToNext());
			}
		}
		db.close();
		return listaUsuarios;
	}

	/**
	 * Funcion para obtener los datos de los medicamentos.
	 */
	public ArrayList<Medicamento> MedicamentosList(Context context) {
		ArrayList<Medicamento> listaMedicamentos = new ArrayList<Medicamento>();
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getReadableDatabase();

		if (db != null) {
			String[] fields = new String[] { "idMedicamento", "nombreComercial" ,"nombreGenerico"};
			Cursor query = db.query("Medicamento", fields, null, null, null, null,
					null);
			if (query.moveToFirst()) {
				do {
					Medicamento temp = new Medicamento(query.getInt(0), query.getString(1), query.getString(2));
					listaMedicamentos.add(temp);
				} while (query.moveToNext());
			}
		}
		db.close();
		return listaMedicamentos;
	}
	
	/**
	 * Funcion para obtener los datos de las presentaciones.
	 */
	public ArrayList<String[]> PresentacionesList(Context context) {
		ArrayList<String[]> listaPresentaciones = new ArrayList<String[]>();
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getReadableDatabase();

		if (db != null) {
			String[] fields = new String[] { "idPresentacion", "nombrePresentacion" ,"notaPresentacion"};
			Cursor query = db.query("Presentacion", fields, null, null, null, null,
					null);
			if (query.moveToFirst()) {
				do {
					String[] buffer = new String[3];
					buffer[0] = query.getString(0);
					buffer[1] = query.getString(1);
					buffer[2] = query.getString(2);
					listaPresentaciones.add(buffer);
				} while (query.moveToNext());
			}
		}
		db.close();
		return listaPresentaciones;
	}


}
