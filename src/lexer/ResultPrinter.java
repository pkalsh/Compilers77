package lexer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultPrinter {
	File result_file;
	FileWriter writer;
	
	public ResultPrinter(String filename) {
		String path = ResultPrinter.class.getResource("").getPath();
		result_file = new File(path + "\\result\\" + filename + ".txt");
		writer = null;
	}
	public void printSymbolTable(String state, String value) {
		try {
			String token = null;
			// 기존 파일의 내용에 이어서 쓰려면 true를, 기존 내용을 없애고 새로 쓰려면 false를 지정한다.
			if(state.matches("IDENTIFIER") || state.matches("INTEGER") ||
					state.matches("FLOAT") || state.matches("LITERAL") ||
					state.matches("COMPARISON") || state.matches("BITWISE") ||
					state.matches("KEYWORD") || state.matches("DATATYPE") ||
					state.matches("BOOLEAN") || state.matches("ARITHMETIC")) {
				token = "<" + state +"," + value + ">\n";
			} else {
				token = "<" + state + ">\n";
			}
			writer = new FileWriter(result_file, true);
			writer.write(token);
			writer.flush();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
		    try {
		        if(writer != null) writer.close();
		    } catch(IOException e) {
		        e.printStackTrace();
		    }
		}

	}
	public void printErrorReport(int linenum, String now_string) {
		// -0 <INTEGER> 에러 리포트
		String error_message = "line " + linenum + " LEXICAL ERROR: Invalid token for " +  
				"\"" + now_string + "\"\n";
		System.out.print(error_message);
		try {
			writer = new FileWriter(result_file, true);
			writer.write(error_message);
			writer.flush();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer != null) writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void printErrorReport(int linenum, String prev_string, char symbol) {
		String error_message = "line " + linenum + " LEXICAL ERROR: Invalid token for " +  
							"\"" + prev_string + symbol + "\"\n";
		System.out.println(error_message);
		try {
			writer = new FileWriter(result_file, true);
			writer.write(error_message);
			writer.flush();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer != null) writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void printErrorReport(int linenum, String prev_string, String now_string) {
		// 00.534 에러 리포트 <INTEGER><FLOAT>
		String error_message = "line " + linenum + " LEXICAL ERROR: Invalid token for " +  
				"\"" + prev_string + now_string + "\"\n";
		System.out.print(error_message);
		try {
		writer = new FileWriter(result_file, true);
		writer.write(error_message);
		writer.flush();
		} catch(IOException e) {
		e.printStackTrace();
		} finally {
			try {
				if(writer != null) writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}


