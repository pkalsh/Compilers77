package parser;

public class Symbol {
	private final String[] nonterminal_list =
	     {
	    		 "CODE", "VDECL", "FDECL", "ARG", "MOREARGS", "BLOCK", "STMT", 
	    		 "ASSIGN", "RHS", "EXPR", "TERM", "FACTOR","COND", "RETURN", "ELSE"
		  };
	
	private final String[] terminal_list =
		{
				"vtype", "id", "num", "float", "literal", "if", "else", "while", "for",
				"return", "addsub", "multdiv", "assign", "comp", "semi", "comma", "lparen",
				"rparen", "lbrace", "rbrace", "$"
		};
	
	private String content;
	
	public Symbol(String token) {
		this.content = token;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Symbol [content=" + content + "]";
	}
	
	
}
