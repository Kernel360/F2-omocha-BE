package org.omocha.api.interfaces.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.interfaces.dto.AnswerDto;
import org.omocha.domain.auction.qna.AnswerCommand;
import org.omocha.domain.auction.qna.AnswerInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AnswerDtoMapper {
	AnswerCommand.CreateAnswerRequest toCommand(Long memberId, AnswerDto.CreateAnswerRequest createAnswerRequest);

	AnswerDto.CreateAnswerResponse toDto(AnswerInfo.CreateAnswer createAnswerInfo);
}
