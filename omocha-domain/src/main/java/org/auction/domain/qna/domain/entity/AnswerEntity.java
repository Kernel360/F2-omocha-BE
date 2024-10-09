package org.auction.domain.qna.domain.entity;

import org.auction.domain.common.domain.entity.TimeTrackableEntity;
import org.auction.domain.member.domain.entity.MemberEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "answer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerEntity extends TimeTrackableEntity {
	// TODO : Time 관련 수정 필요

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long answerId;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	private QuestionEntity questionEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private MemberEntity memberEntity;

	@Builder
	public AnswerEntity(
		String title,
		String content,
		QuestionEntity questionEntity,
		MemberEntity memberEntity
	) {
		this.title = title;
		this.content = content;
		this.questionEntity = questionEntity;
		this.memberEntity = memberEntity;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	private void setContent(String content) {
		this.content = content;
	}

	public void updateAnswer(
		String title, String content
	) {
		setTitle(title);
		setContent(content);
	}

}
