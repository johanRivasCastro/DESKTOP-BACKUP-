package modelo;

import java.io.*;
import java.util.*;


public class ManageFileAppData {
	
	private File  target;
	private BufferedReader reedTargets;
	private ArrayList<File> rutasTree = null;
	private ArrayList<File> filesInternos = null;
	
	
	//-------LEEMOS EN buckUp_targets CADA RUTA DESTINO PARA LA CARPETA SER MOSTRADA EN JTree----------
	public ArrayList<File> getValidTargets() throws IOException  {
		
		rutasTree = new ArrayList<File>();
		File file = new File(System.getProperty("user.home")+"\\AppData\\Local\\buckUpData/buckUp_targets"); 
		
		if(file.exists()) {	
				
		
			String internos[] = file.list();
			
			 for (int i = 0; i < internos.length; i++) {
				 
				String rutaTarget[] = new String [1];
				 
	           File f = new File(file.getAbsolutePath(),internos[i]);
	           
	           BufferedReader leerDatos = new BufferedReader(new FileReader(f));
	           
	           for (int j = 0; j < 1; j++) {
		           
	        	   rutaTarget[j] = leerDatos.readLine();     
	           }
	           leerDatos.close();
	           rutasTree.add(new File(rutaTarget[0]));
			 }
			 
		return rutasTree;
	  }else {
		  
		  File rutaFalsa = new File("xxxxxxxxxxxxxxxxxxxx");
		  rutasTree = new ArrayList<File>();
		  rutasTree.add(rutaFalsa);
		  
		  return rutasTree;
	  }
		
	}
	//------------------------------------------------------------------------
	


	//----------REGISTRAMOS LAS NUEVAS RUTAS TARGET EN appData//Local\\buckUp_targets-------
	public void writeAppData(File target) throws IOException {
		
		File rutaDestinoDatosTar = null;
		String nomFiles = target.getName();
		boolean yaEsta = false;
		
		
		
		String signoDolar = "";
		
		File datosArchivos = new File(System.getProperty("user.home")+"\\AppData\\Local\\buckUpData/buckUp_targets");
		
		if(!datosArchivos.exists()) {
			datosArchivos.mkdirs(); 
		}

			String archivosInt[] = datosArchivos.list();
			
			for (int i = 0; i < archivosInt.length; i++) {
				 
				String ruta = "";
				 
	           File f = new File(datosArchivos.getAbsolutePath(),archivosInt[i]);
	           
	           BufferedReader leerDatos = new BufferedReader(new FileReader(f));
	           
	           for (int j = 0; j < 1; j++) {
		           
	        	   ruta = leerDatos.readLine();   
			}
	           
	           leerDatos.close();
	           // compravamos si de dicho archivo ya se a hecho copia de seguridad
	           if(ruta.equals(target.getAbsolutePath())) {
	        	   yaEsta = true;
	           }	      
			}
			// comprovamos si no hay ningun archivo de copia de datos, 
		  	
		    
			if(yaEsta == false) {
		// coprovamos si lla se hiso copia de datos de un source con el mismo nombre, en dicho caso, modificamos el nombre
			boolean hayIgual= true;		
			while(hayIgual) {
			hayIgual = false;
			for (int i = 0; i < archivosInt.length; i++) {
				  if(archivosInt[i].equals(nomFiles)) {
					 signoDolar += "$";
					 nomFiles = signoDolar+target.getName();
					 hayIgual = true;
			      }
			    }
	        }
			
			rutaDestinoDatosTar = new File(datosArchivos.getAbsolutePath()+"/"+nomFiles);
	
		    // creamos y escrivimos los datos de archivo target.
		
			BufferedWriter write_fileBuckupTar = new BufferedWriter(new FileWriter(rutaDestinoDatosTar));		
			write_fileBuckupTar.append(target.getAbsolutePath());
			write_fileBuckupTar.close();	
		}
		}
	
