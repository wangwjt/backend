package com.yfs.compiler;

/**
 * ������
 * 
 * @author yfs
 *
 */
public class Compiler {
	
	public static void main(String[] args) {
		Lexer lexer = new Lexer();
		BasicParser parser = new BasicParser(lexer);
		parser.statements(); // �﷨����
	}

}
