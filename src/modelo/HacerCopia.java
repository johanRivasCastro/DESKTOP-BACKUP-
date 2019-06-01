package modelo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;

public class HacerCopia {
	
	private File source, target;
	private ManageFileAppData mfd = new ManageFileAppData();

	
	// determinamos si el source es un directorio o un archivo
	public void controlCopia(File source, File target) throws IOException {
		
		this.source = source;
		this.target = target;
		String targetF = "";
		if(source.isDirectory()) {
			
		   targetF = this.target+"/"+this.source.getName();
		   compruevaTipo(this.source,new File(targetF));
		   
		}else {
			
			copiarSoloArchivo(this.source,this.target);
			System.out.println(">>>>>>>>>>>>>>>>>>>");
		}
		
	}
	
	// comprovamos cada elemento dentro del archivo
	public void compruevaTipo(File source, File target) throws IOException {
      if (source.isDirectory()) {
    	  copiarDirectorio(source, target);
	    } else {
	    	copiarArchivos(source, target);
	    }
	}
	
	
	// en casa de que sea un directorio. sacamos cada elemento hijo
	private void copiarDirectorio(File source, File target) throws IOException {
		if (!target.exists()) {
	        target.mkdir();
	        
	    }
		
		for (String f : source.list()) {
			compruevaTipo(new File(source, f), new File(target, f));
			
	    }

	}
	
	
	// copiamos cada archivo del origen a su destino
	private void copiarArchivos(File source, File target) throws IOException {
		Files.copy(source.toPath(),target.toPath(),StandardCopyOption.REPLACE_EXISTING);
		
		mfd.compruevaTipo(source, target);
	}

	private void copiarSoloArchivo(File source, File target) throws IOException {
	
		if(target.isDirectory()) {
			
		 String targetFile = target.getAbsolutePath()+"/"+source.getName();
		    if(source.exists()) {
			Files.copy(source.toPath(), new File(targetFile).toPath(),StandardCopyOption.REPLACE_EXISTING);
		    mfd.compruevaTipo(source, target);   
		    }
		    
		}else {
			if(source.exists()) {
				Files.copy(source.toPath(),target.toPath(),StandardCopyOption.REPLACE_EXISTING);
			 }
		}
	}

}

















