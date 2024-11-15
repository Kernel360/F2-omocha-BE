package org.omocha.api.auth.dto;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AuthDtoMapper {

	MemberCommand.AddMember toCommand(String email, String password);

	MemberCommand.MemberLogin toCommand(AuthDto.MemberLoginRequest memberLoginRequest);

	AuthDto.MemberDetailResponse toResponse(MemberInfo.MemberDetail memberDetailInfo);

}
