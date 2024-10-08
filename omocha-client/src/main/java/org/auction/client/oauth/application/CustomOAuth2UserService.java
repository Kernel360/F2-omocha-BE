package org.auction.client.oauth.application;

import java.util.Map;

import org.auction.client.jwt.UserPrincipal;
import org.auction.client.oauth.OAuth2UserInfo;
import org.auction.domain.member.domain.entity.MemberEntity;
import org.auction.domain.member.infrastructure.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		String provider = userRequest.getClientRegistration().getRegistrationId();

		Map<String, Object> attributes = oAuth2User.getAttributes();

		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(provider, attributes);

		MemberEntity member = memberRepository.findByProviderAndProviderId(
				oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId())
			.orElseGet(() -> memberRepository.save(oAuth2UserInfo.toEntity()));

		return new UserPrincipal(member, oAuth2User.getAttributes());
	}
}
