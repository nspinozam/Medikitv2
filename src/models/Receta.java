package models;

import java.io.Serializable;

public class Receta implements Serializable{

	public int idUsuario;
	public int idMedicina;
	public int cantidadConsumo;
	public int idTipoConsumo;
	public String fechaI;
	public String horaI;
	public int duracionDias;
	public int cadaDias;
	public int vecesDia;
	public String nota;
	/////////Cuando se crea la receta para la notificación////////////////
	public String nombreMedicina;
	public String nombreConsumo;
	public String nombrePaciente;
	public int idReceta;
	
	public Receta(int idUsuario, int idMedicina, int cantidadConsumo,int idTipoConsumo, String fechaI,
			String HoraI, int duracionDias, int cadaDias,int vecesDia,String nota) {
		this.idUsuario=idUsuario;
		this.idMedicina = idMedicina;
		this.cantidadConsumo=cantidadConsumo;
		this.idTipoConsumo=idTipoConsumo;
		this.fechaI = fechaI;
		this.horaI = HoraI;
		this.duracionDias= duracionDias;
		this.cadaDias= cadaDias;
		this.vecesDia = vecesDia;
		this.nota = nota;
	}
	
	public Receta(int idReceta, String fechaI, int duracionDias, String nombreConsumo, String nombrePaciente, int cantidadConsumo, String nombreMedicina) {
		this.idReceta = idReceta;
		this.fechaI = fechaI;
		this.nombreMedicina = nombreMedicina;
		this.duracionDias = duracionDias;
		this.nombreConsumo = nombreConsumo;
		this.nombrePaciente = nombrePaciente;
		this.cantidadConsumo = cantidadConsumo;
	}
	
	@Override
	public String toString(){
		return "";
	}

}
