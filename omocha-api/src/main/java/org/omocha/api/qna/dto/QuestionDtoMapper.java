package org.omocha.api.qna.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.omocha.domain.qna.question.QuestionCommand;
import org.omocha.domain.qna.question.QuestionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface QuestionDtoMapper {

	QuestionCommand.ModifyQuestion toCommand(
		Long memberId,
		Long questionId,
		QuestionDto.QuestionModifyRequest questionModifyRequest
	);

	QuestionCommand.AddQuestion toCommand(Long memberId, QuestionDto.QuestionAddRequest request);

	QuestionDto.QuestionAddResponse toResponse(QuestionInfo.AddQuestion createQuestionInfo);

	QuestionDto.QuestionModifyResponse toResponse(QuestionInfo.ModifyQuestion modifyQuestionInfo);

	QuestionCommand.RemoveQuestion toCommand(Long memberId, Long questionId);

	QuestionCommand.RetrieveQnas toCommand(Long auctionId);

	default Page<QuestionDto.QnaListResponse> toResponse(Page<QuestionInfo.RetrieveQnas> retriveQna) {
		List<QuestionDto.QnaListResponse> content = retriveQna.getContent().stream()
			.map(this::toResponse)
			.collect(Collectors.toList());

		return new PageImpl<>(content, retriveQna.getPageable(), retriveQna.getTotalElements());
	}

	QuestionDto.QnaListResponse toResponse(QuestionInfo.RetrieveQnas retrieveQnas);

	QuestionDto.QuestionDetails toResponse(QuestionInfo.QuestionDetails questionDetailsInfo);

}
