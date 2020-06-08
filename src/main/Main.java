package main;

import lexer.LexicalAnalyzer;
import parser.SyntaxAnalyzer;

public class Main {
	public static void main(String[] args) {
		LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(args[0]);
		lexicalAnalyzer.run();
		
		SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer.getTokenList());
		syntaxAnalyzer.run();
	}

}
