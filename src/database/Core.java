package database;

import java.util.ArrayList;

import models.Medicamento;
import models.Presentacion;
import models.Receta;
import models.Usuario;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaExtractor;
import android.util.Log;

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
	public int EliminarUsuario(int user, Context context){
		int res = 0;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("uEstado",1);
		String[] whereArgs = new String[]{String.valueOf(user)};
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
		ContentValues values = new ContentValues();
		values.put("mEstado",1);
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
		ContentValues values = new ContentValues();
		values.put("pEstado",1);
		String[] whereArgs = new String[]{String.valueOf(presentacion.idPresentacion)};
		if(db!=null){
			try{
				db.update("Presentacion",values, "idPresentacion=?", whereArgs);
				res = 1;
			}catch(SQLException e){
				return -1;
			}
		}
		return res;
	}

	/**
	 * Funcion para agregar la receta a la base de datos.
	 * @param user instancia de la receta
	 * @param context contexto de la aplicación
	 * @return
	 */
	public long agregarReceta(Receta receta,Context context){
		long id = 0;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		if (db != null) {
			try {
				ContentValues nuevoRegistro = new ContentValues();
				nuevoRegistro.put("idUsuario", receta.idUsuario);
				nuevoRegistro.put("idMedicina", receta.idMedicina);
				nuevoRegistro.put("cantidadConsumo", receta.cantidadConsumo);
				nuevoRegistro.put("idTipoConsumo", receta.idTipoConsumo);
				nuevoRegistro.put("fechaI", receta.fechaI);
				nuevoRegistro.put("duracionDias", receta.duracionDias);
				nuevoRegistro.put("cadaDias", receta.cadaDias);
				nuevoRegistro.put("vecesDia", receta.vecesDia);
				nuevoRegistro.put("nota", receta.nota);
				id = db.insert("Receta", null, nuevoRegistro);
			} catch (SQLException e) {
				//retorno el error
				return -1;
			}
		}
		db.close();
		return id;
	}
	
	public int ActualizarReceta(Receta receta, Context context){
		int res = 0;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		ContentValues nuevoRegistro = new ContentValues();
		nuevoRegistro.put("idUsuario", receta.idUsuario);
		nuevoRegistro.put("idMedicina", receta.idMedicina);
		nuevoRegistro.put("cantidadConsumo", receta.cantidadConsumo);
		nuevoRegistro.put("idTipoConsumo", receta.idTipoConsumo);
		nuevoRegistro.put("fechaI", receta.fechaI);
		nuevoRegistro.put("duracionDias", receta.duracionDias);
		nuevoRegistro.put("cadaDias", receta.cadaDias);
		nuevoRegistro.put("vecesDia", receta.vecesDia);
		nuevoRegistro.put("nota", receta.nota);
		String[] whereArgs = new String[]{String.valueOf(receta.idReceta)};
		if(db!=null){
			try{
				db.update("Receta", nuevoRegistro, "idReceta=?", whereArgs);
				res = 1;
			}catch(SQLException e){
				return -1;
			}
		}
		return res;
	}
	
	/**
	 * Funci�n para eliminar las presentaciones
	 */
	public int EliminarReceta(int receta, Context context){
		int res = 0;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("rEstado",1);
		String[] whereArgs = new String[]{String.valueOf(receta)};
		if(db!=null){
			try{
				db.update("Receta",values, "idReceta=?", whereArgs);
				//db.delete("Receta", "idReceta=?", whereArgs);
				res = 1;
			}catch(SQLException e){
				return -1;
			}
		}
		return res;
	}
	/**
	 * Funcion para agregar los horarios a la base de datos
	 * @param idReceta identificador de la receta.
	 * @param horarios array con el horario de la receta
	 * 
	 * @param context contexto de la aplicación
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList agregarHorarios(long idReceta,ArrayList horarios, Context context){
		ArrayList id = new ArrayList();
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		if (db != null) {
			try {
				for (int i = 0; i < horarios.size(); i++) {
					long idl = 0;
					ArrayList contenedor = (ArrayList) horarios.get(i);
					ContentValues nuevoRegistro = new ContentValues();
					nuevoRegistro.put("idReceta", idReceta);
					nuevoRegistro.put("hora", contenedor.get(1).toString());
					idl = db.insert("Hour", null, nuevoRegistro);
					id.add(idl);
				}
				
			} catch (SQLException e) {
				//retorno el error
				return null;
			}
		}
		db.close();
		return id;
	}
	
	public int EliminarHorarios(int receta, Context context){
		int res = 0;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		String[] whereArgs = new String[]{String.valueOf(receta)};
		if(db!=null){
			try{
				db.delete("Hour", "idReceta=?", whereArgs);
				res = 1;
			}catch(SQLException e){
				return -1;
			}
		}
		return res;
	}
	/*public int ActualizarHorarios(long idReceta,ArrayList horarios, Context context){
		int res = 0;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getWritableDatabase();
		ContentValues nuevoRegistro = new ContentValues();
		nuevoRegistro.put("idReceta", idReceta);
		nuevoRegistro.put("hora", contenedor.get(1).toString());
		String[] whereArgs = new String[]{String.valueOf(idReceta)};
		if(db!=null){
			try{
				db.update("Receta", nuevoRegistro, "idReceta=?", whereArgs);
				res = 1;
			}catch(SQLException e){
				return -1;
			}
		}
		return res;
	}*/
	
	public Receta obtenerReceta(int id,Context context) {
		Receta receta = null;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getReadableDatabase();

		if (db != null) {
			Cursor query = db.rawQuery("SELECT idUsuario, idMedicina,cantidadConsumo,idTipoConsumo," +
					"fechaI,duracionDias,cadaDias,vecesDia,nota FROM Receta "
					+ "WHERE Receta.idReceta = "+id,null);
			if (query.moveToFirst()) {
				do {
					receta = new Receta(query.getInt(0), query.getInt(1), query.getInt(2), query.getInt(3), 
							query.getString(4), null, query.getInt(5), query.getInt(6), query.getInt(7), query.getString(8));
				} while (query.moveToNext());
			}
		}
		db.close();
		return receta;
	}
	
	public Medicamento obtenerMedicamento(int id,Context context) {
		Medicamento receta = null;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getReadableDatabase();

		if (db != null) {
			Cursor query = db.rawQuery("SELECT nombreComercial, nombreGenerico FROM Medicamento "
					+ "WHERE Medicamento.idMedicamento = "+id,null);
			if (query.moveToFirst()) {
				do {
					receta = new Medicamento(id, query.getString(0), query.getString(1));
				} while (query.moveToNext());
			}
		}
		db.close();
		return receta;
	}
	
	public Presentacion obtenerPresentacion(int id,Context context) {
		Presentacion receta = null;
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getReadableDatabase();

		if (db != null) {
			Cursor query = db.rawQuery("SELECT nombrePresentacion, notaPresentacion FROM Presentacion "
					+ "WHERE Presentacion.idPresentacion = "+id,null);
			if (query.moveToFirst()) {
				do {
					receta = new Presentacion(id, query.getString(0), query.getString(1));
				} while (query.moveToNext());
			}
		}
		db.close();
		return receta;
	}
	
	public String obtenerHoraI(int id,Context context) {
		String hora = "";
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getReadableDatabase();

		if (db != null) {
			Cursor query = db.rawQuery("SELECT hora FROM Hour "
					+ "WHERE Hour.idReceta = "+id,null);
			if (query.moveToFirst()) {
				hora = query.getString(0);
			}
		}
		db.close();
		return hora;
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
			String[] whereArgs = new String[]{"0"};
			Cursor query = db.query("Usuario", fields, "uEstado=?", whereArgs, null, null,
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
			String[] whereArgs = new String[]{"0"};
			Cursor query = db.query("Medicamento", fields, "mEstado=?", whereArgs, null, null,
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
	public ArrayList<Presentacion> PresentacionesList(Context context) {
		ArrayList<Presentacion> listaPresentaciones = new ArrayList<Presentacion>();
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getReadableDatabase();

		if (db != null) {
			String[] whereArgs = new String[]{"0"};
			String[] fields = new String[] { "idPresentacion", "nombrePresentacion" ,"notaPresentacion"};
			Cursor query = db.query("Presentacion", fields,"pEstado=?", whereArgs, null, null,
					null);
			if (query.moveToFirst()) {
				do {
					Presentacion temp = new Presentacion(query.getInt(0),query.getString(1), query.getString(2));
					listaPresentaciones.add(temp);
				} while (query.moveToNext());
			}
		}
		db.close();
		return listaPresentaciones;
	}

	/**
	 * Funcion para obtener los datos de las presentaciones.
	 */
	public ArrayList RecetaListInicial(int id,Context context) {
		ArrayList listaPresentaciones = new ArrayList();
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getReadableDatabase();

		if (db != null) {
			//String[] fields = new String[] { "idPresentacion", "nombrePresentacion" ,"notaPresentacion"};
			Cursor query = db.rawQuery("SELECT idReceta, cantidadConsumo,nombrePresentacion, nombreComercial FROM Receta, Medicamento, Presentacion "
					+ "WHERE Receta.idTipoConsumo = Presentacion.idPresentacion AND Receta.idMedicina = Medicamento.idMedicamento" +
					" AND Presentacion.pEstado = 0 AND Receta.rEstado = 0 AND Medicamento.mEstado = 0 AND Receta.idUsuario = "+id,null);
			while(query.moveToNext()) {
					ArrayList temp = new ArrayList();
					temp.add(query.getInt(0));
					temp.add(query.getInt(1));
					temp.add(query.getString(2));
					temp.add(query.getString(3));
					listaPresentaciones.add(temp);
			}
		}
		db.close();
		return listaPresentaciones;
	}
	
	public ArrayList RecetasUsuario(int id,Context context) {
		ArrayList listaPresentaciones = new ArrayList();
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getReadableDatabase();

		if (db != null) {
			//String[] fields = new String[] { "idPresentacion", "nombrePresentacion" ,"notaPresentacion"};
			Cursor query = db.rawQuery("SELECT idReceta FROM Receta WHERE Receta.rEstado = 0 AND Receta.idUsuario = "+id,null);
			while(query.moveToNext()) {
					ArrayList temp = new ArrayList();
					temp.add(query.getInt(0));
					listaPresentaciones.add(temp);
			}
		}
		db.close();
		return listaPresentaciones;
	}
	
	
	public String consultarNombreC(int id,Context context) {
		String nombre = "";
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getReadableDatabase();

		if (db != null) {
			Cursor query = db.rawQuery("SELECT nombrePresentacion FROM Presentacion "
					+ "WHERE Presentacion.idPresentacion = "+id,null);
			if (query.moveToFirst()) {
				do {
					nombre = query.getString(0);
				} while (query.moveToNext());
			}
		}
		db.close();
		return nombre;
	}

	public String consultarNombreP(int id,Context context) {
		String nombre = "";
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getReadableDatabase();

		if (db != null) {
			Cursor query = db.rawQuery("SELECT nombre FROM Usuario "
					+ "WHERE Usuario.idUser = "+id,null);
			if (query.moveToFirst()) {
				do {
					nombre = query.getString(0);
				} while (query.moveToNext());
			}
		}
		db.close();
		return nombre;
	}
	
	public String consultarNombreM(int id,Context context) {
		String nombre = "";
		MedikitDB rdb = new MedikitDB(context, "database", null, 1);
		SQLiteDatabase db = rdb.getReadableDatabase();

		if (db != null) {
			Cursor query = db.rawQuery("SELECT nombreComercial FROM Medicamento "
					+ "WHERE Medicamento.idMedicamento = "+id,null);
			if (query.moveToFirst()) {
				do {
					nombre = query.getString(0);
				} while (query.moveToNext());
			}
		}
		db.close();
		return nombre;
	}
	
}
