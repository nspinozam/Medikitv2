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
	
	@Override
	public String toString(){
		String res = "";
		if(this.notaPresentacion!=null && !this.notaPresentacion.equals("")){
			res = this.nombrePresentacion+"\nNota: "+this.notaPresentacion;
		}
		else{
			res = this.nombrePresentacion;
		}
		return res;
	}

}