	//------------------------------------------------------------------------------
	

	
	//------AMACENAMOS LOS DATOS DE LOS ARCHIVOS SOURCE---------------
	public void compruevaTipo(File source, File target) throws IOException {
	      if (source.isDirectory()) {
	    	  desintegrarDirectorio(source, target);
		    } else {
		    	CopiarDatosSeguridadFiles(source, target);
		    }
		}
		private void desintegrarDirectorio(File source, File target) throws IOException {
			
			for (String f : source.list()) {
				compruevaTipo(new File(source, f), new File(target, f));
				
		    }

		}

		
	public void CopiarDatosSeguridadFiles(File source, File target)throws IOException {
		
		File rutaDestinoDatosS = null;
		long ultimaMod;
		String nomFiles = source.getName();
		boolean yaEsta = false;
		
	
		
		String signoDolar = "";
		
		File datosArchivos = new File(System.getProperty("user.home")+"\\AppData\\Local\\buckUpData/DatosArchivos");
		
		if(!datosArchivos.exists()) {
			datosArchivos.mkdirs(); 
		}

			String archivosInt[] = datosArchivos.list();
			
			for (int i = 0; i < archivosInt.length; i++) {
				 
				String ruta = "";
				 
	           File f = new File(datosArchivos.getAbsolutePath(),archivosInt[i]);
	           
	           BufferedReader leerDatos = new BufferedReader(new FileReader(f));
	           
	           for (int j = 0; j < 1; j++) {
		           
	        	   ruta = leerDatos.readLine();   
			}
	           
	           leerDatos.close();
	           // compravamos si de dicho archivo ya se a hecho copia de seguridad
	           if(ruta.equals(source.getAbsolutePath())) {
	        	   yaEsta = true;
	           }	      
			}
			// comprovamos si no hay ningun archivo de copia de datos, 
		  	
		    
			if(yaEsta == false) {
		// coprovamos si lla se hiso copia de datos de un source con el mismo nombre, en dicho caso, modificamos el nombre
			boolean hayIgual= true;		
			while(hayIgual) {
			hayIgual = false;
			for (int i = 0; i < archivosInt.length; i++) {
				
				  if(archivosInt[i].equals(nomFiles+"_.txt")) {
					 signoDolar += "$";
					 nomFiles = signoDolar+source.getName();
					 hayIgual = true;
			      }
			    }
	        }
			
			nomFiles += "_.txt";
			
			rutaDestinoDatosS = new File(datosArchivos.getAbsolutePath()+"/"+nomFiles);
	
		    // creamos y escrivimos los datos de archivo source.
			ultimaMod = source.lastModified();
			BufferedWriter write_fileBuckupTar = new BufferedWriter(new FileWriter(rutaDestinoDatosS));		
			write_fileBuckupTar.append(source.getAbsolutePath());
			write_fileBuckupTar.newLine();
			write_fileBuckupTar.append(target.getAbsolutePath());
			write_fileBuckupTar.newLine();
			write_fileBuckupTar.append(String.valueOf(ultimaMod));
			write_fileBuckupTar.close();	
		}
	}
	//-------------------------------------------------------------------------------------


	
	//-------REESCRIBIMOS LA ULTIMA FECHA DE MODIFICACION DE AQUELLOS DOCUMENTOS QUE FUERON MODIFICADOS
	public void reescribeUltimaMod(File source, File target, Long actualUltimaMod) throws IOException {
		
        File archivosDatos = new File(System.getProperty("user.home")+"\\AppData\\Local\\buckUpData/DatosArchivos");
		
		String internos[] = archivosDatos.list();
		
		 for (int i = 0; i < internos.length; i++) {
			 
			String ruta = "";
			 
           File f = new File(archivosDatos.getAbsolutePath(),internos[i]);
           
           BufferedReader leerDatos = new BufferedReader(new FileReader(f));
           
           for (int j = 0; j < 1; j++) {
	           
        	   ruta = leerDatos.readLine();   
		}
           
           leerDatos.close();
           if(ruta.equals(source.getAbsolutePath())) {
        	   PrintWriter writer = new PrintWriter(f);
        	   writer.print("");
        	   writer.close();
        	BufferedWriter write_fileBuckupTar = new BufferedWriter(new FileWriter(f,true));		
   			write_fileBuckupTar.append(source.getAbsolutePath());
   			write_fileBuckupTar.newLine();
   			write_fileBuckupTar.append(target.getAbsolutePath());
   			write_fileBuckupTar.newLine();
   			write_fileBuckupTar.append(String.valueOf(actualUltimaMod));
   			write_fileBuckupTar.close();
           }
		 }
	}
	//--------------------------------------------------------------------------------



	//------COMPROVAMOS QUE CADA ARCHIVO ORIGEN ESTA EN SU DESTINO, SINO ELIMINAMOS SUS DATOS EN DatosArchivos------
	public void compruevaSincronizacion() throws IOException {
		
    File archivosDatos = new File(System.getProperty("user.home")+"\\AppData\\Local\\buckUpData/DatosArchivos");
		 
        if(archivosDatos.exists()) {
      
		String internos[] = archivosDatos.list();
		
		 for (int i = 0; i < internos.length; i++) {
			 
			String rutas[] = new String [2];
			 
           File f = new File(archivosDatos.getAbsolutePath(),internos[i]);
           
           BufferedReader leerDatos = new BufferedReader(new FileReader(f));
           
           for (int j = 0; j < 2; j++) {
	           
        	   rutas[j] = leerDatos.readLine();     
           }
           
           leerDatos.close();
           File rutaOrigen = new File(rutas[0]);
           File estaEnTarget = new File(rutas[1]);
           
           if(estaEnTarget.isDirectory()) {
        	   estaEnTarget = new File(rutas[1]+"/"+rutaOrigen.getName());
           }

           if(!estaEnTarget.exists() || !rutaOrigen.exists()) {
        		 f.delete();
           }
		}
       }  
	}
	//--------------------------------------------------------------------------------------------


