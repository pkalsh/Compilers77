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
		try {
			if (sheet.getRow(1) != null) {
				if (next_symbol.getType().matches("terminal")) {
					for(short col = ACTION_START_COL; col <= ACTION_END_COL; col++) {
						XSSFCell cell = sheet.getRow(1).getCell(col);
						if(cell.getStringCellValue().matches(next_symbol.getContent())) {
							return sheet.getRow(current_state + 1).getCell(col).getStringCellValue();
						};
					}
				}
				else {
					// nonterminal일 경우 cell 값을 숫자 값으로 가져온다.
					for(short col = GOTO_START_COL; col <= GOTO_END_COL; col++) {
						XSSFCell cell = sheet.getRow(1).getCell(col);
						if(cell.getStringCellValue().matches(next_symbol.getContent())) {
							// getNumericCellValue는 double 값을 가져오므로 int로 형변환하여 String 값으로 변환하고 그 값을 반환한다.
							return Integer.toString((int)sheet.getRow(current_state + 1).getCell(col).getNumericCellValue());
						};
					}
				}
			}
		} catch(java.lang.NullPointerException e) {
			return null;
		}
		return null;
	}

}

