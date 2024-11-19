package org.omocha.api.member.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.omocha.domain.member.vo.PhoneNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberDtoMapper {

	// 통합된 메소드
	default <T, R> Page<R> toResponsePage(Page<T> pageInfo, Function<T, R> mapper) {
		List<R> content = pageInfo.getContent().stream()
			.map(mapper)
			.collect(Collectors.toList());

		return new PageImpl<>(content, pageInfo.getPageable(), pageInfo.getTotalElements());
	}

	MemberDto.CurrentMemberInfoResponse toResponse(MemberInfo.RetrieveCurrentMemberInfo retrieveCurrentMemberInfo);

	@Mapping(target = "phoneNumber", source = "memberModifyRequest.phoneNumber", qualifiedByName = "toPhoneNumber")
	MemberCommand.ModifyBasicInfo toCommand(Long memberId, MemberDto.MemberModifyRequest memberModifyRequest);

	@Named("toPhoneNumber")
	static PhoneNumber toPhoneNumber(String phoneNumber) {
		if (phoneNumber != null) {
			return new PhoneNumber(phoneNumber);
		}

		return new PhoneNumber(null);
	}

	MemberDto.MemberModifyResponse toResponse(MemberInfo.ModifyBasicInfo modifyBasicInfoInfo);

	MemberCommand.ModifyPassword toCommand(Long memberId, String currentPassword, String newPassword);

	MemberCommand.ModifyProfileImage toCommand(Long memberId, MultipartFile profileImage);

	MemberDto.ProfileImageModifyResponse toResponse(MemberInfo.ModifyProfileImage modifyProfileImage);

}
