package org.omocha.domain.member;

public class MemberInfo {

	public record MemberDetailInfo(
		String email,
		String nickname,
		String birth,
		String phoneNumber,
		String imageUrl,
		Role role
	) {
		public static MemberDetailInfo toDto(
			Member member
		) {
			return new MemberDetailInfo(
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
