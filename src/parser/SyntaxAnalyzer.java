package parser;


import java.util.ArrayList;
import java.util.Stack;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;

public class SyntaxAnalyzer {
	private ArrayList<SimpleEntry<String, String> > token_list;
	private Stack<Integer> stack;
	private LinkedList<Symbol> input_symbols;
	private ReductionRule reduction_rule;
	
	
	public SyntaxAnalyzer(ArrayList<SimpleEntry<String, String> > token_list) {
		this.token_list = token_list;
		// reduction을 위해 앞에 add하는 연산이 필요하여 linked list 사용
		this.input_symbols = new LinkedList<Symbol>();
		this.stack = new Stack<>();
		this.reduction_rule = new ReductionRule();
		
		// start state를 스택에 넣는다. 
		this.stack.push(1);
		
		for(int i = 0; i < token_list.size(); i++) {
			if(!token_list.get(i).getKey().matches("whitespace")) {
				this.input_symbols.addLast(new Symbol(token_list.get(i).getKey()));
			}
		}
		
		// add end marker
		this.input_symbols.addLast(new Symbol("end"));
	}
	
	public void reduceSymbols(int CFG_num) {
		int current_symbol_pos = stack.size() - 2;
		int next_symbol_pos = stack.size() - 1;
		
		SimpleEntry<Integer, Symbol> rule = reduction_rule.CFG.get(CFG_num);
		int length = rule.getKey();
		Symbol reduced_symbol = rule.getValue();
		int remove_pos = current_symbol_pos - length + 1;
		
		for(int i = 0; i < length; i++) {
			stack.pop(); 
			input_symbols.remove(remove_pos);
		}
		input_symbols.add(remove_pos, reduced_symbol);
		for(Symbol sym:input_symbols) System.out.println(sym);
	}
	
	public void printErrorReport() {
		// TODO 에러리포트 출력하기
	}
	

	public void run() {
		String path = SLRParsingTable.class.getResource("").getPath();
		SLRParsingTable table = new SLRParsingTable(path + "\\data\\SLR_parsing_table.xlsx");
		
		int next_symbol_pos = 0;
		Symbol start_symbol = new Symbol("S");
				
		// start symbol S까지 reduction이 전부 수행되면 symbol list는 받아들여진다.
		while(!input_symbols.get(0).equals(start_symbol)) {
			// stack의 크기가 곧 splitter의 위치이다.
			int current_state = stack.peek();
			
			next_symbol_pos = stack.size() - 1;
			String cell = table.getCellValue(current_state, input_symbols.get(next_symbol_pos));
			
			if (cell == null) {
				printErrorReport();
				return;
			}
			
			if (cell.charAt(0) == 'S') {
				String[] cell_splited = cell.split(" ");
				int next_state = Integer.parseInt(cell_splited[1]);
				stack.add(next_state);
			} 
			else if (cell.charAt(0) == 'R') {
				String[] cell_splited = cell.split(" ");
				int rule_num = Integer.parseInt(cell_splited[1]);
				System.out.println(rule_num);
				reduceSymbols(rule_num);
			}
			else {
				int next_state = Integer.parseInt(cell);
				stack.add(next_state);
			}
		}
		
		System.out.println("Accept!!!");
	}
	
}
