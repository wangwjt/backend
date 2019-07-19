package com.yfs.compiler;

import static com.yfs.compiler.Lexer.*;

/**
 * �﷨�������� ��ʵ�����������̺��﷨��������һ����, ��ȫ����
 * 
 * @author yfs
 *
 */
public class BasicParser {

	private Lexer lexer;
	private boolean isLeagal = true; // ���ʽ�Ƿ�Ϸ�.

	public BasicParser(Lexer lexer) {
		this.lexer = lexer;
	}

	/**
	 * ����statements, ����1: statements -> '��' | expression;statements
	 */
	public void statements() {

		expression();

		if (lexer.match(SEMI)) { // �����ǰ�ַ��Ƿֺ�
			lexer.advance(); // �������ȡ��һ���ַ�
		} else {
			isLeagal = false; // ��һ���ַ����Ƿֺ�
			System.out.println("line " + lexer.lineno + ": Missing semicolon"); // ȱ�ٷֺ�
			return;
		}
		if (!lexer.match(EOF)) { // �����һ���ַ�����EOF��Ҳ����˵�������ݵĻ�
			statements(); // �ݹ����, ��������statements���μ�����1��
		}
		if (isLeagal) { // ������պϷ��Ļ�, ��Ϸ�
			System.out.println("The statements is legal.");
		}
	}

	/**
	 * expression ����Ϊ term expression' ����2: expression -> term expression'
	 */
	private void expression() {
		term();
		expr_prime();
	}

	/**
	 * ����3: expression' -> + term expression' | '��'
	 */
	private void expr_prime() {
		if (lexer.match(Lexer.PLUS)) { // �����ǰ�ַ��� + ,��
			lexer.advance(); // �������ȡһ���ַ�
			term();
			expr_prime(); // �ݹ����
		} else if (lexer.match(UNKNOWN_SYMBOL)) {
			isLeagal = false;
			System.out.println("Unknown symbol: " + lexer.text);
			return;
		} else {
			return; // '��'
		}
	}

	/**
	 * ����4: term -> factor term'
	 */
	private void term() {
		factor();
		term_prime();
	}

	/**
	 * ����5: term' -> * factor term' | '��'
	 */
	private void term_prime() {
		if (lexer.match(TIMES)) { // �����ǰ�ַ���*
			lexer.advance(); // ������ȡһ���ַ�
			factor();
			term_prime(); // �ݹ�
		} else if (lexer.match(UNKNOWN_SYMBOL)) {
			isLeagal = false;
			System.out.println("Unknown symbol: " + lexer.text);
			return;
		} else {
			return; // '��'
		}
	}

	/**
	 * ����6: factor -> number | (expression)
	 */
	private void factor() {
		if (lexer.match(NUM_OR_ID)) { // ������ֻ���ĸ
			lexer.advance(); // ���������ȡһ���ַ�
		} else if (lexer.match(LP)) { // �����������
			lexer.advance(); // �������ȡ(������)����һ���ַ�
			expression(); // ��ʼ����expression
			if (lexer.match(RP)) { // �����������
				lexer.advance(); // �������ȡ(������)����һ���ַ�
			} else { // �������ŵ���û��������,����
				isLeagal = false;
				System.out.println("line " + lexer.lineno + " Missing ).");
				return;
			}
		} else {
			isLeagal = false;
			System.out.println("illegal statements");
			return;
		}
	}
}
