package org.omocha.domain.auction.qna;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.member.Member;
import org.omocha.domain.member.MemberReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	private final QnaStore qnaStore;
	private final QnaReader qnaReader;

	private final QuestionValidator questionValidator;

	@Override
	@Transactional(readOnly = true)
	public Page<QuestionInfo.RetriveQnas> retriveQnas(QuestionCommand.QnaList qnaListCommand,
		Pageable sortPage) {

		log.info("find qnaList started for auctionId: {}, pageable: {}", qnaListCommand.auctionId(), sortPage);

		Auction auction = auctionReader.getAuction(qnaListCommand.auctionId());

		Page<Qna> qnaEntityList = qnaReader.getQnaList(auction.getAuctionId(),
			sortPage);

		Page<QuestionInfo.RetriveQnas> qnaResponseList = qnaEntityList.map(qna ->
			QuestionInfo.RetriveQnas.toInfo(qna.getQuestion(), qna.getAnswer()));
		log.debug("find qnaList finished");

		return qnaResponseList;

	}

	@Override
	@Transactional
	public QuestionInfo.AddQuestion addQuestion(QuestionCommand.AddQuestion addQuestionCommand) {

		log.info("add question started for createQuestionCommand: {}", addQuestionCommand);

		Member member = memberReader.findById(addQuestionCommand.memberId());

		Auction auction = auctionReader.getAuction(addQuestionCommand.auctionId());

		// TODO : Entity 생성 방식 논의 해야함
		// 		Bid , Auction , Question 다름
		Question question = addQuestionCommand.toEntity(member, auction);

		qnaStore.store(question);

		log.info("add question finished");

		return QuestionInfo.AddQuestion.toInfo(question);

	}

	@Override
	@Transactional
	public QuestionInfo.ModifyQuestion modifyQuestion(QuestionCommand.ModifyQuestion modifyQuestionCommand) {

		log.info("modify question started for modifyQuestionCommand: {}", modifyQuestionCommand);

		Member member = memberReader.findById(modifyQuestionCommand.memberId());

		Question question = qnaReader.getQuestion(modifyQuestionCommand.questionId());

		questionValidator.hasQuestionOwnership(question, member);

		questionValidator.validModifyAndRemove(question);

		question.updateQuestion(modifyQuestionCommand.title(), modifyQuestionCommand.content());

		return QuestionInfo.ModifyQuestion.toInfo(question);
	}

	@Override
	@Transactional
	public void questionRemove(QuestionCommand.RemoveQuestion removeQuestionCommand) {
		log.info("remove question started for deleteQuestionCommand: {}", removeQuestionCommand);

		Member member = memberReader.findById(removeQuestionCommand.memberId());

		Question question = qnaReader.getQuestion(removeQuestionCommand.questionId());

		questionValidator.hasQuestionOwnership(question, member);

		questionValidator.validModifyAndRemove(question);

		question.deleteQuestion();

		log.info("remove question finished for deleteQuestionCommand: {}", removeQuestionCommand);

	}

}
