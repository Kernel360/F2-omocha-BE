package org.auction.domain.member.domain.entity;

import java.util.Objects;

import org.auction.domain.common.domain.entity.TimeTrackableEntity;
import org.auction.domain.member.domain.enums.Role;
import org.auction.domain.member.domain.enums.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends TimeTrackableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "email")
	private String email;

	// TODO: Password VO로 변경해야함
	@Column(name = "password")
	private String password;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "username")
	private String username;

	@Column(name = "birth")
	private String birth;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "profile_image_url")
	private String profileImageUrl;

	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	// TODO: 추후 통합 로그인으로 수정해야함
	@Column(name = "provider")
	private String provider;

	@Column(name = "provider_id")
	private String providerId;

	@Column(name = "user_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@Builder
	public MemberEntity(
		String email, String password, String nickname,
		String username, String birth, String phoneNumber,
		String profileImageUrl, Role role, String provider,
		String providerId, UserStatus userStatus
	) {
		this.password = password;
		this.nickname = nickname;
		this.username = username;
		this.birth = birth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.profileImageUrl = profileImageUrl;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
		this.userStatus = userStatus;
	}

	public void updateMember(
		String password, String nickname,
		String email, String phoneNumber
	) {
		this.password = password;
		this.nickname = nickname;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public void updateProfileImage(
		String profileImageUrl
	) {
		this.profileImageUrl = profileImageUrl;
	}

	public void updatePassword(
		String password
	) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MemberEntity that = (MemberEntity)o;
		return Objects.equals(memberId, that.memberId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(memberId);
	}

}
