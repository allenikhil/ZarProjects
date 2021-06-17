
// Java program to illustrate Copying the file 
// and deleting the original file 
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;

public class Test {
	public static void main(String[] args) throws IOException {
		/*
		 * File source = new File(
		 * "D:\\appdata\\SupplierDocuments\\testuser1\\8046Folder\\supplier\\prog_jpeg.jpg"
		 * ); // renaming the file and moving it to a new location File dest = new File(
		 * "D:\\appdata\\SupplierDocuments\\testuser1\\8046Folder\\supplier\\prog_jpeg-1.jpg"
		 * );
		 * 
		 * if(source.exists()) { FileInputStream fis = null; try { FileOutputStream fos
		 * = new FileOutputStream(dest); fis = new FileInputStream(source);
		 * 
		 * byte[] buffer = new byte[1024]; int length;
		 * 
		 * while ((length = fis.read(buffer)) > 0) {
		 * 
		 * fos.write(buffer, 0, length); } fos.close(); fis.close(); } catch (Exception
		 * e) {} }
		 * 
		 * try (FileInputStream fis = new FileInputStream(source); FileOutputStream fos
		 * = new FileOutputStream(dest)) {
		 * 
		 * byte[] buffer = new byte[1024]; int length;
		 * 
		 * while ((length = fis.read(buffer)) > 0) {
		 * 
		 * fos.write(buffer, 0, length); } }
		 * 
		 * 
		 * Path result = Files.move(Paths.get(
		 * "D:\\appdata\\SupplierDocuments\\testuser1\\8046Folder\\supplier\\prog_jpeg-1.jpg"
		 * ),Paths.get(
		 * "D:\\appdata\\SupplierDocuments\\testuser1\\8046Folder\\prog_jpeg.jpg"));
		 * if(result!=null) { System.out.println("Done"); }
		 */
		
	//	Path sourceDirectory = Paths.get("D:\\appdata\\SupplierDocuments\\testuser1\\8046Folder\\supplier\\\\prog_jpeg.jpg");
      //  Path targetDirectory = Paths.get("D:\\appdata\\SupplierDocuments\\testuser1\\8046Folder\\prog_jpeg.jpg");

        //copy source to target using Files Class
      //  Files.copy(sourceDirectory, targetDirectory);
		/*
		 * BigInteger f=new BigInteger("12323"); BigInteger f1=new BigInteger("123223");
		 * System.out.println(f.add(f1)); System.out.println(f.multiply(val));
		 */
        
      //  new File("C:\\Users\\Nikhil\\Downloads\\test").mkdir(); for creating new directory
        
		int h=1;
		System.out.printf("%02d",h);
		 
		
		//copy  directory and files in that directory
        File source = new File("C:\\Users\\Nikhil\\Downloads\\test");
        File dest = new File("C:\\Users\\Nikhil\\Desktop\\test");

        try {
            FileUtils.copyDirectory(source,dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
      //copy only directory not files in that directory
        Path sourceLocation = Paths.get("C:\\Users\\Nikhil\\Downloads\\test");
        Path targetLocation = Paths.get("C:\\Users\\Nikhil\\Desktop\\test1");
        Files.copy(sourceLocation, targetLocation,StandardCopyOption.REPLACE_EXISTING); 
        
        System.out.println("completed");
	}
}
