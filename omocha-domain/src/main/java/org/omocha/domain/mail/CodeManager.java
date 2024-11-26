package org.omocha.domain.mail;

import java.util.HashMap;
import java.util.Map;

public class CodeManager {

	protected static Map<String, String> codes = new HashMap<>();

	public static boolean checkCode(String email, String code) {

		if (codes.get(email).equals(code)) {
			codes.remove(email);
			return true;
		}
		return false;

	}

	public static String addCode(String email) {
		CodeGenerate codeGenerate = new CodeGenerate();
		String code = codeGenerate.getCode();
		codes.put(email, code);
		return code;
	}

}
