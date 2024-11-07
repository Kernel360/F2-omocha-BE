package org.omocha.domain.member;

import java.util.Objects;

import org.omocha.domain.auction.review.Rating;
import org.omocha.domain.auction.review.RatingDbConverter;
import org.omocha.domain.common.BaseEntity;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	// TODO : VO 로 변경 필요
	private String email;

	// TODO: Password VO로 변경해야함
	private String password;

	private String nickname;

	private String username;

	private String birth;

	private String phoneNumber;

	private String profileImageUrl;

	@Convert(converter = RatingDbConverter.class)
	private Rating averageRating;

	@Enumerated(EnumType.STRING)
	private Role role;

	// TODO: 추후 통합 로그인으로 수정해야함
	private String provider;

	private String providerId;

	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@Builder
	public Member(
		String email, String password, String nickname,
		String username, String birth, String phoneNumber,
		String profileImageUrl, Rating averageRating, Role role,
		String provider, String providerId, UserStatus userStatus
	) {
		this.password = password;
		this.nickname = nickname;
		this.username = username;
		this.birth = birth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.profileImageUrl = profileImageUrl;
		this.averageRating = averageRating;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
		this.userStatus = userStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Member that = (Member)o;
		return Objects.equals(memberId, that.memberId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(memberId);
	}

	public void updateAverageRating(Rating averageRating) {
		this.averageRating = averageRating;
	}

}
