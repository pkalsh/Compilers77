package parser;

public class Symbol {
	private final String[] nonterminal_list =
	     {
	    		 "S", "CODE", "VDECL", "FDECL", "ARG", "MOREARGS", "BLOCK", "STMT", 
	    		 "ASSIGN", "RHS", "EXPR", "TERM", "FACTOR","COND", "RETURN", "ELSE"
		  };
	
	private final String[] terminal_list =
		{
				"vtype", "id", "num", "float", "literal", "if", "else", "while", "for",
				"return", "addsub", "multdiv", "assign", "comp", "semi", "comma", "lparen",
				"rparen", "lbrace", "rbrace", "$"
		};
	
	private String content;
	// terminal인지 non-terminal인지
	private String type;
	
	public Symbol(String token) {
		this.content = token;
		
		for(String nonterminal:nonterminal_list) {
			if(nonterminal.matches(token)) {
				this.type = "nonterminal";
			}
		}
		
		for(String terminal:terminal_list) {
			if(terminal.matches(token)) {
				this.type = "terminal";
			}
		}
	}

	public String getContent() {
		return content;
	}

	public String getType() {
		return type;
	}
	
	@Override
	public boolean equals(Object obj){
	     if((obj instanceof Symbol)&& obj != null) {
	    	 return this.content == ((Symbol)obj).content;
	     } else {
	    	 return false;
	     }
	  }  

	@Override
	public String toString() {
		return "Symbol [content=" + content + ", type=" + type + "]";
	}
	
	
}
