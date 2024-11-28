package org.omocha.api.mail.dto;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.common.util.ValueObjectMapper;
import org.omocha.domain.mail.MailCommand;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR,
	uses = ValueObjectMapper.class
)
public interface MailDtoMapper {

	@Mapping(target = "email", source = "email", qualifiedByName = "toEmail")
	MailCommand.SendMail toCommand(String email);

	@Mapping(target = "email", source = "email", qualifiedByName = "toEmail")
	MailCommand.VerifyMailCode toCommand(String email, String code);

}

