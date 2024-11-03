package org.omocha.api.interfaces;

import static org.omocha.domain.exception.code.QnACode.*;

import org.omocha.api.application.QnaFacade;
import org.omocha.api.common.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.interfaces.dto.QuestionDto;
import org.omocha.api.interfaces.mapper.QuestionDtoMapper;
import org.omocha.domain.auction.qna.QuestionCommand;
import org.omocha.domain.auction.qna.QuestionInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/question")
public class QuestionController {

	private final QnaFacade qnaFacade;
	private final QuestionDtoMapper questionDtoMapper;

	@PostMapping()
	public ResponseEntity<ResultDto<QuestionDto.CreateQuestionResponse>> questionAdd(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestBody QuestionDto.CreateQuestionRequest createQuestionRequest
	) {
		log.info("received CreateQuestionRequest: {}", createQuestionRequest);
		log.debug("add question started");

		Long memberId = userPrincipal.getId();

		QuestionCommand.CreateQuestion createQuestionCommand = questionDtoMapper.toCommand(memberId,
			createQuestionRequest);

		QuestionInfo.CreateQuestionResponse createQuestionInfo = qnaFacade.addQuestion(createQuestionCommand);

		QuestionDto.CreateQuestionResponse createQuestionResponse = questionDtoMapper.toDto(createQuestionInfo);

		ResultDto<QuestionDto.CreateQuestionResponse> resultDto = ResultDto.res(
			QUESTION_CREATE_SUCCESS.getStatusCode(),
			QUESTION_CREATE_SUCCESS.getResultMsg(),
			createQuestionResponse
		);

		log.debug("add question finished");

		return ResponseEntity
			.status(QUESTION_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

}
