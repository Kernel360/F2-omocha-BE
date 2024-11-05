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
	private final QuestionReader questionReader;
	private final AuctionReader auctionReader;
	private final AnswerStore answerStore;
	private final AnswerReader answerReader;

	private final AnswerValidator answerValidator;

	@Override
	public AnswerInfo.AddAnswer addAnswer(AnswerCommand.AddAnswer addAnswerCommand) {
		log.info("add answer started for createAnswerCommand: {}", addAnswerCommand);

		// TODO : Entity 조회 추후 리팩토링
		Member member = memberReader.findById(addAnswerCommand.memberId());

		Question question = questionReader.findQuestion(addAnswerCommand.memberId());

		Auction auction = auctionReader.getAuction(question.getAuction().getAuctionId());

		answerValidator.hasAuctionOwnership(auction, member);

		answerValidator.validateAnswerNotExists(question);

		Answer answer = addAnswerCommand.toEntity(question);

		log.info("add answer finished for createAnswerCommand: {}", addAnswerCommand);

		answerStore.store(answer);

		return AnswerInfo.AddAnswer.toInfo(answer);
	}

	@Override
	@Transactional
	public AnswerInfo.ModifyAnswerResponse modifyAnswer(AnswerCommand.ModifyAnswer modifyAnswerCommand) {
		log.info("modify answer started for modifyAnswerCommand : {}", modifyAnswerCommand);

		Member member = memberReader.findById(modifyAnswerCommand.memberId());

		Answer answer = answerReader.findAnswer(modifyAnswerCommand.answerId());

		answerValidator.hasAuctionOwnership(answer.getQuestion().getAuction(), member);

		answer.updateAnswer(modifyAnswerCommand.title(), modifyAnswerCommand.content());

		return AnswerInfo.ModifyAnswerResponse.toInfo(answer);
	}

	@Override
	public void removeAnswer(AnswerCommand.RemoveAnswer removeAnswerModify) {

		log.info("remove answer started for deleteAnswerModify : {} ", removeAnswerModify);

		Member member = memberReader.findById(removeAnswerModify.memberId());

		Answer answer = answerReader.findAnswer(removeAnswerModify.answerId());

		answerValidator.hasAuctionOwnership(answer.getQuestion().getAuction(), member);

		answer.deleteAnswer();

		log.info("remove answer finished for deleteAnswerModify : {} ", removeAnswerModify);

	}
}
