package org.omocha.domain.mail;

import java.util.UUID;

public class CodeGenerate {

	public String getCode() {
		return String.valueOf(UUID.randomUUID());
	}

}
