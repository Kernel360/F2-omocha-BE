package org.omocha.domain.qna.question;

import org.hibernate.annotations.ColumnDefault;
import org.omocha.domain.auction.Auction;
import org.omocha.domain.common.BaseEntity;
import org.omocha.domain.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {

	// TODO : Time 관련 수정 필요
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionId;

	private String title;

	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_id", nullable = false)
	private Auction auction;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	// TODO : SoftDelete 논의 후 결정
	//  field 추가 or removedAt 추가
	@Column(name = "deleted", nullable = false)
	@ColumnDefault("false")
	private boolean deleted;

	@Builder
	public Question(
		String title,
		String content,
		Auction auction,
		Member member
	) {
		this.title = title;
		this.content = content;
		this.auction = auction;
		this.member = member;
	}

	public void updateQuestion(
		String title,
		String content
	) {
		this.title = title;
		this.content = content;
	}

	public void deleteQuestion() {
		this.deleted = true;
	}

}
