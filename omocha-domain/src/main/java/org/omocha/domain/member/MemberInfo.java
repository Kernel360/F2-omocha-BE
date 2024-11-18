package org.omocha.domain.member;

import org.omocha.domain.common.Role;

public class MemberInfo {

	public record MemberDetail(

		String email,
		String nickname,
		String birth,
		String phoneNumber,
		String imageUrl,
		Role role
	) {
		public static MemberDetail toInfo(
			Member member
		) {
			return new MemberDetail(
				member.getEmail(),
				member.getNickname(),
				member.getBirth(),
				member.getPhoneNumber(),
				member.getProfileImageUrl(),
				member.getRole()
			);
		}
	}

	public record Login(
		Long memberId,
		String password
	) {
		public static Login toInfo(
			Member member
		) {
			return new Login(
				member.getMemberId(),
				member.getPassword()
			);
		}
	}

	public record ModifyBasicInfo(
		// TODO : 회원 가입 정보 추가 후 변경
		Long memberId,
		String email,
		String userName,
		String nickName,
		String phoneNumber,
		String birth,
		Role role,
		String profileImageUrl
	) {
		public static ModifyBasicInfo toInfo(
			Member member
		) {
			return new ModifyBasicInfo(
				member.getMemberId(),
				member.getEmail(),
				member.getUsername(),
				member.getNickname(),
				member.getPhoneNumber(),
				member.getBirth(),
				member.getRole(),
				member.getProfileImageUrl()

			);

		}
	}

	// TODO : 수정 필요
	public record ModifyProfileImage(
		String imageUrl
	) {
		public static ModifyProfileImage toInfo(
			String imageUrl
		) {
			return new ModifyProfileImage(
				imageUrl
			);
		}

	}

	public record RetrieveCurrentMemberInfo(
		// TODO : 회원 가입 정보 추가 후 변경
		Long memberId,
		String email,
		String userName,
		String nickName,
		String phoneNumber,
		String birth,
		String profileImageUrl,
		String loginType,
		int likeCount
	) {
		public static RetrieveCurrentMemberInfo toInfo(
			Member member,
			String loginType,
			int likeCount
		) {
			return new RetrieveCurrentMemberInfo(
				member.getMemberId(),
				member.getEmail(),
				member.getUsername(),
				member.getNickname(),
				member.getPhoneNumber(),
				member.getBirth(),
				member.getProfileImageUrl(),
				loginType,
				likeCount
			);

		}
	}

	public record RetrievePassword(
		Long memberId,
		String password
	) {
		public static RetrievePassword toInfo(
			Member member
		) {
			return new RetrievePassword(
				member.getMemberId(),
				member.getPassword()
			);
		}
	}
}
