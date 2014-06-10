package models;

import android.os.Parcel;
import android.os.Parcelable;

public class Receta implements Parcelable{

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
	
	@Override
	public String toString(){
		return "";
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
