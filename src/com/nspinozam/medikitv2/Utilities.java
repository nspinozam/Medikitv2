package com.nspinozam.medikitv2;

public class Utilities {

	String horas(int hora) {
    	switch(hora) {
    	case 0:
    		return "12";
    	case 12:
    		return "12";
    	case 13:
    		return "1";
    	case 14:
    		return "2";
    	case 15:
    		return "3";
    	case 16:
    		return "4";
    	case 17:
    		return "5";
    	case 18:
    		return "6";
    	case 19:
    		return "7";
    	case 20:
    		return "8";
    	case 21:
    		return "9";
    	case 22:
    		return "10";
    	case 23:
    		return "11";
    	default:
    		return "" + hora;
        }
    }
	
	String NombreMes (int mes){
		String res = "";
		switch (mes) {
			case 0:
				res = "Enero";
				break;
			case 1:
				res = "Febrero";
				break;
			case 2:
				res = "Marzo";
				break;
			case 3:
				res = "Abril";
				break;
			case 4:
				res = "Mayo";
				break;
			case 5:
				res = "Junio";
				break;
			case 6:
				res = "Julio";
				break;
			case 7:
				res = "Agosto";
				break;
			case 8:
				res = "Septiembre";
				break;
			case 9:
				res = "Octubre";
				break;
			case 10:
				res = "Noviembre";
				break;
			case 11:
				res = "Diciembre";
				break;
		default:
			break;
		}
		return res;
	}

}
