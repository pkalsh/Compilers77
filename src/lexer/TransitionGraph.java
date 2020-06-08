package lexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

public class TransitionGraph {
	private ResultPrinter printer;
	private AlphabetSet alphabet;
	private ArrayList<StateNode> nodes;
	private ArrayList<Transition> transitions;
	private HashMap<SimpleEntry<Integer, Integer>, String> transition_map;
	private ArrayList<SimpleEntry<String, String> > tokens;
	private char prev_symbol;
	private StateNode prev_node;
	private StateNode now_node;
	private String buffer;
	private boolean is_error;
	
	public TransitionGraph(String filename) {
		this.printer = new ResultPrinter(filename);
		this.nodes = new ArrayList<StateNode>();
		this.transitions = new ArrayList<Transition>();
		this.transition_map = new HashMap<SimpleEntry<Integer, Integer>, String>();
		this.tokens = new ArrayList<SimpleEntry<String, String> >();
		readGraphInfoFile();
		this.alphabet = new AlphabetSet();
		this.prev_symbol = '\0';
		this.prev_node = null;
		// start node
		this.now_node = nodes.get(0);
		this.is_error = false;
		this.buffer = "";
	}
	public boolean isError() {
		return is_error;
	}
	
	private void readGraphInfoFile() {
		// DFA에 따라 수동으로 그래프 생성
		try{
			String path = TransitionGraph.class.getResource("").getPath();
		    File file = new File(path + "\\data\\graph_info.txt");
			      
		    //입력 스트림 생성
		    FileReader filereader = new FileReader(file);
		    //입력 버퍼 생성
			BufferedReader bufReader = new BufferedReader(filereader);
			String line = "";
			        
			while((line = bufReader.readLine()) != null) {
			        /*
			         * txt 파일 입력 form
			         * ex) N/integer
			         * 1번째 토큰: Node인지 Edge인지
			         * 2번째 토큰: Node라면 상태, Edge라면 조건
			         * 3번째 토큰: Node는 node 번호, Edge는 엣지 정보
			         * 4번째 토큰 이후: parameter
			         */
			        	
				String[] tokens = line.split("/");
			    if(tokens[0].matches("N")) {
			    	StateNode node = new StateNode(Integer.parseInt(tokens[2]), tokens[1]);
			        if(tokens.length == 4) {
			            String[] given_letters = tokens[2].split(",");
			            char[] to_chars = new char[given_letters.length];
			            for (int i = 0; i < to_chars.length; i++) {
			               	to_chars[i] = given_letters[i].charAt(0);
			            }
			            node.setKeywordIdentifier(to_chars);
			        }
			        nodes.add(node);
			    }
			    else {
			    	// tokens[0].matches("E")
			        String[] from_to = tokens[2].split(",");
			        int from = Integer.parseInt(from_to[0]);
			        int to = Integer.parseInt(from_to[1]);
			        Transition transition = new Transition(tokens[1], from, to);
			        nodes.get(from).setLinkedNode(nodes.get(to)); 
			        transition_map.put(new SimpleEntry<Integer, Integer>(from, to), tokens[1]);
			        transitions.add(transition);
			    }
			            
			}
		    //.readLine()은 끝에 개행문자를 읽지 않는다.            
			bufReader.close();
			filereader.close();

		} catch (FileNotFoundException e) {
			e.getStackTrace();
		} catch(IOException e){
			e.getStackTrace();
	     }

	}
	public ArrayList<SimpleEntry<String, String> >getTokenList() {
		return tokens;
	}
	
