// This class is a recursive-descent parser,
// modeled after the programming language's grammar.
// It constructs and has-a Scanner for the program
// being parsed.

import java.util.*;

/**
 * Parses the tokens that were tokenized by the scanner and performs
 * the appropriate operations. They heart of the program. 
 * 
 * @author Michael Green
 *
 */
public class Parser {

    private Scanner scanner;
    private java.util.Scanner scan = new java.util.Scanner(System.in);

    private void match(String s) throws SyntaxException {
	scanner.match(new Token(s));
    }

    private Token curr() throws SyntaxException {
	return scanner.curr();
    }

    private int pos() {
	return scanner.pos();
    }
    
    private NodeUop parseUop() throws SyntaxException{
    	if(curr().equals(new Token("-"))){
    		match("-");
    		return new NodeUop(pos(),"-");
    	}
    	return null;
    }
    
    private NodeRelop parseRelop() throws SyntaxException{
    	if(curr().equals(new Token("<"))){
    		match("<");
    		return new NodeRelop(pos(),"<");
    	}
    	if(curr().equals(new Token("<="))){
    		match("<=");
    		return new NodeRelop(pos(),"<=");
    	}
    	if(curr().equals(new Token(">"))){
    		match(">");
    		return new NodeRelop(pos(),">");
    	}
    	if(curr().equals(new Token(">="))){
    		match(">=");
    		return new NodeRelop(pos(),">=");
    	}
    	if(curr().equals(new Token("<>"))){
    		match("<>");
    		return new NodeRelop(pos(),"<>");
    	}
    	if(curr().equals(new Token("=="))){
    		match("==");
    		return new NodeRelop(pos(),"==");
    	}
    	return null;
    
    }
    
    private NodeMulop parseMulop() throws SyntaxException {
	if (curr().equals(new Token("*"))) {
	    match("*");
	    return new NodeMulop(pos(),"*");
	}
	if (curr().equals(new Token("/"))) {
	    match("/");
	    return new NodeMulop(pos(),"/");
	}
	return null;
    }

    private NodeAddop parseAddop() throws SyntaxException {
	if (curr().equals(new Token("+"))) {
	    match("+");
	    return new NodeAddop(pos(),"+");
	}
	if (curr().equals(new Token("-"))) {
	    match("-");
	    return new NodeAddop(pos(),"-");
	}
	return null;
    }

    private NodeFact parseFact() throws SyntaxException {
	if (curr().equals(new Token("("))) {
	    match("(");
	    NodeExpr expr=parseExpr();
	    match(")");
	    return new NodeFactExpr(expr);
	}
	if (curr().equals(new Token("id"))) {
	    Token id=curr();
	    match("id");
	    return new NodeFactId(pos(),id.lex());
	}
	
	if(curr().equals(new Token("num"))){
		Token num=curr();
		match("num");
		return new NodeFactNum(num.lex());
	}
	
	
	NodeUop uop =parseUop();
	NodeFact fact = parseFact();
	return new NodeFactUop(uop, fact);
    }

    private NodeTerm parseTerm() throws SyntaxException {
	NodeFact fact=parseFact();
	NodeMulop mulop=parseMulop();
	if (mulop==null)
	    return new NodeTerm(fact,null,null);
	NodeTerm term=parseTerm();
	term.append(new NodeTerm(fact,mulop,null));
	return term;
    }

    private NodeExpr parseExpr() throws SyntaxException {
	NodeTerm term=parseTerm();
	NodeAddop addop=parseAddop();
	if (addop==null)
	    return new NodeExpr(term,null,null);
	NodeExpr expr=parseExpr();
	expr.append(new NodeExpr(term,addop,null));
	return expr;
    }

    private NodeAssn parseAssn() throws SyntaxException {
	Token id=curr();
	match("id");
	match("=");
	NodeExpr expr=parseExpr();
	NodeAssn assn=new NodeAssn(id.lex(),expr);
	return assn;
    }

    private NodeStmtAssn parseStmtAssn() throws SyntaxException {
    	NodeAssn assn=parseAssn();
    	NodeStmtAssn stmt=new NodeStmtAssn(assn);
    	return stmt;
    }

    public Node parse(String program) throws SyntaxException {
	scanner=new Scanner(program);
	scanner.next();
	return parseProg();
    }
    
    private NodeStmt parseStmt() throws SyntaxException {
    if(curr().equals(new Token("rd"))){
    	//Token rd=curr();
	    match("rd");
	    Token id = curr();
	    match("id");
    	Double toRead = scan.nextDouble();
    	
    	return new NodeStmtRdId(id.lex(),toRead);
    }
    if(curr().equals(new Token("wr"))){
    	 
    	 match("wr");
    	 NodeFact fact=parseFact();
    	
    	 return new NodeStmtWrExpr(fact);
    }
    
    if(curr().equals(new Token("if"))){
    	match("if");
    	NodeBoolExpr boolExpr = parseBoolExpr();
   		match("then");
   		NodeStmt stmt = parseStmt();
   		if(curr().equals(new Token("else"))){
   			match("else");
   			NodeStmt stmt2 = parseStmt();
   			return new NodeStmtIfBoolExprThenStmtElseStmt(boolExpr,stmt,stmt2);
   		}else{
   			return new NodeStmtIfBoolExprThenStmt(boolExpr,stmt);
   		}
    }
    
    if(curr().equals(new Token("while"))){
    	match("while");
    	NodeBoolExpr boolExpr = parseBoolExpr();
    	match("do");
    	NodeStmt stmt = parseStmt();
    	
    	return new NodeStmtWhileBoolExprDoStmt(boolExpr,stmt);
    	
    	
    }
    if(curr().equals(new Token("begin"))){
    	match("begin");
    	NodeBlock block = parseBlock();
    	match("end");
    	
    	
    	return new NodeStmtBeginBlockEnd(block);
    	
    	
    }
    
    NodeStmt assn=parseStmtAssn();
	return assn;
    }
    
    private NodeBoolExpr parseBoolExpr() throws SyntaxException{
    	NodeExpr expr1 = parseExpr();
    	NodeRelop relop = parseRelop();
    	NodeExpr expr2 = parseExpr();
    	
    	return new NodeBoolExpr(expr1, relop, expr2);
    }
    private NodeProg parseProg() throws SyntaxException{
    	NodeBlock block = parseBlock();
    	return new NodeProg(block);
    }
    
    private NodeBlock parseBlock() throws SyntaxException{
    	NodeStmt stmt = parseStmt();
    	if(curr().equals(new Token(";"))){
    		NodeBlock block = null;
    		match(";");
    		block = parseBlock();
    		return new NodeBlockStmtBlock(stmt,block);
    	}
    
    	return new NodeBlockStmt(stmt);
    	
    	
    }

}
