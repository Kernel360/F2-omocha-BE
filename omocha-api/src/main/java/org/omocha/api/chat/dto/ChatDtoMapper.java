package org.omocha.api.chat.dto;

import java.time.LocalDateTime;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.common.util.ValueObjectMapper;
import org.omocha.domain.chat.Chat;
import org.omocha.domain.chat.ChatCommand;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR,
	uses = ValueObjectMapper.class
)
public interface ChatDtoMapper {

	ChatCommand.AddChatRoom toCommand(Long auctionId, Long buyerMemberId);

	ChatCommand.RetrieveMyChatRoom toCommand(Long memberId);

	@Mapping(target = "messageType", source = "chatMessageRequest.messageType", qualifiedByName = "toChatType")
	ChatCommand.AddChatMessage toCommand(ChatDto.ChatMessageRequest chatMessageRequest, Long roomId);

	ChatCommand.RetrieveChatRoomMessage toCommand(Long roomId, Long memberId, LocalDateTime cursor);

	@Named("toChatType")
	default Chat.MessageType toChatType(String type) {
		return Chat.MessageType.valueOf(type.toUpperCase());
	}
}
