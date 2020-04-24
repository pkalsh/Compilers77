package lexer;

public class Main {
	public static void main(String[] args) {
		LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(args[0]);
		lexicalAnalyzer.run();
	}

}
