package modelo;

import java.io.*;
import java.nio.file.*;

public class CompruevaCopia implements Runnable{
	boolean isFileUnlocked = false;
	
	public void run() {
		
		while(true) {
		try {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			leeDatos();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getMessage();
		}
	}
	}
	
	//----LEEMOS LOS TRES DATOS ALMACENADOS DE CADA SOURCE----------
	public void leeDatos() throws IOException {
		 ManageFileAppData mf = new ManageFileAppData();
         mf.compruevaSincronizacion();
		File archivosDatos = new File(System.getProperty("user.home")+"\\AppData\\Local\\buckUpData/DatosArchivos");
		BufferedReader leerDatos = null;
		
		if(archivosDatos.exists()) {
		String datos[] = new String[3];
		String internos[] = archivosDatos.list();
		
		if(internos.length != 0) {
		 for (int i = 0; i < internos.length; i++) {
			 
           File f = new File(archivosDatos.getAbsolutePath(),internos[i]);
           
           if(f.exists()) {
            leerDatos = new BufferedReader(new FileReader(f));
           
           for (int j = 0; j < 3; j++) {
	           
        	   datos[j] = leerDatos.readLine();
		}
           leerDatos.close();
           }
         if(datos.length != 0) {
        	
        	 copruevaModificacion(datos);           	 
            }
		 }
		}
	  }
	}
	//-----------------------------------------------------------------------
	

	//---COMPROVAMOS SI EL ARCHIVO A SIDO MODIIFICADO Y EN DICHO CASO YAMAMOS AL RESPECTIVO METODO PARA SU RECOPIA
	private void copruevaModificacion(String[] datos) {
		File rutaOrigen = new File(datos[0]);
		File rutaDestino = new File(datos[1]);
		long fechaMod = Long.parseLong(datos[2]);
		  Long actualUltimaMod = rutaOrigen.lastModified();
		if( actualUltimaMod != fechaMod) {
			HacerCopia hc = new HacerCopia();
			try {
				hc.controlCopia(rutaOrigen, rutaDestino);
				ManageFileAppData mf = new ManageFileAppData();
				mf.reescribeUltimaMod(rutaOrigen,rutaDestino,actualUltimaMod);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

















