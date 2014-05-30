package models;

import android.os.Parcel;
import android.os.Parcelable;


public class Usuario implements Parcelable{
	public int idUsuario;
	public String nombreUsuario;

	public Usuario(String nombre) {
		this.nombreUsuario = nombre;
	}
	
	public Usuario(String nombre, int id){
		this.nombreUsuario = nombre;
		this.idUsuario = id;
	}
	
	@Override
	public String toString(){
		 return this.nombreUsuario;
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
