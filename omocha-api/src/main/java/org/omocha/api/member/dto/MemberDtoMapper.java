package org.omocha.api.member.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.common.util.ValueObjectMapper;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR,
	uses = ValueObjectMapper.class
)
public interface MemberDtoMapper {

	// 통합된 메소드
	default <T, R> Page<R> toResponsePage(Page<T> pageInfo, Function<T, R> mapper) {
		List<R> content = pageInfo.getContent().stream()
			.map(mapper)
			.collect(Collectors.toList());

		return new PageImpl<>(content, pageInfo.getPageable(), pageInfo.getTotalElements());
	}

	MemberDto.MyInfoResponse toResponse(MemberInfo.RetrieveMyInfo retrieveMyInfo);

	MemberDto.MemberInfoResponse toResponse(MemberInfo.RetrieveMemberInfo retrieveMemberInfo);

	@Mapping(target = "phoneNumber", source = "memberModifyRequest.phoneNumber", qualifiedByName = "toPhoneNumber")
	MemberCommand.ModifyMyInfo toCommand(Long memberId, MemberDto.MyInfoModifyRequest memberModifyRequest);

	MemberDto.MyInfoModifyResponse toResponse(MemberInfo.ModifyMyInfo modifyBasicInfoInfo);

	@Mapping(target = "newEncryptedPassword", source = "passwordModifyRequest.newPassword", qualifiedByName = "toPassword")
	MemberCommand.ModifyPassword toCommand(Long memberId, MemberDto.PasswordModifyRequest passwordModifyRequest);

	MemberCommand.ModifyProfileImage toCommand(Long memberId, MultipartFile profileImage);

	MemberDto.ProfileImageModifyResponse toResponse(MemberInfo.ModifyProfileImage modifyProfileImage);

}
