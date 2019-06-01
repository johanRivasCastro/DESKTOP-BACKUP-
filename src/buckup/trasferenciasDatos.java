package buckup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class trasferenciasDatos {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Operaciones op = new Operaciones();
		op.OperacionesE();
	
		
//		try {
//			td.transferencia(new File("C:\\Users\\HP-EliteBook\\Desktop\\itla"), new File("C:\\Users\\HP-EliteBook\\Desktop\\carpeta 2"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	
		
	}
	

}


  class Operaciones{
	  
	  public void OperacionesE() {
		  
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
				JFileChooser chooser = new JFileChooser();  // clase para acceder al sistema de archivos
			    FileNameExtensionFilter filter = new FileNameExtensionFilter( 
			        "Archivos txt", "txt");   /*clase con la que filtramos  el formato de los documentos*/
			   
			    chooser.setFileFilter(filter); // establecemos el filtro
			    int returnVal = chooser.showOpenDialog(null); // establecemos el componente padre sobre el que va a actuar
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			      /* System.out.println("You chose to open this file: " +
			           chooser.getSelectedFile().getName());*/
			    	
			    	System.out.println(chooser.getSelectedFile().getAbsolutePath());
			    }
			 
		   }
	  
	  
	  private static void transferencia(File source, File dest) throws IOException {
		    Files.copy(source.toPath(), dest.toPath());
			
			if(source.isDirectory()) {
				
				String nomSource = source.getName();
				
				String carpetaF = dest.getAbsolutePath() +"/"+ nomSource;
				
				System.out.println(carpetaF);
				Files.copy(source.toPath(),new File(carpetaF).toPath());
				
			}else {
				Files.copy(source.toPath(), dest.toPath());
			}
		}
	   
  }




