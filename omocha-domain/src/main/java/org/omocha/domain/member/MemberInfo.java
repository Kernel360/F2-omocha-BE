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
		public static MemberDetail toDto(
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
		public static Login toDto(
			Member member
		) {
			return new Login(
				member.getMemberId(),
				member.getEmail(),
				member.getPassword()
			);
		}
	}
}
