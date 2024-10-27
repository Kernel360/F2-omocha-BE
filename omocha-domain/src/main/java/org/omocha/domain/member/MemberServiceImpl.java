package org.omocha.domain.member;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	// private final PasswordEncoder passwordEncoder;
	private final MemberStore memberStore;
	private final MemberReader memberReader;

	@Override
	public MemberInfo.MemberDetailInfo addMember(MemberCommand.MemberCreateCommand memberCreateCommand) {

		Member member = Member.builder()
			.email(memberCreateCommand.email())
			.password(memberCreateCommand.password())
			.role(Role.ROLE_USER)
			.userStatus(UserStatus.ACTIVATE)
			.build();

		// TODO : security 추가 후 패스워드 인코딩 해야됨
		// Member member = Member.builder()
		// 	.email(memberCreateCommand.email())
		// 	.password(passwordEncoder.encode(memberCreateCommand.password()))
		// 	.role(Role.ROLE_USER)
		// 	.userStatus(UserStatus.ACTIVATE)
		// 	.build();

		return MemberInfo.MemberDetailInfo.toDto(memberStore.addMember(member));
	}

	// public boolean isEmailDuplicate(
	// 	String email
	// ) {
	// 	if (memberReader.existsByEmailAndProviderIsNull(email)) {
	// 		throw new MemberEmailAlreadyExistsException(MEMBER_ALREADY_EXISTS);
	// 	}
	//
	// 	return true;
	// }

	// TODO : 아래 두개의 메서드에서 에러가 발생했을 경우 각각 식별이 필요함
	//  Exception의 명확한 네이밍 => MemberNotFoundByIdException, MemberNotFoundByEmailException
	// public Member findMember(
	// 	Long memberId
	// ) {
	// 	return memberRepository.findById(memberId)
	// 		.orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
	// }

	// public Member findMember(
	// 	MemberCommand.MemberLoginCommand memberLoginCommand
	// ) {
	// 	Member member = memberReader.findByEmail(memberLoginCommand.email())
	// 		.orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
	//
	// 	validatePassword(memberLoginRequest, member);
	//
	// 	return member;
	// }

	// private void validatePassword(
	// 	MemberLoginRequest memberLoginRequest,
	// 	Member member
	// ) {
	// 	if (!passwordEncoder.matches(memberLoginRequest.password(), member.getPassword())) {
	// 		throw new InvalidPasswordException(INVALID_PASSWORD);
	// 	}
	// }

	// TODO : exception 수정 필요
	private void validateEmail(MemberCommand.MemberCreateCommand memberCreateCommand) {
		if (memberReader.existsByEmail(memberCreateCommand.email())) {
			// throw new MemberEmailAlreadyExistsException(MEMBER_ALREADY_EXISTS);
		}
	}
}
