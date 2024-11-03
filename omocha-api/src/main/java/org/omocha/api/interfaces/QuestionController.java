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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PatchMapping("/{questionId}")
	public ResponseEntity<ResultDto<QuestionDto.QuestionResponse>> questionModify(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable(value = "questionId") Long questionId,
		@RequestBody QuestionDto.ModifyQuestionRequest modifyQuestionRequest
	) {

		log.info("received questionId : {} ModifyQuestionRequest: {}", questionId, modifyQuestionRequest);
		log.debug("modify question started");

		Long memberId = userPrincipal.getId();

		QuestionCommand.ModifyQuestion modifyQuestionCommand = questionDtoMapper.toCommand(memberId, questionId,
			modifyQuestionRequest);

		QuestionInfo.ModifyQuestion modifyQuestionInfo = qnaFacade.modifyQuestion(modifyQuestionCommand);

		QuestionDto.QuestionResponse questionResponse = questionDtoMapper.toDto(modifyQuestionInfo);

		ResultDto<QuestionDto.QuestionResponse> resultDto = ResultDto.res(
			QUESTION_MODIFY_SUCCESS.getStatusCode(),
			QUESTION_MODIFY_SUCCESS.getResultMsg(),
			questionResponse
		);

		log.debug("modify question finished");

		return ResponseEntity
			.status(QUESTION_MODIFY_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

	@DeleteMapping("/{questionId}")
	public ResponseEntity<ResultDto<Void>> questionRemove(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable(value = "questionId") Long questionId
	) {

		log.info("received questionId: {}", questionId);
		log.debug("remove question started");

		Long memberId = userPrincipal.getId();

		QuestionCommand.DeleteQuestion deleteQuestionCommand = questionDtoMapper.toCommand(memberId, questionId);

		qnaFacade.removeQuestion(deleteQuestionCommand);

		ResultDto<Void> resultDto = ResultDto.res(
			QUESTION_DELETE_SUCCESS.getStatusCode(),
			QUESTION_DELETE_SUCCESS.getResultMsg()
		);

		log.debug("remove question finished");

		return ResponseEntity
			.status(QUESTION_DELETE_SUCCESS.getStatusCode())
			.body(resultDto);
	}

}
