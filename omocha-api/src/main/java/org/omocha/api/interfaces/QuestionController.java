package org.omocha.api.interfaces;

import static org.omocha.domain.exception.code.QnACode.*;

import org.omocha.api.application.QnaFacade;
import org.omocha.api.common.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.interfaces.dto.QuestionDto;
import org.omocha.api.interfaces.mapper.QuestionDtoMapper;
import org.omocha.domain.auction.qna.QuestionCommand;
import org.omocha.domain.auction.qna.QuestionInfo;
import org.omocha.domain.common.util.PageSort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	private final PageSort pageSort;

	// TODO : QueryDSL JOIN 관련 수정 필요
	@GetMapping("/{auctionId}/qna-list")
	public ResponseEntity<ResultDto<Page<QuestionDto.RetriveQnasResponse>>> qnaList(
		@PathVariable(value = "auctionId") Long auctionId,
		@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
		@RequestParam(value = "direction", defaultValue = "ASC") String direction,
		@PageableDefault(page = 0, size = 10)
		Pageable pageable
	) {

		// TODO : PR 전 TODO 수정해야됨

		Pageable sortPage = pageSort.sortPage(pageable, sort, direction);

		QuestionCommand.QnaList qnaListCommand = questionDtoMapper.toCommand(auctionId);

		Page<QuestionInfo.RetriveQnas> qnaResponseList = qnaFacade.retriveQnas(qnaListCommand, sortPage);

		Page<QuestionDto.RetriveQnasResponse> qnaServiceResponse = questionDtoMapper.toResponse(
			qnaResponseList);

		ResultDto<Page<QuestionDto.RetriveQnasResponse>> resultDto = ResultDto.res(
			QNA_LIST_ACCESS_SUCCESS.getStatusCode(),
			QNA_LIST_ACCESS_SUCCESS.getResultMsg(),
			qnaServiceResponse
		);

		return ResponseEntity
			.status(QNA_LIST_ACCESS_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@PostMapping()
	public ResponseEntity<ResultDto<QuestionDto.AddQuestionResponse>> questionAdd(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestBody QuestionDto.AddQuestionRequest addQuestionRequest
	) {
		log.info("received CreateQuestionRequest: {}", addQuestionRequest);
		log.debug("add question started");

		Long memberId = userPrincipal.getId();

		QuestionCommand.AddQuestion addQuestionCommand = questionDtoMapper.toCommand(memberId,
			addQuestionRequest);

		QuestionInfo.AddQuestion addQuestionInfo = qnaFacade.addQuestion(addQuestionCommand);

		QuestionDto.AddQuestionResponse addQuestionResponse = questionDtoMapper.toResponse(addQuestionInfo);

		ResultDto<QuestionDto.AddQuestionResponse> resultDto = ResultDto.res(
			QUESTION_CREATE_SUCCESS.getStatusCode(),
			QUESTION_CREATE_SUCCESS.getResultMsg(),
			addQuestionResponse
		);

		log.debug("add question finished");

		return ResponseEntity
			.status(QUESTION_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

	@PatchMapping("/{questionId}")
	public ResponseEntity<ResultDto<QuestionDto.ModifyQuestionResponse>> questionModify(
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

		QuestionDto.ModifyQuestionResponse modifyQuestionResponse = questionDtoMapper.toResponse(modifyQuestionInfo);

		ResultDto<QuestionDto.ModifyQuestionResponse> resultDto = ResultDto.res(
			QUESTION_MODIFY_SUCCESS.getStatusCode(),
			QUESTION_MODIFY_SUCCESS.getResultMsg(),
			modifyQuestionResponse
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

		QuestionCommand.RemoveQuestion removeQuestionCommand = questionDtoMapper.toCommand(memberId, questionId);

		qnaFacade.removeQuestion(removeQuestionCommand);

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
