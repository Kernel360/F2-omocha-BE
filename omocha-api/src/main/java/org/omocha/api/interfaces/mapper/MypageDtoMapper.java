package org.omocha.api.interfaces.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.interfaces.dto.MypageDto;
import org.omocha.domain.member.mypage.MypageInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MypageDtoMapper {

	MypageDto.MemberInfoResponse of(MypageInfo.MemberInfoResponse memberInfoResponse);

}
