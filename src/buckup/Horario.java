package buckup;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;

public class Horario {
	
	String hora,minutos;
 

	public static void main(String[]args) {

		Horario  h = new Horario();
		
		 Scanner tcla= new Scanner(System.in);
		System.out.println("Ingrese la hora");
	    String hora = tcla.nextLine();
	    System.out.println("Ingrese el minuto");
	    String minunos = tcla.nextLine();
	System.out.println(h.getHora());
	}
	
	public void setHora(String hora) {
		this.hora  = hora;
	}
	
    public void setMinutos(String minutos) {
		this.minutos = minutos;
	}
   
    public String getHora() {
    	
    	
    	
    	return  hora+":"+minutos;
    }
}






