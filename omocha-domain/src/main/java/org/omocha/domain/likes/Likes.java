package org.omocha.domain.likes;

import java.time.LocalDateTime;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
	name = "likes",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "likes_uk",
			columnNames = {"auction_id", "member_id"}
		)
	}
)
public class Likes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likesId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_id")
	private Auction auction;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	LocalDateTime createdAt;
}
