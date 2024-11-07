package org.omocha.api.interfaces.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.interfaces.dto.MypageDto;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MypageDtoMapper {

	MemberCommand.ModifyBasicInfo toCommand(Long memberId, MypageDto.MemberModifyRequest memberModifyRequest);

	MemberCommand.ModifyPassword toCommand(Long memberId, String currentPassword, String newPassword);

	MypageDto.CurrentMemberInfoResponse toResponse(MemberInfo.RetrieveCurrentMemberInfo retrieveCurrentMemberInfo);

	MypageDto.MemberModifyResponse toResponse(MemberInfo.ModifyBasicInfo modifyBasicInfoInfo);

	MypageDto.ProfileImageModifyResponse toResponse(MemberInfo.modifyProfileImage modifyProfileImage);

	MemberCommand.ModifyProfileImage toCommand(Long memberId, MultipartFile profileImage);

	// MypageDto.MypageAuctionListResponse toResponse(AuctionInfo.MypageAuctionListInfo mypageAuctionListInfo);

	// MypageDto.MypageBidListResponse toResponse(AuctionInfo.MypageBidListInfo mypageBidListInfo);

}
