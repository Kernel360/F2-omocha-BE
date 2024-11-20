package org.omocha.api.auth.dto;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.common.util.ValueObjectMapper;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR,
	uses = ValueObjectMapper.class
)
public interface AuthDtoMapper {

	@Mapping(target = "email", source = "email", qualifiedByName = "toEmail")
	MemberCommand.AddMember toCommand(String email, String password);

	@Mapping(target = "email", source = "memberLoginRequest.email", qualifiedByName = "toEmail")
	MemberCommand.MemberLogin toCommand(AuthDto.MemberLoginRequest memberLoginRequest);

	AuthDto.MemberDetailResponse toResponse(MemberInfo.MemberDetail memberDetailInfo);

}
