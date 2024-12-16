package org.omocha.domain.common.code;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

	// Member Code
	MEMBER_CREATE_SUCCESS(HttpStatus.OK, "회원을 성공적으로 생성하였습니다."),
	MEMBER_LOGIN_SUCCESS(HttpStatus.OK, "로그인이 완료되었습니다."),
	MEMBER_LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃이 완료되었습니다."),
	TOKEN_REISSUE_SUCCESS(HttpStatus.OK, "토큰을 성공적으로 재발급 하였습니다."),
	VALIDATE_EMAIL_SUCCESS(HttpStatus.OK, "사용 가능한 이메일입니다."),

	MEMBER_INFO_RETRIEVE_SUCCESS(HttpStatus.OK, "유저 정보를 성공적으로 반환하였습니다."),
	MY_AUCTION_LIST_SUCCESS(HttpStatus.OK, "나의 경매 내역을 성공적으로 조회하였습니다."),
	MEMBER_AUCTION_LIST_SUCCESS(HttpStatus.OK, "상대방의 경매 내역을 성공적으로 조회하였습니다."),
	MY_BIDDING_LIST_SUCCESS(HttpStatus.OK, "입찰 내역을 성공적으로 조회하였습니다."),
	MY_BIDDING_AUCTION_LIST_SUCCESS(HttpStatus.OK, "입찰한 경매 내역을 성공적으로 조회하였습니다."),

	MEMBER_INFO_UPDATED(HttpStatus.OK, "멤버 정보 수정이 완료되었습니다."),
	PROFILE_IMAGE_UPDATED(HttpStatus.OK, "프로필 이미지 수정이 완료되었습니다."),
	PASSWORD_UPDATED(HttpStatus.OK, "패스워드 수정이 완료되었습니다."),
	NICKNAME_AVAILABLE(HttpStatus.OK, "사용 가능한 닉네임입니다."),

	// Auction Code
	AUCTION_CREATE_SUCCESS(HttpStatus.OK, "경매가 성공적으로 생성되었습니다."),
	AUCTION_DELETE_SUCCESS(HttpStatus.OK, "경매가 성공적으로 삭제되었습니다."),
	AUCTION_LIST_ACCESS_SUCCESS(HttpStatus.OK, "경매 리스트를 성공적으로 조회하였습니다."),
	AUCTION_DETAIL_SUCCESS(HttpStatus.OK, "경매 상세정보를 성공적으로 조회하였습니다."),
	AUCTION_INSTANT_BUY_SUCCESS(HttpStatus.OK, "즉시 구매를 성공적으로 처리하였습니다."),

	// QnA Code
	QUESTION_CREATE_SUCCESS(HttpStatus.OK, "문의가 성공적으로 생성되었습니다."),
	QUESTION_MODIFY_SUCCESS(HttpStatus.OK, "문의가 성공적으로 수정되었습니다."),
	QUESTION_DELETE_SUCCESS(HttpStatus.OK, "문의가 성공적으로 삭제되었습니다."),
	QUESTION_ACCESS_SUCCESS(HttpStatus.OK, "문의 조회에 성공하였습니다."),
	QNA_LIST_ACCESS_SUCCESS(HttpStatus.OK, "문의,답변 목록 조회에 성공하였습니다."),

	ANSWER_CREATE_SUCCESS(HttpStatus.OK, "답변이 성공적으로 생성되었습니다."),
	ANSWER_MODIFY_SUCCESS(HttpStatus.OK, "답변이 성공적으로 수정되었습니다."),
	ANSWER_DELETE_SUCCESS(HttpStatus.OK, "답변이 성공적으로 삭제되었습니다."),
	ANSWER_LIST_ACCESS_SUCCESS(HttpStatus.OK, "답변 목록 조회에 성공하였습니다."),

	// Category Code
	CATEGORY_CREATE_SUCCESS(HttpStatus.OK, "카테고리가 성공적으로 생성되었습니다."),
	CATEGORY_HIERARCHY_SUCCESS(HttpStatus.OK, "카테고리 계층 정보를 성공적으로 조회하였습니다"),
	CATEGORY_DETAIL_SUCCESS(HttpStatus.OK, "카테고리 전체 조회를 성공했습니다."),

	// Bid Code
	BIDDING_CREATE_SUCCESS(HttpStatus.OK, "성공적으로 입찰되었습니다."),
	BIDDING_GET_SUCCESS(HttpStatus.OK, "성공적으로 입찰을 불러왔습니다."),
	NOW_PRICE_GET_SUCCESS(HttpStatus.OK, "성공적으로 현재가를 불러왔습니다."),

	// Chat Code
	CHATROOM_CREATE_SUCCESS(HttpStatus.CREATED, "채팅방이 성공적으로 생성되었습니다."),
	CHATROOM_LIST_SUCCESS(HttpStatus.OK, "채팅방 목록을 성공적으로 조회하였습니다."),
	CHATROOM_MESSAGES_SUCCESS(HttpStatus.OK, "전체 메시지를 성공적으로 조회하였습니다."),

	// Review Code
	REVIEW_ADD_SUCCESS(HttpStatus.OK, "리뷰가 성공적으로 생성되었습니다."),
	REVIEW_LIST_ACCESS_SUCCESS(HttpStatus.OK, "리뷰 리스트를 성공적으로 조회하였습니다."),

	// Like Code
	AUCTION_LIKE_SUCCESS(HttpStatus.OK, "경매 찜을 성공적으로 했습니다."),
	AUCTION_UNLIKE_SUCCESS(HttpStatus.OK, "경매 찜 취소를 성공적으로 했습니다"),
	AUCTION_LIKE_LIST_SUCCESS(HttpStatus.OK, "사용자의 찜 목록을 성공적으로 조회했습니다"),

	// Image Code
	IMAGE_UPLOAD_SUCCESS(HttpStatus.OK, "이미지를 성공적으로 업로드 했습니다"),
	IMAGE_DELETE_SUCCESS(HttpStatus.OK, "이미지를 성공적으로 삭제 했습니다"),

	// Mail Code
	MAIL_CODE_SUCCESS(HttpStatus.OK, "메일 인증에 성공하였습니다."),
	MAIL_SEND_SUCCESS(HttpStatus.OK, "메일이 성공적으로 발송되었습니다."),

	// Notification Code
	NOTIFICATION_READ_SUCCESS(HttpStatus.OK, "알림 읽음 처리를 완료하였습니다."),
	SSE_DISCONNECT_SUCCESS(HttpStatus.OK, "SSE 연결을 정상적으로 종료하였습니다.");

	private final HttpStatus httpStatus;
	private final String description;

	public int getStatusCode() {
		return httpStatus.value();
	}
}
