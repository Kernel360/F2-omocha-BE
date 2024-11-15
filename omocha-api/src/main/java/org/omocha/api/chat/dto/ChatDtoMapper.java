package org.omocha.api.chat.dto;

import java.time.LocalDateTime;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.domain.chat.ChatCommand;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ChatDtoMapper {

	ChatCommand.AddChatRoom toCommand(Long auctionId, Long buyerId);

	ChatCommand.RetrieveMyChatRoom toCommand(Long memberId);

	ChatCommand.AddChatMessage toCommand(ChatDto.ChatMessageRequest chatMessageRequest, Long roomId);

	ChatCommand.RetrieveChatRoomMessage toCommand(Long roomId, Long memberId, LocalDateTime cursor);

}
