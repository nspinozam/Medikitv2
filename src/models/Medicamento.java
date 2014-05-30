package models;

public class Medicamento {
	
	public int idMedicamento;
	public String nombreComercial;
	public String nombreGenerico;

	public Medicamento(String nombreC, String nombreG) {
		this.nombreComercial = nombreC;
		this.nombreGenerico = nombreG;
	}
	
	public Medicamento(int idMedicamento, String nombreC, String nombreG) {
		this.idMedicamento = idMedicamento;
		this.nombreComercial = nombreC;
		this.nombreGenerico = nombreG;
	}
	
	@Override
	public String toString() {
		String res =this.nombreComercial+" / "+this.nombreGenerico;
		return res;
	}

}