	public void recognizeTokens(char input_symbol, int linenum) {
		boolean no_valid_condition = true;
		ArrayList<StateNode> linked_nodes = now_node.getLinkedNode();
		
		if(input_symbol == '-' && !now_node.getState().matches("WHITESPACE")) {
			// - 숫자, 연산에 대한 예외처리
			// '숫자 - 숫자' or 'IDENTIFIER-숫자' 연산식에서 -를 ARITHMETIC으로 처리하기 위함 
			if(tokens.size()>1 &&
			   (tokens.get(tokens.size()-1).getKey().matches("IDENTIFIER") || 
			   tokens.get(tokens.size()-1).getKey().matches("INTEGER") ||
			   tokens.get(tokens.size()-1).getKey().matches("FLOAT"))) {
				now_node = nodes.get(0);
				buffer = "";
				tokens.add(new SimpleEntry<String, String>("ARITHMETIC", "-"));
				printer.printSymbolTable("ARITHMETIC", "-");
				return;
			}
		}
		
		for(int i = 0; i < linked_nodes.size(); i++) {
			int linked_id = linked_nodes.get(i).getId();
			//
			// System.out.println(now_node.getId() +" to " + linked_id);
			SimpleEntry<Integer, Integer> key = new SimpleEntry<Integer, Integer>(now_node.getId(), linked_id);
			String transition = transition_map.get(key);
		
			if(transition.matches("NotGivenLetter")) {
				if(alphabet.isValidCondition(transition, input_symbol, now_node.getKeywordIdentifier())) {
					buffer += input_symbol;
					now_node = nodes.get(linked_id);
					no_valid_condition = false;
					break;
				}
			}
			
			if(alphabet.isValidCondition(transition, input_symbol)) {			
				// 유효한 조건이 있으면 버퍼에 심볼을 추가하고 현재 노드를 그 노드로 갱신
				buffer += input_symbol;
				prev_symbol = input_symbol;
				prev_node = now_node;
				now_node = nodes.get(linked_id);
				no_valid_condition = false;
				break;
			}
		}
		
		if(no_valid_condition) {
			/*
			 * 더 이상 갈 수 있는 노드가 없으면
			 * 현재 노드가 종료 노드인지 아닌지에 따라 달라지는데
			 * 종료 노드이면 그 노드의 상태를 기록 (or 출력)하고 start node로 현재 노드를 갱신
			 * 그리고 시작 노드에서 input_symbol을 다시 검사
			 * 아니면 에러 메시지 출력하고 프로그램 종료.
			 * 
			 * start node인데 valid condition이 부재이면 에러 메시지 출력
			 */
			
			if(!now_node.getState().matches("none") && !now_node.getState().matches("start")) {
				if(buffer.matches("-0")) {
					tokens.add(new SimpleEntry<String, String>("ARITHMETIC", "-"));
					tokens.add(new SimpleEntry<String, String>("INTEGER", "0"));
					printer.printSymbolTable("ARITHMETIC", "-");
					printer.printSymbolTable("INTEGER", "0");
					now_node = nodes.get(0);
					buffer="";
					recognizeTokens(input_symbol, linenum);
					return;
				}
				
				tokens.add(new SimpleEntry<String, String>(now_node.getState(), buffer));
				printer.printSymbolTable(now_node.getState(), buffer);
				now_node = nodes.get(0);
				buffer="";
				recognizeTokens(input_symbol, linenum);
			}
			else if(now_node.getState().matches("none")) {
				// -0.00.0 같은 예시 처리 -0.00000.0 또는 -4.1290.4 / -4.129000.4
				// 완료
				if(prev_node.getState().matches("FLOAT")) {
					buffer = buffer.substring(0, buffer.length()-1);
					tokens.add(new SimpleEntry<String, String>("FLOAT", buffer));
					printer.printSymbolTable("FLOAT", buffer);
					now_node = nodes.get(0);
					buffer = "";
					recognizeTokens(prev_symbol, linenum);
					recognizeTokens(input_symbol, linenum);
				}
				else {
					is_error = true;
					printer.printErrorReport(linenum, buffer, input_symbol);
					now_node = nodes.get(0);
					buffer="";
				}
			}
			else if(now_node.getState().matches("start")){
				is_error = true;
				printer.printErrorReport(linenum, tokens.get(tokens.size()-1).getValue(), input_symbol);
				buffer="";
			}
			else {
				is_error = true;
				printer.printErrorReport(linenum, buffer);
				now_node = nodes.get(0);
				buffer="";
				recognizeTokens(input_symbol, linenum);
			}
		}
	}
	
}
class StateNode { 
	private int id;
	private String state;
	private ArrayList<StateNode> linked_node;
	private char[] keyword_identifier;
	
	public StateNode(int id) {
		this.id = id;
		this.state = "none";
		this.linked_node = new ArrayList<StateNode>();
		this.keyword_identifier = null;
	}
	public StateNode(int id, String state) {
		this.id = id;
		this.state = state;
		this.linked_node = new ArrayList<StateNode>();
		this.keyword_identifier = null;
	}
	public int getId() {
		return this.id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setLinkedNode(StateNode node) {
		linked_node.add(node);
	}
	public ArrayList<StateNode> getLinkedNode() {
		return linked_node;
	}
	public void setKeywordIdentifier(char[] given_letters) {
		keyword_identifier = given_letters;
	}
	public char[] getKeywordIdentifier() {
		return keyword_identifier;
	}
	
}

class Transition {
	private String condition;
	private int from_node;
	private int to_node;
	
	public Transition(String condition, int from_node, int to_node) {
		this.from_node = from_node;
		this.to_node = to_node;
		this.condition = condition;
	}

	public int getFromNode() {
		return from_node;
	}
	public void setFromNode(int from_node) {
		this.from_node = from_node;
	}
	public int getToNode() {
		return to_node;
	}
	public void setToNode(int to_node) {
		this.to_node = to_node;
	}
	public String getCondition() {
		return this.condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
}