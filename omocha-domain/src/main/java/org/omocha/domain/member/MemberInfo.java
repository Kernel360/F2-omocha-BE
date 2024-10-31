package org.omocha.domain.member;

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
		String email,
		String password
	) {
		public static Login toInfo(
			Member member
		) {
			return new Login(
				member.getMemberId(),
				member.getEmail(),
				member.getPassword()
			);
		}
	}

	public record MemberModifyInfo(
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
		public static MemberModifyInfo toInfo(
			Member member
		) {
			return new MemberModifyInfo(
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
	public record ProfileImageInfo(
		String imageUrl
	) {
		public static ProfileImageInfo toInfo(
			String imageUrl
		) {
			return new ProfileImageInfo(
				imageUrl
			);
		}

	}

	public record CurrentMemberInfo(
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
		public static CurrentMemberInfo toInfo(
			Member member
		) {
			return new CurrentMemberInfo(
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
}
