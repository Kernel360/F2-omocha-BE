package org.omocha.api.mail.dto;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.domain.mail.MailCommand;
import org.omocha.domain.member.vo.Email;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MailDtoMapper {

	MailCommand.SendMail toCommand(Email email);

	MailCommand.VerifyMailCode toCommand(Email email, String code);
}

