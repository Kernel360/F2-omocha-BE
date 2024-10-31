package org.auction.client.oauth;

import static org.auction.client.common.code.OauthCode.*;

import java.util.Map;

import org.auction.client.exception.oauth.UnsupportedOAuthProviderException;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.auction.domain.member.domain.enums.Role;
import org.auction.domain.member.domain.enums.UserStatus;

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
			default -> throw new UnsupportedOAuthProviderException(UNSUPPORTED_OAUTH_PROVIDER);
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

	public MemberEntity toEntity() {
		return MemberEntity.builder()
			.username(name)
			.email(email)
			.role(Role.ROLE_USER)
			.provider(provider)
			.providerId(providerId)
			.userStatus(UserStatus.ACTIVATE)
			.build();
	}

}
