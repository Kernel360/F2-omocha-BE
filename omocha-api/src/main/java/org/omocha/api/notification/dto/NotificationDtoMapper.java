package org.omocha.api.notification.dto;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.common.util.ValueObjectMapper;
import org.omocha.domain.notification.NotificationCommand;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR,
	uses = ValueObjectMapper.class
)
public interface NotificationDtoMapper {

	NotificationCommand.Connect toConnectCommand(Long memberId);

	NotificationCommand.Read toReadCommand(Long notificationId);
}
