package edu.ben.util;

import java.util.Random;

public class CodeGenerator {
	
	public static String generateCode() {

		Random random = new Random();
		String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String code = "";

		while (code.length() < 10) {
			int loc = (int) (random.nextDouble() * possibleChars.length());
			code = code + possibleChars.charAt(loc);
		}

		return code;
	}

}
