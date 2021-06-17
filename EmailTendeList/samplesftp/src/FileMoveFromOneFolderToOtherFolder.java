
//Java program to illustrate renaming and 
//moving a file permanently to a new loaction 
import java.io.*; 
import java.nio.file.Files; 
import java.nio.file.*; 

public class FileMoveFromOneFolderToOtherFolder
{ 
	public static void main(String[] args) throws IOException 
	{ 
		/*
		 * String sPath="D:\\appdata\\SupplierDocuments\\sundar\\9140Folder\\Items.xls";
		 * String sFileName="Items.xls"; String
		 * path=sPath.substring(0,sPath.lastIndexOf('\\')+1); String
		 * name=sFileName.substring(0,sFileName.lastIndexOf('.'))+"-1"+sFileName.
		 * substring(sFileName.lastIndexOf('.'),sFileName.length());
		 * System.out.println(path+name); System.out.println(name);
		 */
		
		  Path temp = Files.move (Paths.get(
		  "D:\\appdata\\SupplierDocuments\\sundar\\9140Folder\\supplier\\Items.xls"),
		  Paths.get(
		  "D:\\\\appdata\\\\SupplierDocuments\\\\sundar\\\\9140Folder\\Items.xls"));
		  
		  if(temp != null) { System.out.println("File renamed and moved successfully");
		  } else { System.out.println("Failed to move the file"); }
		 
		/*
		 * File file = new File(
		 * "D:\\\\appdata\\\\SupplierDocuments\\\\sundar\\\\9140Folder\\\\Items.xls");
		 * File newFile = new File(
		 * "D:\\\\appdata\\\\SupplierDocuments\\\\sundar\\\\9140Folder\\\\Items-1.xls");
		 * if(file.renameTo(newFile)){ System.out.println("File rename success");;
		 * }else{ System.out.println("File rename failed"); }
		 */
	} 
} 