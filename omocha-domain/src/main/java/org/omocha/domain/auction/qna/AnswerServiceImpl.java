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
public class AnswerServiceImpl implements AnswerService {

	private final MemberReader memberReader;
	private final AuctionReader auctionReader;
	private final QnaReader qnaReader;
	private final QnaStore qnaStore;

	private final AnswerValidator answerValidator;

	@Override
	public AnswerInfo.AddAnswer addAnswer(AnswerCommand.AddAnswer addAnswerCommand) {

		Member member = memberReader.findById(addAnswerCommand.memberId());

		Question question = qnaReader.getQuestion(addAnswerCommand.memberId());

		Auction auction = auctionReader.getAuction(question.getAuction().getAuctionId());

		answerValidator.hasAuctionOwnership(auction, member);

		answerValidator.validateAnswerNotExists(question);

		Answer answer = addAnswerCommand.toEntity(question);

		qnaStore.store(answer);

		return AnswerInfo.AddAnswer.toInfo(answer);
	}

	@Override
	@Transactional
	public AnswerInfo.ModifyAnswer modifyAnswer(AnswerCommand.ModifyAnswer modifyAnswerCommand) {

		Member member = memberReader.findById(modifyAnswerCommand.memberId());

		Answer answer = qnaReader.getAnswer(modifyAnswerCommand.answerId());

		answerValidator.hasAuctionOwnership(answer.getQuestion().getAuction(), member);

		answer.updateAnswer(modifyAnswerCommand.title(), modifyAnswerCommand.content());

		return AnswerInfo.ModifyAnswer.toInfo(answer);
	}

	@Override
	public void removeAnswer(AnswerCommand.RemoveAnswer removeAnswerModify) {

		Member member = memberReader.findById(removeAnswerModify.memberId());

		Answer answer = qnaReader.getAnswer(removeAnswerModify.answerId());

		answerValidator.hasAuctionOwnership(answer.getQuestion().getAuction(), member);

		answer.deleteAnswer();

	}
}
