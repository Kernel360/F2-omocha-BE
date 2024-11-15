package org.omocha.domain.chat;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.omocha.domain.member.Member;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ChatInfoMapper {

	@Mapping(source = "savedChat.createdAt", target = "createdAt")
	@Mapping(source = "sender.profileImageUrl", target = "senderProfileImage")
	ChatInfo.RetrieveChatRoomMessage toResponse(Member sender, Chat savedChat);
}
