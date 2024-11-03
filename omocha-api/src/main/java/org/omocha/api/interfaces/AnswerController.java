package org.omocha.api.interfaces;

import org.omocha.api.application.QnaFacade;
import org.omocha.api.common.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.interfaces.dto.AnswerDto;
import org.omocha.api.interfaces.mapper.AnswerDtoMapper;
import org.omocha.domain.auction.qna.AnswerCommand;
import org.omocha.domain.auction.qna.AnswerInfo;
import org.omocha.domain.exception.code.QnACode;
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
@RequestMapping("/api/v2/answer")
public class AnswerController {

	private final QnaFacade qnaFacade;
	private final AnswerDtoMapper answerDtoMapper;

	@PostMapping()
	public ResponseEntity<ResultDto<AnswerDto.CreateAnswerResponse>> answerAdd(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestBody AnswerDto.CreateAnswerRequest createAnswerRequest
	) {

		log.info("received CreateAnswerRequest : {}", createAnswerRequest);
		log.debug("add answer started");

		Long memberId = userPrincipal.getId();

		AnswerCommand.CreateAnswerRequest createAnswerCommand = answerDtoMapper.toCommand(memberId,
			createAnswerRequest);

		AnswerInfo.CreateAnswer createAnswerInfo = qnaFacade.addAnswer(createAnswerCommand);

		AnswerDto.CreateAnswerResponse createAnswerResponse = answerDtoMapper.toDto(createAnswerInfo);

		// TODO : resultDto 수정 필요
		ResultDto<AnswerDto.CreateAnswerResponse> resultDto = ResultDto.res(
			QnACode.ANSWER_CREATE_SUCCESS.getStatusCode(),
			QnACode.ANSWER_CREATE_SUCCESS.getResultMsg(),
			createAnswerResponse
		);

		log.debug("add answer finished");

		return ResponseEntity
			.status(QnACode.ANSWER_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

}
