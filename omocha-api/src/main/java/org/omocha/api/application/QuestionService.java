package org.omocha.api.application;

import static org.omocha.client.common.code.MemberCode.*;

import org.omocha.client.common.code.AuctionCode;
import org.omocha.client.common.code.MemberCode;
import org.omocha.client.common.code.QnACode;
import org.omocha.client.exception.auction.AuctionNotFoundException;
import org.omocha.client.exception.member.InvalidMemberException;
import org.omocha.client.exception.member.MemberNotFoundException;
import org.omocha.client.exception.qna.QnaNotAllowedException;
import org.omocha.client.exception.qna.QnaNotFoundException;
import org.omocha.client.qna.interfaces.request.CreateQuestionRequest;
import org.omocha.client.qna.interfaces.request.ModifyQuestionRequest;
import org.omocha.client.qna.interfaces.response.CreateQuestionResponse;
import org.omocha.client.qna.interfaces.response.QnaServiceResponse;
import org.omocha.client.qna.interfaces.response.QuestionResponse;
import org.omocha.domain.auction.AuctionEntity;
import org.omocha.domain.auction.qna.QnaDomainResponse;
import org.omocha.domain.auction.qna.QuestionEntity;
import org.omocha.domain.member.MemberEntity;
import org.omocha.infra.AnswerRepository;
import org.omocha.infra.AuctionRepository;
import org.omocha.infra.MemberRepository;
import org.omocha.infra.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;
	private final MemberRepository memberRepository;
	private final AuctionRepository auctionRepository;
	private final AnswerService answerService;

	@Transactional(readOnly = true)
	public Page<QnaServiceResponse> qnaList(
		Long auctionId,
		Pageable pageable
	) {
		log.debug("find qnaList started for auctionId: {}, pageable: {}", auctionId, pageable);

		AuctionEntity auctionEntity = auctionRepository.findById(auctionId)
			.orElseThrow(() -> new AuctionNotFoundException(AuctionCode.AUCTION_NOT_FOUND));

		Page<QnaDomainResponse> qnaEntityList = questionRepository.findQnaList(auctionEntity.getAuctionId(), pageable);

		Page<QnaServiceResponse> qnaResponseList = qnaEntityList.map(qnaEntity ->
			QnaServiceResponse.toDto(qnaEntity.getQuestionEntity(), qnaEntity.getAnswerEntity())
		);
		log.debug("find qnaList finished");

		return qnaResponseList;
	}

	@Transactional
	public CreateQuestionResponse addQuestion(
		Long memberId,
		CreateQuestionRequest createQuestionRequest
	) {

		log.debug("add question started for memberId: {}, CreateQuestionRequest: {}", memberId, createQuestionRequest);

		MemberEntity memberEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		AuctionEntity auctionEntity = auctionRepository.findById(createQuestionRequest.auctionId())
			.orElseThrow(() -> new AuctionNotFoundException(AuctionCode.AUCTION_NOT_FOUND));

		QuestionEntity questionEntity = QuestionEntity.builder()
			.title(createQuestionRequest.title())
			.content(createQuestionRequest.content())
			.memberEntity(memberEntity)
			.auctionEntity(auctionEntity)
			.build();

		questionRepository.save(questionEntity);

		log.debug("add question finished for memberId: {}, CreateQuestionRequest: {}", memberId, createQuestionRequest);

		return CreateQuestionResponse.toDto(questionEntity);

	}

	@Transactional
	public QuestionResponse modifyQuestion(
		Long memberId,
		Long questionId,
		ModifyQuestionRequest modifyQuestionRequest
	) {

		log.debug("modify question started for memberId: {}, questionId: {}, ModifyQuestionRequest: {}", memberId,
			questionId, modifyQuestionRequest);

		MemberEntity memberEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		QuestionEntity questionEntity = questionRepository.findByQuestionIdAndDeletedIsFalse(questionId)
			.orElseThrow(() -> new QnaNotFoundException(QnACode.QUESTION_NOT_FOUND));

		hasQuestionOwnership(questionEntity, memberEntity);

		validModifyAndRemove(questionEntity);

		questionEntity.updateQuestion(modifyQuestionRequest.title(), modifyQuestionRequest.content());

		log.debug("modify question finished for memberId: {}, questionId: {}, ModifyQuestionRequest: {}", memberId,
			questionId, modifyQuestionRequest);

		return QuestionResponse.toDto(questionEntity);

	}

	@Transactional
	public void removeQuestion(
		Long memberId,
		Long questionId
	) {

		log.debug("remove question started for memberId: {}, questionId: {}", memberId, questionId);

		MemberEntity memberEntity = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(MemberCode.MEMBER_NOT_FOUND));

		QuestionEntity questionEntity = questionRepository.findByQuestionIdAndDeletedIsFalse(questionId)
			.orElseThrow(() -> new QnaNotFoundException(QnACode.QUESTION_NOT_FOUND));

		hasQuestionOwnership(questionEntity, memberEntity);

		validModifyAndRemove(questionEntity);

		questionEntity.deleteQuestion();

		log.debug("remove question finished for memberId: {}, questionId: {}", memberId, questionId);

	}

	public void validModifyAndRemove(
		QuestionEntity questionEntity
	) {

		if (answerRepository.existsByQuestionEntityAndDeletedIsFalse(questionEntity)) {
			throw new QnaNotAllowedException(QnACode.QUESTION_DENY);
		}

	}

	public void hasQuestionOwnership(
		QuestionEntity questionEntity,
		MemberEntity memberEntity
	) {
		if (!questionEntity.getMemberEntity().getMemberId().equals(memberEntity.getMemberId())) {
			throw new InvalidMemberException(INVALID_MEMBER);
		}
	}

}
