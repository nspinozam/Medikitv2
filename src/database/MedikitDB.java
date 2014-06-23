package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MedikitDB extends SQLiteOpenHelper {

	/*private static final String RECIPE_SQL = "CREATE TABLE IF NOT EXISTS Recipe (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, patient TEXT, dosisStartTime TEXT, dosisPerDay INTEGER, dosisInterval INTEGER," +
			"			 dosisAmount INTEGER, dosisType TEXT, duration INTEGER, durationType TEXT, fechaI TEXT, fechaF TEXT)";
	*/
	private static final String USER_SQL = "CREATE TABLE IF NOT EXISTS Usuario (idUser INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT UNIQUE)";
	private static final String MEDICINE_SQL = "CREATE TABLE IF NOT EXISTS 	Medicamento (idMedicamento INTEGER PRIMARY KEY AUTOINCREMENT, nombreComercial TEXT UNIQUE, "
			+ "nombreGenerico TEXT)";
	private static final String PRESENTATION_SQL = "CREATE TABLE IF NOT EXISTS Presentacion(idPresentacion INTEGER PRIMARY KEY AUTOINCREMENT, nombrePresentacion TEXT UNIQUE, "
			+ "notaPresentacion TEXT)";
	private static final String HOUR_SQL = "CREATE TABLE IF NOT EXISTS Hour (idReceta INTEGER PRIMARY KEY AUTOINCREMENT, hora TEXT)";
			
	private static final String DROP_SQL = "DROP TABLE IF EXISTS User";

	
	public MedikitDB(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(USER_SQL);
		db.execSQL(MEDICINE_SQL);
		db.execSQL(PRESENTATION_SQL);
		db.execSQL(HOUR_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_SQL);
		db.execSQL(MEDICINE_SQL);
		db.execSQL(PRESENTATION_SQL);
		db.execSQL(HOUR_SQL);
	}

}
