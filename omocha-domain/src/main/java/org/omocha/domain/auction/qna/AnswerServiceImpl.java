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
	public AnswerInfo.CreateAnswer addAnswer(AnswerCommand.CreateAnswer createAnswerCommand) {
		log.debug("add answer started for createAnswerCommand: {}", createAnswerCommand);

		// TODO : Entity 조회 추후 리팩토링
		Member member = memberReader.findById(createAnswerCommand.memberId());

		Question question = questionReader.findQuestion(createAnswerCommand.memberId());

		Auction auction = auctionReader.findAuction(question.getAuction().getAuctionId());

		answerValidator.hasAuctionOwnership(auction, member);

		answerValidator.validateAnswerNotExists(question);

		Answer answer = Answer.builder()
			.title(createAnswerCommand.title())
			.content(createAnswerCommand.content())
			.question(question)
			.build();

		log.debug("add answer finished for createAnswerCommand: {}", createAnswerCommand);

		answerStore.store(answer);

		return AnswerInfo.CreateAnswer.toDto(answer);
	}

	@Override
	@Transactional
	public AnswerInfo.AnswerResponse modifyAnswer(AnswerCommand.ModifyAnswer modifyAnswerCommand) {
		log.debug("modify answer started for modifyAnswerCommand : {}", modifyAnswerCommand);

		Member member = memberReader.findById(modifyAnswerCommand.memberId());

		Answer answer = answerReader.findAnswer(modifyAnswerCommand.answerId());

		answerValidator.hasAuctionOwnership(answer.getQuestion().getAuction(), member);

		answer.updateAnswer(modifyAnswerCommand.title(), modifyAnswerCommand.content());

		// log.debug("modify answer finished for memberId: {} , answerId: {}, ModifyAnswerRequest : {}", memberId,
		// 	answerId,
		// 	modifyAnswerRequest);

		return AnswerInfo.AnswerResponse.toDto(answer);
	}

	@Override
	public void removeAnswer(AnswerCommand.DeleteAnswer deleteAnswerModify) {

		log.debug("remove answer started for deleteAnswerModify : {} ", deleteAnswerModify);

		Member member = memberReader.findById(deleteAnswerModify.memberId());

		Answer answer = answerReader.findAnswer(deleteAnswerModify.answerId());

		answerValidator.hasAuctionOwnership(answer.getQuestion().getAuction(), member);

		answer.deleteAnswer();

		log.debug("remove answer finished for deleteAnswerModify : {} ", deleteAnswerModify);

	}
}
