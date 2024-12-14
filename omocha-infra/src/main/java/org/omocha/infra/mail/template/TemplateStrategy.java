package org.omocha.infra.mail.template;

import org.omocha.domain.member.vo.Email;

import jakarta.mail.internet.MimeMessage;

public interface TemplateStrategy {

	MimeMessage createMimeMessage(Email email, String code, MimeMessage mimeMessage);
}
