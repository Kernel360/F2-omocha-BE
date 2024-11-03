package org.omocha.api.interfaces.mapper;

import java.time.LocalDateTime;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.interfaces.dto.ChatDto;
import org.omocha.domain.auction.chat.ChatCommand;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ChatDtoMapper {

	ChatCommand.CreateChatRoom toCommand(Long auctionId, Long buyerId);

	ChatCommand.RetrieveMyChatRoom toCommand(Long memberId);

	ChatCommand.SaveChatMessage toCommand(ChatDto.ChatMessageRequest chatMessageRequest, Long roomId);

	ChatCommand.RetrieveChatRoomMessage toCommand(Long roomId, Long memberId, LocalDateTime cursor);

}
