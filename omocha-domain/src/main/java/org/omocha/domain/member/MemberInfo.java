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
}
