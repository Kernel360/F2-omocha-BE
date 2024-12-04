package org.omocha.api.mail;

import org.omocha.api.common.response.ResultDto;
import org.omocha.api.mail.dto.MailDto;
import org.omocha.api.mail.dto.MailDtoMapper;
import org.omocha.domain.common.code.SuccessCode;
import org.omocha.domain.mail.MailCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/mail")
@RequiredArgsConstructor
public class MailController extends MailApi {

	private final MailFacade mailFacade;
	private final MailDtoMapper mailDtoMapper;

	@Override
	@PostMapping("")
	public ResponseEntity<ResultDto<Void>> mailSend(
		@RequestBody MailDto.MailSendRequest mailSendRequest
	) {

		MailCommand.SendMail sendCommand = mailDtoMapper.toCommand(mailSendRequest.email());

		mailFacade.sendMail(sendCommand);

		ResultDto<Void> resultDto = ResultDto.res(
			SuccessCode.MAIL_SEND_SUCCESS.getStatusCode(),
			SuccessCode.MAIL_SEND_SUCCESS.getDescription()
		);

		return ResponseEntity
			.status(SuccessCode.MAIL_SEND_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

	@Override
	@PostMapping("/code")
	public ResponseEntity<ResultDto<Boolean>> mailCodeVerification(
		@RequestBody MailDto.MailCodeVerificationRequest codeVerificationRequest
	) {

		MailCommand.VerifyMailCode verifyMailCodeCommand = mailDtoMapper.toCommand(
			codeVerificationRequest.email(),
			codeVerificationRequest.code()
		);

		Boolean result = mailFacade.verifyMailCode(verifyMailCodeCommand);

		ResultDto<Boolean> resultDto = ResultDto.res(
			SuccessCode.MAIL_CODE_SUCCESS.getStatusCode(),
			SuccessCode.MAIL_CODE_SUCCESS.getDescription(),
			result
		);

		return ResponseEntity
			.status(SuccessCode.MAIL_CODE_SUCCESS.getHttpStatus())
			.body(resultDto);

	}

}
