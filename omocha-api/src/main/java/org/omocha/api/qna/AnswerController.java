package org.omocha.api.qna;

import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.api.common.response.ResultDto;
import org.omocha.api.qna.dto.AnswerDto;
import org.omocha.api.qna.dto.AnswerDtoMapper;
import org.omocha.domain.common.code.SuccessCode;
import org.omocha.domain.qna.answer.AnswerCommand;
import org.omocha.domain.qna.answer.AnswerInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/answers")
public class AnswerController implements AnswerApi {

	private final QnaFacade qnaFacade;
	private final AnswerDtoMapper answerDtoMapper;

	@Override
	@PostMapping()
	public ResponseEntity<ResultDto<AnswerDto.AnswerAddResponse>> answerAdd(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@RequestBody @Valid AnswerDto.AnswerAddRequest answerAddRequest
	) {

		log.info("received CreateAnswerRequest : {}", answerAddRequest);

		Long memberId = userPrincipal.getId();

		AnswerCommand.AddAnswer addAnswerCommand = answerDtoMapper.toCommand(memberId, answerAddRequest);

		AnswerInfo.AddAnswer addAnswerInfo = qnaFacade.addAnswer(addAnswerCommand);

		AnswerDto.AnswerAddResponse answerAddResponse = answerDtoMapper.toResponse(addAnswerInfo);

		// TODO : resultDto 수정 필요
		ResultDto<AnswerDto.AnswerAddResponse> resultDto = ResultDto.res(
			SuccessCode.ANSWER_CREATE_SUCCESS.getStatusCode(),
			SuccessCode.ANSWER_CREATE_SUCCESS.getDescription(),
			answerAddResponse
		);

		log.info("add answer finished");

		return ResponseEntity
			.status(SuccessCode.ANSWER_CREATE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@PatchMapping("/{answer_id}")
	public ResponseEntity<ResultDto<AnswerDto.AnswerModifyResponse>> answerModify(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable("answer_id") Long answerId,
		@RequestBody @Valid AnswerDto.AnswerModifyRequest answerModifyRequest
	) {

		log.info("received answerId : {} , ModifyAnswerRequest : {}", answerId, answerModifyRequest);

		Long memberId = userPrincipal.getId();

		AnswerCommand.ModifyAnswer modifyAnswerCommand = answerDtoMapper.toCommand(memberId, answerId,
			answerModifyRequest);

		AnswerInfo.ModifyAnswer modifyAnswerInfo = qnaFacade.modifyAnswer(modifyAnswerCommand);

		AnswerDto.AnswerModifyResponse answerModifyResponse = answerDtoMapper.toResponse(modifyAnswerInfo);

		ResultDto<AnswerDto.AnswerModifyResponse> resultDto = ResultDto.res(
			SuccessCode.ANSWER_MODIFY_SUCCESS.getStatusCode(),
			SuccessCode.ANSWER_MODIFY_SUCCESS.getDescription(),
			answerModifyResponse
		);

		log.info("modify answer finished");

		return ResponseEntity
			.status(SuccessCode.ANSWER_MODIFY_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

	@Override
	@DeleteMapping("/{answer_id}")
	public ResponseEntity<ResultDto<Void>> answerRemove(
		@AuthenticationPrincipal UserPrincipal userPrincipal,
		@PathVariable(value = "answer_id") Long answerId
	) {

		log.info("received answerId : {} ", answerId);

		Long memberId = userPrincipal.getId();

		AnswerCommand.RemoveAnswer removeAnswerModify = answerDtoMapper.toCommand(memberId, answerId);

		qnaFacade.removeAnswer(removeAnswerModify);

		ResultDto<Void> resultDto = ResultDto.res(
			SuccessCode.ANSWER_DELETE_SUCCESS.getStatusCode(),
			SuccessCode.ANSWER_DELETE_SUCCESS.getDescription()
		);

		log.info("remove answer finished");

		return ResponseEntity
			.status(SuccessCode.ANSWER_DELETE_SUCCESS.getHttpStatus())
			.body(resultDto);
	}

}
