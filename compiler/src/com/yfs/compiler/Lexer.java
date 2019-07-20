package com.yfs.compiler;

import java.util.Scanner;

/**
 * �ʷ�������
 * 
 * @author yfs
 *
 */
@SuppressWarnings("all")
public class Lexer {

	private static final int EOF = -1; // �ļ�ĩβ
	public static final int SEMI = 1; // �ֺ�
	public static final int PLUS = 2; // +
	public static final int TIMES = 3; // *
	public static final int LP = 4; // (
	public static final int RP = 5; // )
	public static final int NUM_OR_ID = 6; // ��ĸ��������

	private int lookAhead = -1; // ���������ö��ֵ֮һ,��ʾ��ǰ��text������ʲô
	private String current = ""; // ��ǰ�û�������ַ�����inputBuffer���ص���������ʣ��������current��
	private String inputBuffer = ""; // �������ڴ洢�û������루end֮ǰ���������ݣ�
	private int length; // text�ĳ���
	private String text; // ��������ν��"�������"�ַ���, ����ԭ�������symbol�����ţ�

	/**
	 * ���дʷ�������
	 */
	public void runLexer() {
		while (!match(EOF)) { // ֻҪ��ǰ����EOF
			System.out.println("Token: " + token() + ",Symbol: " + text); // ���ա���ǩ:�����ַ��������д�ӡ
			advance(); // ��ȡ��һ���ַ������з���
		}
	}

	private void advance() {
		lookAhead = lex();
	}

	private boolean match(int token) {
		if (lookAhead == -1) {
			lookAhead = lex();
		}
		return token == lookAhead;
	}

	private int lex() {
		while (true) {
			while ("".equals(current)) { // currentһ����ֵ��, ��Ҫ��ʼ������, ���ܼ���������
				Scanner scanner = new Scanner(System.in);
				while (true) {
					String line = scanner.nextLine();
					if ("end".equals(line)) { // �û��������"end"�Ļ�,�ͽ���
						break;
					}
					inputBuffer += line; // �洢�û����� end ֮ǰ����������, inputBuffer����������ö���
				}
				if ("".equals(inputBuffer)) {
					current = "";
					return EOF; // ������ڿ���̨�������� end�Ļ�, ������õ��ľ���EOF�� ��runLexer�ͻ����whileѭ����
				}
				current = inputBuffer; // ��ʼ, inputBuffer��û�б���ȡ, ����current���� inputBuffer
				current = current.trim();
			}
			for (int i = 0; i < current.length(); i++) {
				length = 0;
				text = current.substring(0, 1); // ���ڷ����ֺ���ĸ, ��text��������ַ�, ���������ֻ�����ĸ������Ҫ���������ȡ, ����default��չʾ������, i��Ҫ++,
												// text��Ҫ�仯
				switch (current.charAt(i)) {
				case ';':
					current = current.substring(1);
					return SEMI;
				case '+':
					current = current.substring(1);
					return PLUS;
				case '*':
					current = current.substring(1);
					return TIMES;
				case '(':
					current = current.substring(1);
					return LP;
				case ')':
					current = current.substring(1);
					return RP;
				case '\n':
				case '\t':
				case ' ':
					current = current.substring(1); // �����ص�
					break;
				default: // ��ĸ��������
					if (isAlplhaOrNum(current.charAt(i))) { // �������ĸ��������
						while (isAlplhaOrNum(current.charAt(i))) {
							i++;
							length++;
						}
						text = current.substring(0, length);
						current = current.substring(length);
						return NUM_OR_ID;
					}
				}
			}
		}
	}

	/**
	 * �ж��Ƿ�����ĸ��������
	 * 
	 * @param c
	 * @return
	 */
	private boolean isAlplhaOrNum(char c) {
		return Character.isAlphabetic(c) || Character.isDigit(c);
	}

	/**
	 * ����ԭ����˵��token���Ǵ��ϵı�ǩ��symbol���Ƕ�Ӧ����������ַ���
	 * 
	 * @return
	 */
	private String token() {
		switch (lookAhead) {
		case EOF:
			return "EOF";
		case PLUS:
			return "PLUS";
		case TIMES:
			return "TIMES";
		case NUM_OR_ID:
			return "NUM_OR_ID";
		case SEMI:
			return "SEMI";
		case LP:
			return "LP";
		case RP:
			return "RP";
		}
		return "";
	}

}
/*
 * �������� 1+2*3+4; end
 * 
 * ���� end
 */
