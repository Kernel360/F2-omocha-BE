package org.omocha.infra.mail.template;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.omocha.domain.mail.exception.MailTemplateFailException;
import org.omocha.domain.member.vo.Email;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class AuthCodeTemplate implements TemplateStrategy {

	@Value("{OMOCHA_EMAIL}")
	private String fromEmail;

	private final FreeMarkerConfigurationFactoryBean configurationFactoryBean;

	public AuthCodeTemplate(
		@Qualifier("freeMarkerConfigurationFactoryBean") FreeMarkerConfigurationFactoryBean configurationFactoryBean) {
		this.configurationFactoryBean = configurationFactoryBean;

	}

	@Override
	public MimeMessage createMimeMessage(Email email, String code, MimeMessage mimeMessage) {

		try {
			Configuration configuration = configurationFactoryBean.createConfiguration();

			Template template = configuration.getTemplate("AuthCode.ftl");

			Map<String, Object> model = new HashMap<>();
			model.put("code", code);

			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());

			mimeMessageHelper.setFrom(fromEmail);
			mimeMessageHelper.setTo(email.getValue());
			mimeMessageHelper.setSubject("인증 코드");
			mimeMessageHelper.setText(content, true);

		} catch (IOException | TemplateException | MessagingException e) {
			MailTemplateFailException mtfe = new MailTemplateFailException(email);
			mtfe.initCause(e);
			throw mtfe;
		}

		return mimeMessage;

	}

}
