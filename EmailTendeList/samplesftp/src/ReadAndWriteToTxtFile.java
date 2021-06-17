
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadAndWriteToTxtFile {
	final static File folder = new File("D:/SftpFiles");

	public static void main(String[] args) {

		for (final File fileEntry : folder.listFiles()) {
			try {
				FileInputStream file = new FileInputStream(new File("D:/SftpFiles/" + fileEntry.getName()));
				
				StringBuffer sb = new StringBuffer();

				// Create Workbook instance holding reference to .xlsx,.xls file
				XSSFWorkbook workbook = new XSSFWorkbook(file);

				// Get first/desired sheet from the workbook
				XSSFSheet sheet = workbook.getSheetAt(1);

				// Iterate through each rows one by one
				Iterator<Row> rowIterator = sheet.iterator();

				int i = sheet.getLastRowNum();// total no of rows in sheet
				int d = 0;// for how many rows of data present in file

				System.out.println("Sheet Row Length is:" + i);
				ArrayList<String> sheetData = new ArrayList<>();// to store sheet data

				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					// For each row, iterate through all the columns
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						// Check the cell type and format accordingly
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							System.out.println(cell.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							if (cell.getStringCellValue().equals("MERCHANT TRANS REF")
									|| cell.getStringCellValue().equals("SETTLEMENT DATE")
									|| cell.getStringCellValue().equals("RRN NO")
									|| cell.getStringCellValue().equals("ARN")) {
								// System.out.println(cell.getStringCellValue()+": Row
								// number:"+row.getRowNum()+" and Column number:"+cell.getColumnIndex());
								System.out.println(cell.getStringCellValue());
								d = i - 3;
								for (int k = 1; k <= d; k++) {
									sheetData.add(sheet.getRow(row.getRowNum() + k).getCell(cell.getColumnIndex())
											.getStringCellValue());
								}
							}
							break;
						}// end of switch
					} // end of cell iterator while loop
				} // end of row iterator while loop
				file.close();

				// System.out.println(sheetData);
				if (sheetData.isEmpty() == false) {
					for (int k = 0; k < d; k++) {
						for (int j = k; j < sheetData.size();) {
							sb.append(sheetData.get(j) + "^");
							j = j + d;
						}
						sb.replace(sb.lastIndexOf("^"), sb.lastIndexOf("^") + 1, "|");
					}
					sb.replace(sb.lastIndexOf("|"), sb.lastIndexOf("|") + 1, " ");
				} else {
					System.out.println("In Sheet Data is Not Found");
				}

				System.out.println("fileName:" + fileEntry.getName());
				System.out.println("Data:" + sb);
				System.out.println(writeUsingFileWriter(sb.toString(),fileEntry.getName().substring(0, fileEntry.getName().lastIndexOf("."))));
				System.out.println();
			} // end of try
			catch (Exception e) {
				e.printStackTrace();
			}
		} // end of for loop
	}// end of main method

	private static String writeUsingFileWriter(String data,String fileName) {
		File file = new File("D:/SftpTxtFiles/"+fileName+".txt");
		FileWriter fr = null;
		try {
			fr = new FileWriter(file);
			fr.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close resources
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "Data inserted into " + file + " Successfully";
	}
}