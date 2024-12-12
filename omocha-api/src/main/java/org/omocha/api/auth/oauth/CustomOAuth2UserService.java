package org.omocha.api.auth.oauth;

import java.util.Map;

import org.omocha.api.auth.jwt.UserPrincipal;
import org.omocha.domain.member.Member;
import org.omocha.domain.member.MemberCommand;
import org.omocha.domain.member.MemberReader;
import org.omocha.domain.member.MemberStore;
import org.omocha.domain.member.RandomNicknameGenerator;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final MemberReader memberReader;
	private final MemberStore memberStore;
	private final RandomNicknameGenerator randomNicknameGenerator;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		String provider = userRequest.getClientRegistration().getRegistrationId();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(provider, attributes);

		MemberCommand.OAuthProvider oAuthProvider = MemberCommand.OAuthProvider.builder()
			.provider(oAuth2UserInfo.getProvider())
			.providerId(oAuth2UserInfo.getProviderId())
			.build();

		Member member = memberReader.findMember(oAuthProvider)
			.orElseGet(() -> {
				String randomNickname = randomNicknameGenerator.generateRandomNickname();
				return memberStore.addMember(oAuth2UserInfo.toEntity(randomNickname));
			});

		return new UserPrincipal(member, attributes);
	}
}