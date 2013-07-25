/**
 * 
 */
package apache_commons;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POITest {

	public static void main(String[] args) throws Exception {
		//
		// An excel file name. You can create a file name with a full
		// path information.
		//
		String fileName = "c:/temp/test.xlsx";

		List<List<Cell>> sheetData = new ArrayList<List<Cell>>();
		InputStream is = null;
		
		try {
			is =  new FileInputStream(fileName); 
			XSSFWorkbook workBook = new XSSFWorkbook(is); 
			XSSFSheet sheetx = workBook.getSheetAt(0); 
			Iterator<Row> rows = sheetx.rowIterator();

			while (rows.hasNext()) {
				Row row =   rows.next();
				Iterator<Cell> cells = row.cellIterator();

				List<Cell> data = new ArrayList<Cell>();
				while (cells.hasNext()) {
					Cell cell =  cells.next();
					data.add(cell);
				}

				sheetData.add(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
		}

		showExelData(sheetData);
	}

	private static void showExelData(List<List<Cell>> rows) {

		for (int i = 0; i < rows.size(); i++) {
			List<Cell> cells = rows.get(i);
			 for (int j = 0; j < cells.size(); j++) {	
				 String val = "";
				 Cell cell = cells.get(j);
				 int type = cell.getCellType();
				 if (type == Cell.CELL_TYPE_STRING) {
					 val = cell.getStringCellValue();
				 } else  if (type == Cell.CELL_TYPE_NUMERIC) {
					 val = cell.getNumericCellValue() + "";
				 }   
				 System.out.print(val + ",  ");
			 }
			 
			System.out.println("");
		}
	}
}