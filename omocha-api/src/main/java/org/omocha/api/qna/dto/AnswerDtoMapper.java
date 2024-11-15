package org.omocha.api.qna.dto;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.domain.qna.answer.AnswerCommand;
import org.omocha.domain.qna.answer.AnswerInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AnswerDtoMapper {
	AnswerCommand.AddAnswer toCommand(Long memberId, AnswerDto.AnswerAddRequest answerAddRequest);

	AnswerDto.AnswerAddResponse toResponse(AnswerInfo.AddAnswer createAnswerInfo);

	AnswerCommand.ModifyAnswer toCommand(
		Long memberId,
		Long answerId,
		AnswerDto.AnswerModifyRequest answerModifyRequest
	);

	AnswerDto.AnswerModifyResponse toResponse(AnswerInfo.ModifyAnswer modifyAnswerInfo);

	AnswerCommand.RemoveAnswer toCommand(Long memberId, Long answerId);

	AnswerDto.AnswerDetails toResponse(AnswerInfo.AnswerDetails answerDetailsInfo);
}
