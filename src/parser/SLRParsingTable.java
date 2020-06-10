package parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lexer.TransitionGraph;
import parser.Symbol;

public class SLRParsingTable {	 
	private FileInputStream fis;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	private final int TERMINAL_NONTERMINAL_ROW = 2;
	private final int START_ROW = 3;
	private final int END_ROW = 84;
	private final short ACTION_START_COL = 1;
	private final short ACTION_END_COL = 21;
	private final short GOTO_START_COL = 22;
	private final short GOTO_END_COL = 36;
	
	public SLRParsingTable(String path){
		try {
			this.fis = new FileInputStream(path);
			this.workbook = new XSSFWorkbook(fis);
			// 첫번째 시트를 가져온다.
			this.sheet = this.workbook.getSheetAt(0);
		}
		catch(Exception e) { 
			e.printStackTrace();
		}
	}
	
	public String getCellValue(int current_state, Symbol next_symbol) {
		System.out.println(sheet.getPhysicalNumberOfRows());
		if (sheet.getRow(1) != null) {
			for(short col = ACTION_START_COL; col <= GOTO_END_COL; col++) {
				XSSFCell cell = sheet.getRow(0).getCell(col);
				if(cell.getRawValue().matches(next_symbol.getContent())) {
					return sheet.getRow(current_state + 2).getCell(col).getRawValue();
				}
				System.out.println(cell.getRawValue());
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		String path = SLRParsingTable.class.getResource("").getPath();
	    System.out.println(path);
		SLRParsingTable table = new SLRParsingTable(path + "\\data\\SLR_parsing_table.xlsx");
		System.out.println(table.getCellValue(1, new Symbol("VDECL")));
	}
}

