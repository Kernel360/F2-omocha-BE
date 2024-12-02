package org.omocha.domain.member;

import java.time.LocalDate;

import org.omocha.domain.common.Role;
import org.omocha.domain.member.vo.Email;
import org.omocha.domain.member.vo.Password;
import org.omocha.domain.member.vo.PhoneNumber;
import org.omocha.domain.review.rating.Rating;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MemberInfo {

	public record MemberDetail(
		Email email,
		String nickname,
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate birth,
		PhoneNumber phoneNumber,
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
		Password encryptedPassword
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

	public record ModifyMyInfo(
		// TODO : 회원 가입 정보 추가 후 변경
		Long memberId,
		Email email,
		String userName,
		String nickname,
		PhoneNumber phoneNumber,
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate birth,
		Role role,
		String profileImageUrl
	) {
		public static ModifyMyInfo toInfo(
			Member member
		) {
			return new ModifyMyInfo(
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

	public record RetrieveMyInfo(
		// TODO : 회원 가입 정보 추가 후 변경
		Long memberId,
		Email email,
		String userName,
		String nickname,
		PhoneNumber phoneNumber,
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate birth,
		String profileImageUrl,
		Rating averageRating,
		String loginType,
		int likeCount,
		boolean emailVerified
	) {
		public static RetrieveMyInfo toInfo(
			Member member,
			String loginType,
			int likeCount
		) {
			return new RetrieveMyInfo(
				member.getMemberId(),
				member.getEmail(),
				member.getUsername(),
				member.getNickname(),
				member.getPhoneNumber(),
				member.getBirth(),
				member.getProfileImageUrl(),
				member.getAverageRating(),
				loginType,
				likeCount,
				member.isEmailVerified()
			);

		}
	}

	public record RetrieveMemberInfo(
		Long memberId,
		String nickname,
		String profileImageUrl,
		Rating averageRating
	) {
		public static RetrieveMemberInfo toInfo(
			Member member
		) {
			return new RetrieveMemberInfo(
				member.getMemberId(),
				member.getNickname(),
				member.getProfileImageUrl(),
				member.getAverageRating()
			);
		}
	}

	public record RetrievePassword(
		Long memberId,
		Password password
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
