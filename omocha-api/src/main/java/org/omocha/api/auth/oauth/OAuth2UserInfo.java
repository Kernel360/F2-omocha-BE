package org.omocha.api.auth.oauth;

import java.util.Map;

import org.omocha.domain.common.Role;
import org.omocha.domain.member.Member;
import org.omocha.domain.member.exception.oauth.UnsupportedOAuthProviderException;
import org.omocha.domain.review.rating.Rating;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class OAuth2UserInfo {
	private String name;
	private String email;
	private String provider;
	private String providerId;

	public static OAuth2UserInfo of(String provider, Map<String, Object> attributes) {
		return switch (provider) {
			case "google" -> ofGoogle(attributes);
			case "naver" -> ofNaver(attributes);
			default -> throw new UnsupportedOAuthProviderException(provider);
		};
	}

	private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
		return OAuth2UserInfo.builder()
			.name((String)attributes.get("name"))
			.email((String)attributes.get("email"))
			.provider("google")
			.providerId((String)attributes.get("sub"))
			.build();
	}

	private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>)attributes.get("response");

		return OAuth2UserInfo.builder()
			.name((String)response.get("name"))
			.email((String)response.get("email"))
			.provider("naver")
			.providerId((String)response.get("id"))
			.build();
	}

	public Member toEntity() {
		return Member.builder()
			.username(name)
			.email(email)
			.averageRating(new Rating(0.0))
			.role(Role.ROLE_USER)
			.provider(provider)
			.providerId(providerId)
			.memberStatus(Member.MemberStatus.ACTIVATE)
			.build();
	}

}
