package org.omocha.api.auth.dto;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.omocha.domain.member.vo.Email;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AuthDtoMapper {

	@Mapping(target = "email", source = "email", qualifiedByName = "toEmail")
	MemberCommand.AddMember toCommand(String email, String password);

	@Mapping(target = "email", source = "memberLoginRequest.email", qualifiedByName = "toEmail")
	MemberCommand.MemberLogin toCommand(AuthDto.MemberLoginRequest memberLoginRequest);

	@Named("toEmail")
	default Email toEmail(String email) {
		return new Email(email);
	}

	AuthDto.MemberDetailResponse toResponse(MemberInfo.MemberDetail memberDetailInfo);

}
