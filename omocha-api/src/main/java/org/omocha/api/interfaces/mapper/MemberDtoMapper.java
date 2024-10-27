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

	MemberCommand.MemberCreateCommand of(MemberDto.MemberCreateRequest memberCreateRequest);

	MemberCommand.MemberLoginCommand of(MemberDto.MemberLoginRequest memberLoginRequest);

	MemberCommand.MemberDuplicateCommand of(MemberDto.MemberDuplicateRequest memberDuplicateRequest);

	MemberDto.MemberDetailResponse of(MemberInfo.MemberDetailInfo memberDetailInfo);

}
