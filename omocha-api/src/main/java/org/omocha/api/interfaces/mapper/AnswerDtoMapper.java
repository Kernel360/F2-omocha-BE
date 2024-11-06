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
	AnswerCommand.AddAnswer toCommand(Long memberId, AnswerDto.AddAnswerRequest addAnswerRequest);

	AnswerDto.AddAnswerResponse toResponse(AnswerInfo.AddAnswer createAnswerInfo);

	AnswerCommand.ModifyAnswer toCommand(Long memberId, Long answerId,
		AnswerDto.ModifyAnswerRequest modifyAnswerRequest);

	AnswerDto.ModifyAnswerResponse toResponse(AnswerInfo.ModifyAnswer modifyAnswerInfo);

	AnswerCommand.RemoveAnswer toCommand(Long memberId, Long answerId);

	AnswerDto.AnswerDetails toResponse(AnswerInfo.AnswerDetails answerDetailsInfo);
}
