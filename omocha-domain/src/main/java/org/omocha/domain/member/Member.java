package org.omocha.domain.member;

import java.time.LocalDate;
import java.util.Objects;

import org.omocha.domain.common.BaseEntity;
import org.omocha.domain.common.Role;
import org.omocha.domain.member.vo.Email;
import org.omocha.domain.member.vo.EmailDbConverter;
import org.omocha.domain.member.vo.Password;
import org.omocha.domain.member.vo.PasswordDbConverter;
import org.omocha.domain.member.vo.PhoneNumber;
import org.omocha.domain.member.vo.PhoneNumberDbConverter;
import org.omocha.domain.review.rating.Rating;
import org.omocha.domain.review.rating.RatingDbConverter;

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

	@Convert(converter = EmailDbConverter.class)
	private Email email;

	@Convert(converter = PasswordDbConverter.class)
	private Password password;

	private String nickname;

	private String username;

	private LocalDate birth;

	@Convert(converter = PhoneNumberDbConverter.class)
	private PhoneNumber phoneNumber;

	private String profileImageUrl;

	@Convert(converter = RatingDbConverter.class)
	private Rating averageRating;

	@Enumerated(EnumType.STRING)
	private Role role;

	// TODO: 추후 통합 로그인으로 수정해야함
	private String provider;

	private String providerId;

	@Enumerated(EnumType.STRING)
	private MemberStatus memberStatus;

	private boolean emailVerified;

	@Builder
	public Member(
		Email email, Password password, String nickname,
		String username, LocalDate birth, PhoneNumber phoneNumber,
		String profileImageUrl, Rating averageRating, Role role,
		String provider, String providerId, MemberStatus memberStatus,
		boolean emailVerified
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
		this.memberStatus = memberStatus;
		this.emailVerified = emailVerified;
	}

	public enum MemberStatus {
		ACTIVATE, DEACTIVATE
	}

	public void updateMember(
		String nickname,
		PhoneNumber phoneNumber,
		LocalDate birth
	) {
		this.nickname = nickname;
		this.phoneNumber = phoneNumber;
		this.birth = birth;
	}

	public void updatePassword(
		Password password
	) {
		this.password = password;
	}

	public void updateProfileImage(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
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
