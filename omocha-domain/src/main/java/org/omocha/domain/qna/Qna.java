package org.omocha.domain.qna;

import org.omocha.domain.qna.answer.Answer;
import org.omocha.domain.qna.question.Question;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Qna {

	private Question question;

	private Answer answer;

	@QueryProjection
	public Qna(Question question, Answer answer) {
		this.question = question;
		this.answer = answer;
	}
}
