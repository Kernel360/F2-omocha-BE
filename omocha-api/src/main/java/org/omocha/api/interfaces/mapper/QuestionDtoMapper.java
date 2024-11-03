package org.omocha.api.interfaces.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.interfaces.dto.QuestionDto;
import org.omocha.domain.auction.qna.QuestionCommand;
import org.omocha.domain.auction.qna.QuestionInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface QuestionDtoMapper {

	QuestionCommand.CreateQuestion toCommand(Long memberId, QuestionDto.CreateQuestionRequest request);

	QuestionDto.CreateQuestionResponse toDto(QuestionInfo.CreateQuestionResponse createQuestionInfo);
}
