package org.omocha.api.interfaces.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.interfaces.dto.MypageDto;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MypageDtoMapper {

	MemberCommand.MemberModify toCommand(Long memberId, MypageDto.MemberModifyRequest memberModifyRequest);

	MemberCommand.PasswordModify toCommand(Long memberId, MypageDto.PasswordModifyReuqest passwordModifyRequest);

	MypageDto.MemberInfoResponse toResponse(MemberInfo.CurrentMemberInfo memberInfoResponse);

	MypageDto.MemberModifyResponse toResponse(MemberInfo.MemberModifyInfo memberModifyInfo);

	MypageDto.ProfileImageModifyResponse toResponse(MemberInfo.ProfileImageInfo profileImageInfo);

	// MypageDto.MypageAuctionListResponse toResponse(AuctionInfo.MypageAuctionListInfo mypageAuctionListInfo);

	// MypageDto.MypageBidListResponse toResponse(AuctionInfo.MypageBidListInfo mypageBidListInfo);

}
