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
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POITest {

	private List<List<Cell>> sheetData;
	public static void main(String[] args) throws Exception {

		String fileName = "c:/temp/excel-files/test_date.xlsx";

		List<List<Cell>> sheetData = sheetData(fileName, 0);
		showExelData(sheetData);
		List<String> header = header(sheetData.get(0));
		for (String ss : header) {
			System.out.println(ss);
		}
		List<Integer> types = columnType(sheetData.get(1));
		for (int ss : types) {
			System.out.println(ss);
		}
	}

	/**********************************************************
	 * New Spreadsheet upload screen
	 *********************************************************/
	static List<List<Cell>> sheetData(String fil, int num) {
		List<List<Cell>> sheetData = new ArrayList<List<Cell>>();
		InputStream is = null;
		
		try {
			is =  new FileInputStream(fil); 
			XSSFWorkbook workBook = new XSSFWorkbook(is); 
			XSSFSheet sheetx = workBook.getSheetAt(num); 
			Iterator<Row> rows = sheetx.rowIterator();
			int oo = 0;
			out:
			while (rows.hasNext()) {
				Row row =   rows.next();
				Iterator<Cell> cells = row.cellIterator();

				List<Cell> data = new ArrayList<Cell>();
				while (cells.hasNext()) {
					Cell cell =  cells.next();
					data.add(cell);
				}

				sheetData.add(data);
				if (oo++ > 0) {
					break out;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sheetData;
	}
	
	private static void showExelData(List<List<Cell>> rows) {

		DataFormatter fmt = new DataFormatter();
		for (int i = 0; i < rows.size(); i++) {
			List<Cell> cells = (List<Cell>)rows.get(i);
			 for (int j = 0; j < cells.size(); j++) {	
				 String val = "";
				 Cell cell = cells.get(j);
				 int type = cell.getCellType();
				 if (type == Cell.CELL_TYPE_STRING) {
					 val = cell.getStringCellValue();
				 } else  if (type == Cell.CELL_TYPE_NUMERIC) {
					 
					 val = cell.getNumericCellValue() + "";
					 if (DateUtil.isCellDateFormatted(cell)) {
						 val = fmt.formatCellValue(cell) ;
					 } 
				 }   
				 System.out.print(val + ",  ");
			 }
			 
			System.out.println("");
		}
	}
	

	private static List<Integer> columnType(List<Cell> cells) {
		List<Integer> types= new ArrayList<Integer>();

		for (Cell cell : cells) {

			int type = cell.getCellType();
			if (type == Cell.CELL_TYPE_NUMERIC) {
				if (DateUtil.isCellDateFormatted(cell)) {
					type = Cell.CELL_TYPE_FORMULA; // 2 date
				}
			}
			types.add(type); // 1-string, 0-double

		}

		System.out.println("");
		return types;

	}
	
	/** header row */
	private static List<String> header(List<Cell> cells) {
		List<String> strs = new ArrayList<String>();

		int col = 0;
		for (Cell cell : cells) {
			col++;
			int type = cell.getCellType();
			String name = "COL" + col;
			 if (type == Cell.CELL_TYPE_STRING) {
				 name = cell.getStringCellValue();
			 }  
			 strs.add( name ); 
		}

		System.out.println("");
		return strs;

	}
}