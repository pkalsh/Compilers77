package parser;

import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

public class ReductionRule {
	// CFG rule 번호 -> <RHS symbol 길이, LHS symbol>
	public HashMap<Integer, SimpleEntry<Integer, Symbol>> CFG;
	public ReductionRule() {
		CFG = new HashMap<Integer, SimpleEntry<Integer, Symbol> >();
		CFG.put(1, new SimpleEntry<Integer, Symbol>(1, new Symbol("S")));
		CFG.put(2, new SimpleEntry<Integer, Symbol>(2,new Symbol("CODE")));
		CFG.put(3, new SimpleEntry<Integer, Symbol>(2,new Symbol("CODE")));
		CFG.put(4, new SimpleEntry<Integer, Symbol>(0,new Symbol("CODE")));
		CFG.put(5, new SimpleEntry<Integer, Symbol>(3,new Symbol("VDECL")));
		CFG.put(6, new SimpleEntry<Integer, Symbol>(3,new Symbol("VDECL")));
		CFG.put(7, new SimpleEntry<Integer, Symbol>(9,new Symbol("FDECL")));
		CFG.put(8, new SimpleEntry<Integer, Symbol>(3,new Symbol("ARG")));
		CFG.put(9, new SimpleEntry<Integer, Symbol>(0,new Symbol("ARG")));
		CFG.put(10, new SimpleEntry<Integer, Symbol>(4,new Symbol("MOREARGS")));
		CFG.put(11, new SimpleEntry<Integer, Symbol>(0,new Symbol("MOREARGS")));
		CFG.put(12, new SimpleEntry<Integer, Symbol>(2,new Symbol("BLOCK")));
		CFG.put(13, new SimpleEntry<Integer, Symbol>(0,new Symbol("BLOCK")));
		CFG.put(14, new SimpleEntry<Integer, Symbol>(1,new Symbol("STMT")));
		CFG.put(15, new SimpleEntry<Integer, Symbol>(2,new Symbol("STMT")));
		CFG.put(16, new SimpleEntry<Integer, Symbol>(8,new Symbol("STMT")));
		CFG.put(17, new SimpleEntry<Integer, Symbol>(7,new Symbol("STMT")));
		CFG.put(18, new SimpleEntry<Integer, Symbol>(11,new Symbol("STMT")));
		CFG.put(19, new SimpleEntry<Integer, Symbol>(4,new Symbol("ELSE")));
		CFG.put(20, new SimpleEntry<Integer, Symbol>(0,new Symbol("ELSE")));
		CFG.put(21, new SimpleEntry<Integer, Symbol>(1,new Symbol("RHS")));
		CFG.put(22, new SimpleEntry<Integer, Symbol>(1,new Symbol("RHS")));
		CFG.put(23, new SimpleEntry<Integer, Symbol>(3,new Symbol("EXPR")));
		CFG.put(24, new SimpleEntry<Integer, Symbol>(1,new Symbol("EXPR")));
		CFG.put(25, new SimpleEntry<Integer, Symbol>(3,new Symbol("TERM")));
		CFG.put(26, new SimpleEntry<Integer, Symbol>(1,new Symbol("TERM")));
		CFG.put(27, new SimpleEntry<Integer, Symbol>(3,new Symbol("FACTOR")));
		CFG.put(28, new SimpleEntry<Integer, Symbol>(1,new Symbol("FACTOR")));
		CFG.put(29, new SimpleEntry<Integer, Symbol>(1,new Symbol("FACTOR")));
		CFG.put(30, new SimpleEntry<Integer, Symbol>(1,new Symbol("FACTOR")));
		CFG.put(31, new SimpleEntry<Integer, Symbol>(3,new Symbol("COND")));
		CFG.put(32, new SimpleEntry<Integer, Symbol>(3,new Symbol("RETURN")));
		CFG.put(33, new SimpleEntry<Integer, Symbol>(3,new Symbol("ASSIGN")));
	}
}
