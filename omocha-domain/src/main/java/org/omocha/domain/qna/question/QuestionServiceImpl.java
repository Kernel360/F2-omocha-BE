package org.omocha.domain.qna.question;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.member.Member;
import org.omocha.domain.member.MemberReader;
import org.omocha.domain.qna.Qna;
import org.omocha.domain.qna.QnaReader;
import org.omocha.domain.qna.QnaStore;
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
	public Page<QuestionInfo.RetrieveQnas> retrieveQnas(QuestionCommand.RetrieveQnas retrieveQnasCommand,
		Pageable sortPage) {

		Auction auction = auctionReader.getAuction(retrieveQnasCommand.auctionId());

		Page<Qna> qnaEntityList = qnaReader.getQnaList(auction.getAuctionId(),
			sortPage);

		Page<QuestionInfo.RetrieveQnas> qnaResponseList = qnaEntityList.map(qna ->
			QuestionInfo.RetrieveQnas.toInfo(qna.getQuestion(), qna.getAnswer()));

		return qnaResponseList;

	}

	@Override
	@Transactional
	public QuestionInfo.AddQuestion addQuestion(QuestionCommand.AddQuestion addQuestionCommand) {

		Member member = memberReader.getMember(addQuestionCommand.memberId());

		Auction auction = auctionReader.getAuction(addQuestionCommand.auctionId());

		// TODO : Entity 생성 방식 논의 해야함
		// 		Bid , Auction , Question 다름
		Question question = addQuestionCommand.toEntity(member, auction);

		qnaStore.store(question);

		return QuestionInfo.AddQuestion.toInfo(question);

	}

	@Override
	@Transactional
	public QuestionInfo.ModifyQuestion modifyQuestion(QuestionCommand.ModifyQuestion modifyQuestionCommand) {

		Member member = memberReader.getMember(modifyQuestionCommand.memberId());

		Question question = qnaReader.getQuestion(modifyQuestionCommand.questionId());

		questionValidator.hasQuestionOwnership(question, member);

		questionValidator.validAnswerExistAtAnswer(question);

		question.updateQuestion(modifyQuestionCommand.title(), modifyQuestionCommand.content());

		return QuestionInfo.ModifyQuestion.toInfo(question);
	}

	@Override
	@Transactional
	public void removeQuestion(QuestionCommand.RemoveQuestion removeQuestionCommand) {

		Member member = memberReader.getMember(removeQuestionCommand.memberId());

		Question question = qnaReader.getQuestion(removeQuestionCommand.questionId());

		questionValidator.hasQuestionOwnership(question, member);

		questionValidator.validAnswerExistAtAnswer(question);

		question.deleteQuestion();

	}

}
