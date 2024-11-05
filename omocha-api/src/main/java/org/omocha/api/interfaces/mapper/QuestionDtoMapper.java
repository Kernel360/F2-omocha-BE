package org.omocha.api.interfaces.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.api.interfaces.dto.QuestionDto;
import org.omocha.domain.auction.qna.QuestionCommand;
import org.omocha.domain.auction.qna.QuestionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface QuestionDtoMapper {

	QuestionCommand.ModifyQuestion toCommand(Long memberId, Long questionId,
		QuestionDto.ModifyQuestionRequest modifyQuestionRequest);

	QuestionCommand.AddQuestion toCommand(Long memberId, QuestionDto.AddQuestionRequest request);

	QuestionDto.AddQuestionResponse toResponse(QuestionInfo.AddQuestionResponse createQuestionInfo);

	QuestionDto.ModifyQuestionResponse toResponse(QuestionInfo.ModifyQuestion modifyQuestionInfo);

	QuestionCommand.RemoveQuestion toCommand(Long memberId, Long questionId);

	QuestionCommand.QnaList toCommand(Long auctionId);

	default Page<QuestionDto.QnaServiceResponse> toResponse(Page<QuestionInfo.QnaServiceResponse> qnaServiceResponses) {
		List<QuestionDto.QnaServiceResponse> content = qnaServiceResponses.getContent().stream()
			.map(this::toResponse)
			.collect(Collectors.toList());

		return new PageImpl<>(content, qnaServiceResponses.getPageable(), qnaServiceResponses.getTotalElements());
	}

	QuestionDto.QnaServiceResponse toResponse(QuestionInfo.QnaServiceResponse qnaServiceResponses);

	QuestionDto.QuestionDetails toResponse(QuestionInfo.QuestionDetails questionDetailsInfo);

}
