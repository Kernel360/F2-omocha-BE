package org.omocha.api.interfaces.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.interfaces.dto.MemberDto;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberDtoMapper {

	MemberCommand.AddMember toCommand(String email, String password);

	MemberCommand.MemberLogin toCommand(MemberDto.MemberLoginRequest memberLoginRequest);

	MemberDto.MemberDetailResponse toResponse(MemberInfo.MemberDetail memberDetailInfo);

}