    //----CONFIRMAMOS SI LA RUTA QUE NOS ENVIAN ESTA ACTUALMENTE EN SICRONIZACION. CONFIRMAMOS LOS HIJOS DE ESE TARGET QUE ESTA EN SINCRONIZACION ANTES DE MOSTRARLO EN EL JTree-------------
	public boolean confirmaHijoTarget(File hijo) throws IOException {
		
		boolean bol = false;
		
		 File archivosDatos = new File(System.getProperty("user.home")+"\\AppData\\Local\\buckUpData/DatosArchivos");
		
			String internos[] = archivosDatos.list();
			
			 for (int i = 0; i < internos.length; i++) {
				 
				String rutas[] = new String [2];
				 
	           File f = new File(archivosDatos.getAbsolutePath(),internos[i]);
	           
	           BufferedReader leerDatos = new BufferedReader(new FileReader(f));
	           
	           for (int j = 0; j < 2; j++) {
		           
	        	   rutas[j] = leerDatos.readLine();     
	           }
	           //--comprovamos si cada target y cada uno de sus hijos estan es anguno de los datos en DatosArchivos
	           leerDatos.close();
	           
	           String rutaHijo = hijo.getAbsolutePath();

	           if(hijo.isDirectory() & rutaHijo.length() < rutas[1].length()) {
	      		
	    		if(rutas[1].substring(0,rutaHijo.length()).equals(rutaHijo)) {
	    			 bol= true;
	    	      }
	           }else if(hijo.getName().equals(new File(rutas[0]).getName())) {
	        	   bol= true;
	           }
		   }
	       return bol;
		}
	//-----------------------------------------------------------------------------------------

	
	
	//----COMPROVAMOS SI CADA TARGET ACTUALAMENTE ESTA SINCRONIZANDO ALGUN ELEMENTO, SI NO ELIMINAMO SU DATO EN buckUp_targets-------
	 public void eliminaDatosTarge() throws IOException {
	 
		File file = new File(System.getProperty("user.home")+"\\AppData\\Local\\buckUpData/buckUp_targets"); 
		 BufferedReader leerDatosSource = null;
		 
		 if(file.exists()) {
		String internos[] = file.list();
		
		 for (int i = 0; i < internos.length; i++) {
		
			 boolean bol = false;
			 
			String rutas[] = new String [1];
			 
           File f = new File(file.getAbsolutePath(),internos[i]);
           
           BufferedReader leerDatos = new BufferedReader(new FileReader(f));
           
           for (int j = 0; j < 1; j++) {
	           
        	   rutas[j] = leerDatos.readLine();     
           }
           leerDatos.close();
           
           //--s accedemos a DatosArchivos para leer cada ruta target de cada source
           File archivosDatos = new File(System.getProperty("user.home")+"\\AppData\\Local\\buckUpData/DatosArchivos");
			String internosS[] = archivosDatos.list();
			 for (int l = 0; l < internosS.length; l++) {	
				 
				String rutasTargetSource[] = new String [2]; 
	           File f2 = new File(archivosDatos.getAbsolutePath(),internosS[l]);
	           if(f2.exists()) {
	            leerDatosSource = new BufferedReader(new FileReader(f2));
	           for (int j = 0; j < 2; j++) {  
	        	 
	        	   rutasTargetSource[j] = leerDatosSource.readLine();     
	           }
	           leerDatosSource.close();
	           }
	           
//	   		File f = new File("C:\\Users\\HP-EliteBook\\Desktop\\el\\programacion\\Programacion Web\\ej\\.vs\\VSWorkspaceState.json");
//	   		File f2 = new File("C:\\Users\\HP-EliteBook\\Desktop\\el");
//	   		 String rutaF = f.getAbsolutePath();
//	   		 String rutaF2 = f2.getAbsolutePath();
//	   		if(rutaF.substring(0,rutaF2.length()).equals(rutaF2))
//	   			System.out.println("son iguales");;
	           // hago el proceso comentado arriba de una manera mas resumida ya que el codigo haci lo permite
	          if(rutasTargetSource[1].length() >= rutas[0].length()) {
	            if(rutas[0].equals(rutasTargetSource[1].substring(0, rutas[0].length()))) {
	            	bol = true;
	            	break;
	            }
	          }
	            
			 }
			
			if(bol == false) {
				
				 f.delete();
			 }
		 }
		 
		 }
	 }
	//-----------------------------------------------------------------------------------------


