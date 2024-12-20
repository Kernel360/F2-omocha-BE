package org.omocha.domain.common.code;

import org.omocha.domain.review.rating.Rating;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// TODO : description을 현재는 exception message와 같게 해놓음, 수정 필요

	// Jwt Code
	INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "올바르지 않는 Refresh Token 입니다."),

	JWT_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "JWT 토큰을 찾을 수 없습니다."),

	// OAuth Code
	UNSUPPORTED_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, "지원하지 않는 OAuth Provider입니다."),

	// Member Code
	MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다."),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	IDENTICAL_PASSWORD(HttpStatus.BAD_REQUEST, "입력한 새 비밀번호가 기존 비밀번호와 동일합니다."),
	NICKNAME_DUPLICATE(HttpStatus.BAD_REQUEST, "닉네임이 이미 사용 중입니다."),
	INVALID_MEMBER(HttpStatus.BAD_REQUEST, "회원이 일치하지 않습니다."),
	INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 형식입니다."),
	INVALID_PHONE_NUMBER_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 휴대폰번호 형식입니다."),

	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),

	// Auction Code
	AUCTION_ALREADY_ENDED(HttpStatus.BAD_REQUEST, "이미 종료된 경매입니다."),
	AUCTION_HAS_BIDS(HttpStatus.BAD_REQUEST, "입찰이 걸려있는 경매입니다. 경매를 종료할 수 없습니다."),
	AUCTION_NOT_IN_BIDDING_STATUS(HttpStatus.BAD_REQUEST, "경매의 상태가 입찰중이 아닙니다."),
	AUCTION_MEMBER_INVALID(HttpStatus.BAD_REQUEST, "경매를 생성한 회원이 아니여서 삭제를 할 수 없습니다."),
	AUCTION_NOT_CONCLUDED(HttpStatus.BAD_REQUEST, "경매가 낙찰되지 않은 상태입니다."),
	LIKE_NOT_NEGATIVE(HttpStatus.BAD_REQUEST, "찜 수가 음수가 되면 안됩니다"),
	NEGATIVE_PRICE(HttpStatus.BAD_REQUEST, "금액은 음수일 수 없습니다."),
	PRICE_TOO_HIGH(HttpStatus.BAD_REQUEST, "허용 가능한 최대 금액을 초과했습니다."),
	START_PRICE_HIGHER_THAN_INSTANT_BUY_PRICE(HttpStatus.BAD_REQUEST, "시작 가격이 즉시 구매 가격보다 높을 수 없습니다."),
	END_TIME_BEFORE_NOW(HttpStatus.BAD_REQUEST, "종료 시간이 현재 시간보다 이전입니다."),
	BID_UNIT_TOO_HIGH(HttpStatus.BAD_REQUEST, "입찰 단위가 너무 높습니다."),

	AUCTION_NOT_FOUND(HttpStatus.NOT_FOUND, "경매를 찾을 수 없습니다."),
	AUCTION_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "경매이미지를 찾을 수 없습니다."),

	// Image Code
	IMAGE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "경매에서 생성된 이미지를 삭제할 수 없습니다."),
	IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "이미지를 찾지 못했습니다"),
	REQUEST_PART_NOT_FOUND(HttpStatus.NOT_FOUND, "RequestPart를 찾지 못했습니다"),

	UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 Content-Type 입니다."),
	MAX_UPLOAD_SIZE_FAIL(HttpStatus.EXPECTATION_FAILED, "파일 크기가 너무 큽니다!"),
	IMAGE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "S3에 파일 업로드가 실패했습니다"),

	// Category Code
	CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 카테고리를 찾을 수 없습니다."),

	// Bid Code
	NO_BIDS_FOUND(HttpStatus.BAD_REQUEST, "입찰이 존재하지 않습니다."),
	SELF_BID_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "자신의 경매에 입찰을 걸 수 없습니다."),
	INVALID_BID_UNIT(HttpStatus.BAD_REQUEST, "입찰 금액이 입찰 단위에 유효하지 않습니다."),
	BID_BELOW_START_PRICE(HttpStatus.BAD_REQUEST, "입찰 가격이 시작 가격보다 낮습니다."),
	BID_NOT_EXCEEDING_CURRENT_HIGHEST(HttpStatus.BAD_REQUEST, "입찰 가격이 최고가보다 높지 않습니다."),
	BID_EXCEEDS_INSTANT_BUY_PRICE(HttpStatus.BAD_REQUEST, "입찰 가격이 즉시 구매 가격과 같거나 높습니다."),

	// Conclude Code
	CONCLUDE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 경매에는 낙찰 내역이 존재하지 않습니다."),

	// Chat Code
	CHATROOM_ACCESS_FAIL(HttpStatus.FORBIDDEN, "채팅방에 접근 거부되었습니다"),

	CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다"),

	CHATROOM_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 채팅방입니다."),

	// Review Code
	INVALID_RATING(HttpStatus.BAD_REQUEST, "리뷰 평점은 " + Rating.MIN + "점부터 " + Rating.MAX + "점까지만 가능합니다."),
	REVIEW_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 리뷰를 작성하였습니다."),
	REVIEW_PERMISSION_DENIED(HttpStatus.BAD_REQUEST, "해당 경매에 리뷰를 작성할 권한이 없습니다."),
	INVALID_REVIEW_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 리뷰 타입입니다."),

	// Qna Code
	QUESTION_DENY(HttpStatus.FORBIDDEN, "수정,삭제가 거부되었습니다."),
	ANSWER_DENY(HttpStatus.FORBIDDEN, "수정,삭제가 거부되었습니다."),

	QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "문의를 찾을 수 없습니다."),
	ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "답변을 찾을 수 없습니다."),

	ANSWER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 답변이 존재합니다."),

	// Mail Code
	MAIL_CODE_KEY_NOT_FOUND(HttpStatus.NOT_FOUND, "유효하지않은 인증 코드입니다."),
	MAIL_AUTH_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "코드가 일치하지 않습니다."),
	MAIL_SEND_FAILED(HttpStatus.BAD_REQUEST, "메일 전송에 실패했습니다."),
	MAIL_TEMPLATE_FAILED(HttpStatus.BAD_REQUEST, "메일 메세지 작성에 실패하였습니다."),
	MAIL_RETRY_EXCESS(HttpStatus.BAD_REQUEST, "잠시 후 다시 시도 바랍니다."),

	// Redis
	LOCK_INTERRUPTED(HttpStatus.INTERNAL_SERVER_ERROR, "요청 처리 중 오류가 발생했습니다."),
	RLOCK_NOT_AVAILABLE(HttpStatus.CONFLICT, "선입찰자가 있습니다."),

	// Notification Code
	NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "알림을 찾을 수 없습니다."),
	NOTIFICATION_ACCESS(HttpStatus.FORBIDDEN, "알림에 접근하려는 사용자가 알림 소유자와 일치하지 않습니다."),

	// Common Code
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 정보가 올바르지 않습니다. 재로그인 후 다시 시도해주세요."),
	OAUTH_FAILURE(HttpStatus.UNAUTHORIZED, "OAuth 로그인에 실패하였습니다."),

	NOT_NULL_FIELD(HttpStatus.BAD_REQUEST, "해당 필드들은 필수 입력 사항입니다."),
	BAD_REQUEST_INVALID_FIELD(HttpStatus.BAD_REQUEST, "요청받은 필드값이 올바르지 않습니다."),

	FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

	URL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 URL을 찾을 수 없습니다."),

	JSON_SERIALIZATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버오류가 발생했습니다."),
	JSON_DESERIALIZATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버오류가 발생했습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버오류가 발생했습니다.");

	private final HttpStatus httpStatus;
	private final String description;

	public int getStatusCode() {
		return httpStatus.value();
	}
}
