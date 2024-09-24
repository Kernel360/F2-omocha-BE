package org.auction.domain.user.domain.entity;

import org.auction.domain.common.domain.entity.TimeTrackableEntity;
import org.auction.domain.user.domain.enums.Role;
import org.auction.domain.user.domain.enums.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
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

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "birth")
	private String birth;

	@Column(name = "email")
	private String email;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "profile_image_url")
	private String profileImageUrl;

	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "user_status", nullable = false)
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	// TODO: Social Login 할 때 provider, providerId 추가하기

}
