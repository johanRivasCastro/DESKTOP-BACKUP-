package buckup;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Ejemplos {
	
	//--------------CODE ONE-----------------------
//	private static void copyFileUsingStream(File source, File dest) throws IOException {
//	    InputStream is = null;
//	    OutputStream os = null;
//	    try {
//	        is = new FileInputStream(source);
//	        os = new FileOutputStream(dest);
//	        byte[] buffer = new byte[1024];
//	        int length;
//	        while ((length = is.read(buffer)) > 0) {
//	            os.write(buffer, 0, length);
//	        }
//	    } finally {
//	        is.close();
//	        os.close();
//	    }
//	}
	//------------------END CODE ONE-------------------
	
	
	
   //-------------------CODE TWO-------------------
//private static void copyFileUsingChannel(File source, File dest) throws IOException {
//    FileChannel sourceChannel = null;
//    FileChannel destChannel = null;
//    try {
//        sourceChannel = new FileInputStream(source).getChannel();
//        destChannel = new FileOutputStream(dest).getChannel();
//        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
//       }finally{
//           sourceChannel.close();
//           destChannel.close();
//   }
//} 
	// ------------------END CODE TWO------------------------
	

private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
    Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
}


	
	public static void main(String[]args) {
		  
		Ejemplos ej = new Ejemplos();
		 
		try {
			ej.copyFileUsingJava7Files(new File("C:\\Users\\HP-EliteBook\\Desktop\\sorce/ej.txt"), new File("C:\\Users\\HP-EliteBook\\Desktop\\target/ej.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("problemas para encontrar el archivo");
			e.printStackTrace();
			
		}
	}
	


}
