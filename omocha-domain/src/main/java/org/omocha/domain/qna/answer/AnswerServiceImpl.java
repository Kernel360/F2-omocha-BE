package org.omocha.domain.qna.answer;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.member.Member;
import org.omocha.domain.member.MemberReader;
import org.omocha.domain.qna.QnaReader;
import org.omocha.domain.qna.QnaStore;
import org.omocha.domain.qna.question.Question;
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

		Member member = memberReader.getMember(addAnswerCommand.memberId());

		Question question = qnaReader.getQuestion(addAnswerCommand.questionId());

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

		Member member = memberReader.getMember(modifyAnswerCommand.memberId());

		Answer answer = qnaReader.getAnswer(modifyAnswerCommand.answerId());

		answerValidator.hasAuctionOwnership(answer.getQuestion().getAuction(), member);

		answer.updateAnswer(modifyAnswerCommand.title(), modifyAnswerCommand.content());

		return AnswerInfo.ModifyAnswer.toInfo(answer);
	}

	@Override
	public void removeAnswer(AnswerCommand.RemoveAnswer removeAnswerModify) {

		Member member = memberReader.getMember(removeAnswerModify.memberId());

		Answer answer = qnaReader.getAnswer(removeAnswerModify.answerId());

		answerValidator.hasAuctionOwnership(answer.getQuestion().getAuction(), member);

		answer.deleteAnswer();

	}
}
