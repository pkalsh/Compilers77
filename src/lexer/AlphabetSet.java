package lexer;

public class AlphabetSet {
	public boolean isValidCondition(String condition, char c) {
		if(condition.matches("KeywordLetter")) return isKeywordLetter(c);
		if(condition.matches("NotKeywordLetter")) return isNotKeywordLetter(c);
		if(condition.matches("Underscore")) return isUnderscore(c);
		if(condition.matches("Whitespace")) return isWhitespace(c);
		if(condition.matches("LessThan")) return isLessThan(c);
		if(condition.matches("GreaterThan")) return isGreaterThan(c);
		if(condition.matches("Equal")) return isEqualSymbol(c);
		if(condition.matches("Not")) return isNotSymbol(c);
		if(condition.matches("AndOr")) return isAndOr(c);
		if(condition.matches("Zero")) return isZero(c);
		if(condition.matches("Nonzero")) return isNonZero(c);
		if(condition.matches("Digit")) return isDigit(c);
		if(condition.matches("Dot")) return isDot(c);
		if(condition.matches("Minus")) return isMinusSign(c);
		if(condition.matches("DoubleQuote")) return isDoubleQuote(c);
		if(condition.matches("Comma")) return isComma(c);
		if(condition.matches("Arithmetic")) return isArithmeticOperation(c);
		if(condition.matches("Termination")) return isTermination(c);
		if(condition.matches("LBracket")) return isLBracket(c);
		if(condition.matches("RBracket")) return isRBracket(c);
		if(condition.matches("LParen")) return isLParen(c);
		if(condition.matches("RParen")) return isRParen(c);
		if(condition.matches("A")) return isALetter(c);
		if(condition.matches("B")) return isBLetter(c);
		if(condition.matches("C")) return isCLetter(c);
		if(condition.matches("E")) return isELetter(c);
		if(condition.matches("F")) return isFLetter(c);
		if(condition.matches("H")) return isHLetter(c);
		if(condition.matches("I")) return isILetter(c);
		if(condition.matches("L")) return isLLetter(c);
		if(condition.matches("N")) return isNLetter(c);
		if(condition.matches("O")) return isOLetter(c);
		if(condition.matches("R")) return isRLetter(c);
		if(condition.matches("S")) return isSLetter(c);
		if(condition.matches("T")) return isTLetter(c);
		if(condition.matches("U")) return isULetter(c);
		if(condition.matches("W")) return isWLetter(c);
		if(condition.matches("Letter")) return isLetter(c);
		if(condition.matches("Blank")) return isBlank(c);
		return false;
	}
	public boolean isValidCondition(String condition, char c, char[] toKeywordLetters) {
		return isLetter(c) && isNotGivenLetter(c, toKeywordLetters);
	}
	
	public static boolean isNotGivenLetter(char c, char[] toKeywordLetters) {
		for(char kl: toKeywordLetters) {
			if (c == kl) return false;
		}
		return true;
	}
	public static boolean isLetter(char c) {
		return ((int)c >= (int)('a') && (int)c <= (int)('z')) ||
			   ((int)c >= (int)('A') && (int)c <= (int)('Z'));
	}
	public static boolean isKeywordLetter(char c) {
		// keyword, boolean value, data type의 앞문자들
		return (c == 'b' || c == 'c' || c == 'e' ||
				c == 'f' || c == 'i' || c == 'r' ||
				c == 't' || c == 'w');
	}
	public static boolean isNotKeywordLetter(char c) {
		// keyword, boolean value, data type의 앞문자들을 제외한 letters
		return isLetter(c) && !isKeywordLetter(c);
	}
	public static boolean isALetter(char c) {
		return c == 'a';
	}
	public static boolean isBLetter(char c) {
		return c == 'b';
	}
	public static boolean isCLetter(char c) {
		return c == 'c';
	}
	public static boolean isELetter(char c) {
		return c == 'e';
	}
	public static boolean isFLetter(char c) {
		return c == 'f';
	}
	public static boolean isHLetter(char c) {
		return c == 'h';
	}
	public static boolean isILetter(char c) {
		return c == 'i';
	}
	public static boolean isLLetter(char c) {
		return c == 'l';
	}
	public static boolean isNLetter(char c) {
		return c == 'n';
	}
	public static boolean isOLetter(char c) {
		return c == 'o';
	}
	public static boolean isRLetter(char c) {
		return c == 'r';
	}
	public static boolean isSLetter(char c) {
		return c == 's';
	}
	public static boolean isTLetter(char c) {
		return c == 't';
	}
	public static boolean isULetter(char c) {
		return c == 'u';
	}
	public static boolean isWLetter(char c) {
		return c == 'w';
	}
	
	
	public static boolean isZero(char c) {
		return (int)c == (int)('0');
	}
	public static boolean isNonZero(char c) {
		return (int)c >= (int)('1') && (int)c <= (int)('9');
	}
	public static boolean isDigit(char c) {
		return isZero(c) || isNonZero(c);
	}
	public static boolean isLBracket(char c) {
		return c == '{';
	}
	public static boolean isRBracket(char c) {
		return c == '}';
	}
	public static boolean isLParen(char c) {
		return c == '(';
	}
	public static boolean isRParen(char c) {
		return c == ')';
	}
	public static boolean isUnderscore(char c) {
		return c == '_';
	}
	public static boolean isDoubleQuote(char c) {
		return c == '\"';
	}
	public static boolean isSingleQuote(char c) {
		return c == '\'';
	}
	public static boolean isNotSymbol(char c) {
		return c == '!';
	}
	public static boolean isEqualSymbol(char c) {
		return c == '=';
	}
	public static boolean isLessThan(char c) {
		return c == '<';
	}
	public static boolean isGreaterThan(char c) {
		return c == '>';
	}
	public static boolean isAndOr(char c) {
		return c == '&' || c == '|';
	}
	public static boolean isComma(char c) {
		return c == ',';
	}
	public static boolean isTermination(char c) {
		return c == ';';
	}
	public static boolean isDot(char c) {
		return c == '.';
	}
	public static boolean isWhitespace(char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r';
	}
	public static boolean isBlank(char c) {
		return c == ' ';
	}
	public static boolean isMinusSign(char c) {
		return c == '-';
	}
	public static boolean isArithmeticOperation(char c) {
		// -를 제외한 산술 연산
		return c == '+' || c == '*' || c == '/';
	}
	
}
