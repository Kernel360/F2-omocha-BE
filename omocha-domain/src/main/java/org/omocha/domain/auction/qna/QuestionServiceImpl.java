package org.omocha.domain.auction.qna;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.member.Member;
import org.omocha.domain.member.MemberReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

	private final MemberReader memberReader;
	private final AuctionReader auctionReader;
	private final QuestionStore questionStore;
	private final QuestionReader questionReader;

	private final QuestionValidator questionValidator;

	@Override
	@Transactional
	public QuestionInfo.CreateQuestionResponse addQuestion(QuestionCommand.CreateQuestion createQuestionCommand) {

		log.debug("add question started for createQuestionCommand: {}", createQuestionCommand);

		Member member = memberReader.findById(createQuestionCommand.memberId());

		Auction auction = auctionReader.findAuction(createQuestionCommand.auctionId());

		// TODO : Entity 생성 방식 논의 해야함
		// 		Bid , Auction , Question 다름
		Question question = Question.builder()
			.title(createQuestionCommand.title())
			.content(createQuestionCommand.content())
			.member(member)
			.auction(auction)
			.build();

		questionStore.store(question);

		log.debug("add question finished");

		return QuestionInfo.CreateQuestionResponse.toDto(question);

	}

	@Override
	@Transactional
	public QuestionInfo.ModifyQuestion modifyQuestion(QuestionCommand.ModifyQuestion modifyQuestionCommand) {

		Member member = memberReader.findById(modifyQuestionCommand.memberId());

		Question question = questionReader.findQuestion(modifyQuestionCommand.questionId());

		questionValidator.hasQuestionOwnership(question, member);

		questionValidator.validModifyAndRemove(question);

		question.updateQuestion(modifyQuestionCommand.title(), modifyQuestionCommand.content());

		// log.debug("modify question finished for memberId: {}, questionId: {}, ModifyQuestionRequest: {}", memberId,
		// 	questionId, modifyQuestionRequest);

		return QuestionInfo.ModifyQuestion.toDto(question);
	}
}
