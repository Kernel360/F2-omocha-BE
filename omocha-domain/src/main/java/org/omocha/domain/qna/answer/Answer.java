package org.omocha.domain.qna.answer;

import org.hibernate.annotations.ColumnDefault;
import org.omocha.domain.common.BaseEntity;
import org.omocha.domain.qna.question.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends BaseEntity {
	// TODO : Time 관련 수정 필요

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long answerId;

	private String title;

	private String content;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	private Question question;

	// TODO : SoftDelete 논의 후 결정
	//  field 추가 or removedAt 추가
	@Column(name = "deleted", nullable = false)
	@ColumnDefault("false")
	private boolean deleted;

	@Builder
	public Answer(
		String title,
		String content,
		Question question
	) {
		this.title = title;
		this.content = content;
		this.question = question;
	}

	public void updateAnswer(
		String title,
		String content
	) {
		this.title = title;
		this.content = content;
	}

	public void deleteAnswer() {
		this.deleted = true;
	}

}
