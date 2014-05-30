package models;

public class Presentacion {

	public int idPresentacion;
	public String nombrePresentacion;
	public String notaPresentacion;
	
	public Presentacion(String nombre, String nota) {
		this.nombrePresentacion = nombre;
		this.notaPresentacion = nota;
	}
	
	public Presentacion(int id, String nombre, String nota) {
		this.idPresentacion = id;
		this.nombrePresentacion = nombre;
		this.notaPresentacion = nota;
	}

}