  //--ELIMINAMOS LOS DATOS EN APPDATA DE EL SOURCE O EL TARGE QUE NOS ENVIEN PARA CANCELAR LA SINCRONIZACION
	public void cancelaSincronizacion(File cancelarNode) throws IOException {
		// TODO Auto-generated method stub
		
		if(cancelarNode.isDirectory()) {
			 //---eliminamos los datos de los source hijos de el actual target
			 
			 File archivosDatos = new File(System.getProperty("user.home")+"\\AppData\\Local\\buckUpData/DatosArchivos");			
				String internosS[] = archivosDatos.list();
				
				 for (int i = 0; i < internosS.length; i++) {				 
					String rutas[] = new String [2];					 
		           File f2 = new File(archivosDatos.getAbsolutePath(),internosS[i]);		           
		           BufferedReader leerDatos2 = new BufferedReader(new FileReader(f2));
		           
		           for (int j = 0; j < 2; j++) {		           
		        	   rutas[j] = leerDatos2.readLine();     
		           }
		           //--comprovamos si cada target y cada uno de sus hijos estan es anguno de los datos en DatosArchivos
		           leerDatos2.close();
		           
		           String rutaTarget = cancelarNode.getAbsolutePath();

		           if(rutaTarget.length() <= rutas[1].length()) {
		      		
		    		if(rutas[1].substring(0,rutaTarget.length()).equals(rutaTarget)) {
		    			 f2.delete();
		    	      }
		           }
			   }
				 
			// eliminamos el dato del target
			
			File file = new File(System.getProperty("user.home")+"\\AppData\\Local\\buckUpData/buckUp_targets"); 
			String internos[] = file.list();
			
			 for (int i = 0; i < internos.length; i++) {
			 
			   String ruta[] = new String [1];				 
	           File f = new File(file.getAbsolutePath(),internos[i]);	           
	           BufferedReader leerDatos = new BufferedReader(new FileReader(f));
	           
	           for (int j = 0; j < 1; j++) {		           
	        	   ruta[j] = leerDatos.readLine();     
	           }
	           leerDatos.close();
	           
	           if(ruta[0].equals(cancelarNode.getAbsolutePath())) {
	        	   f.delete();
	           }
	           break;
			 }			
		}else {
			 File archivosDatos3 = new File(System.getProperty("user.home")+"\\AppData\\Local\\buckUpData/DatosArchivos");			
				String internosS[] = archivosDatos3.list();
				
				 for (int i = 0; i < internosS.length; i++) {				 
					String rutas3[] = new String [2];					 
		           File f3 = new File(archivosDatos3.getAbsolutePath(),internosS[i]);		           
		           BufferedReader leerDatos3 = new BufferedReader(new FileReader(f3));
		           
		           for (int j = 0; j < 2; j++) {		           
		        	   rutas3[j] = leerDatos3.readLine();     
		           }
		           leerDatos3.close();
		           File rutaT = new File(rutas3[1]);
		           String rutaFile = cancelarNode.getAbsolutePath();
		           if(!rutaT.isDirectory()) {
		        	   System.out.println("<<<<<<<1");
		        	   if(rutaFile.length() <= rutas3[1].length()) {
		        		   System.out.println("<<<<<<<2");
		        	   if(rutas3[1].substring(0, rutaFile.length()).equals(rutaFile)) {
		        		   System.out.println("<<<<<<<3");
		        		   f3.delete();
		        	    }
		        	  }
		           }else {
		        	   System.out.println("<<<<<<<4");
		        	   String rutaFormada = new File(rutaT+"/"+new File(rutas3[0]).getName()).getAbsolutePath();
		        	   
		        	   if(rutaFile.length() <= rutaFormada.length()) {
		        		   System.out.println("<<<<<<<5");
		        		   System.out.println(")))"+rutaFile);
		        		   System.out.println(")))))"+rutaFormada.substring(0, rutaFile.length()));
			        	   if(rutaFormada.substring(0, rutaFile.length()).equals(rutaFile)) {
			        		   System.out.println("<<<<<<<6");
			        		   f3.delete();
			        	    }
			        	  }
		           }
			 }
		}
		
	}
//-----------------------------------------------------------------------------------------	 
	
	
	
	//------CREAMOS EL ARCHIVO .BAT EN STARTUP PARA QUE LA APLICACION SIEMPRE INCIE AUTOMATICAMENTE-------
	public void creaBatStartUp() {
		
		File f =new File(System.getProperty("user.home")+"\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\.bat");
		
		if(!f.exists()) {
			
			try {
				BufferedWriter b = new BufferedWriter(new FileWriter(f));
				
				b.write("javaw -Xmx200m -jar "+System.getProperty("user.home")+"\\Desktop\\LocalBuckUp.jar");
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

		
	
	






