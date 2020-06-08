package parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.AbstractMap.SimpleEntry;

public class SyntaxAnalyzer {
	private ArrayList<SimpleEntry<String, String> > token_list;
	private ArrayList<Symbol> input_symbols;
	private Stack<Integer> stack;
	
	private int splitter_pos;
	private int next_symbol_pos;
	
	public SyntaxAnalyzer(ArrayList<SimpleEntry<String, String> > token_list) {
		this.token_list = token_list;
		this.input_symbols = new ArrayList<Symbol>();
		this.stack = new Stack<>();
		
		this.splitter_pos = -1;
		this.next_symbol_pos = 0;
		// start state를 스택에 넣는다. 
		this.stack.push(1);
		
		for(int i = 0; i < token_list.size(); i++) {
			if(!token_list.get(i).getKey().matches("whitespace")) {
				this.input_symbols.add(new Symbol(token_list.get(i).getKey()));
			}
			// add end marker
			this.input_symbols.add(new Symbol("$"));
		}
	}
	
	public void run() throws IOException {
		String path = SLRParsingTable.class.getResource("").getPath();
	    
		SLRParsingTable table = new SLRParsingTable(path + "\\data\\SLR_parsing_table.xlsx");
		
		while(!stack.empty()) {
			
		}
	}
	
}
