package org.omocha.domain.qna.question;

import java.time.LocalDateTime;

import org.omocha.domain.member.vo.Email;
import org.omocha.domain.qna.answer.Answer;
import org.omocha.domain.qna.answer.AnswerInfo;

public class QuestionInfo {

	public record RetrieveQnas(
		QuestionInfo.QuestionDetails questionDetails,
		AnswerInfo.AnswerDetails answerDetails

	) {
		public static RetrieveQnas toInfo(
			Question question,
			Answer answer
		) {

			return new RetrieveQnas(
				QuestionInfo.QuestionDetails.toInfo(question),
				answer != null ? AnswerInfo.AnswerDetails.toInfo(answer) : null
			);
		}

	}

	public record QuestionDetails(
		Long questionId,
		String title,
		String content,
		LocalDateTime createdAt,
		Long memberId,
		Email email,
		String nickname,
		String profileImageUrl

	) {

		public static QuestionDetails toInfo(
			Question question
		) {
			return new QuestionDetails(
				question.getQuestionId(),
				question.getTitle(),
				question.getContent(),
				question.getCreatedAt(),
				question.getMember().getMemberId(),
				question.getMember().getEmail(),
				question.getMember().getNickname(),
				question.getMember().getProfileImageUrl()
			);

		}
	}

	public record AddQuestion(
		Long questionId,
		String title,
		String content,
		LocalDateTime createAt

	) {
		public static AddQuestion toInfo(Question question) {
			return new AddQuestion(
				question.getQuestionId(),
				question.getTitle(),
				question.getContent(),
				question.getCreatedAt()
			);
		}
	}

	public record ModifyQuestion(
		Long questionId

	) {
		public static ModifyQuestion toInfo(
			Question question
		) {
			return new ModifyQuestion(
				question.getQuestionId()

			);

		}
	}

}
