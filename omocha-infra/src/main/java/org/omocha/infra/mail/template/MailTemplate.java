package org.omocha.infra.mail.template;

import java.util.Map;

import org.omocha.domain.member.vo.Email;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class MailTemplate {

	private final Map<String, TemplateStrategy> templateStrategies;

	public MailTemplate(Map<String, TemplateStrategy> templateStrategies) {
		this.templateStrategies = templateStrategies;

	}

	public MimeMessage getMimeMessage(String templateName, Email email, String code, MimeMessage mimeMessage) {
		TemplateStrategy templateStrategy = templateStrategies.get(templateName);
		return templateStrategy.createMimeMessage(email, code, mimeMessage);
	}

}
