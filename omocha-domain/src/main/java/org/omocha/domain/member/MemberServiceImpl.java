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
	private final MemberValidator memberValidator;
	private final MemberReader memberReader;

	@Override
	public MemberInfo.MemberDetail addMember(MemberCommand.MemberCreate memberCreateCommand) {

		// TODO : security 추가 후 패스워드 인코딩 해야됨
		Member member = memberCreateCommand.toEntity();
		return MemberInfo.MemberDetail.toDto(memberStore.addMember(member));
	}

	// TODO : 아래 두개의 메서드에서 에러가 발생했을 경우 각각 식별이 필요함
	//  Exception의 명확한 네이밍 => MemberNotFoundByIdException, MemberNotFoundByEmailException
	public MemberInfo.MemberDetail findMember(Long memberId) {
		return MemberInfo.MemberDetail.toDto(memberReader.findById(memberId));
	}

	// TODO : JWT 와 관련하여 협의 필요(INFO 객체)
	public MemberInfo.MemberDetail findMember(MemberCommand.MemberLogin memberLoginCommand) {
		Member member = memberReader.findByEmail(memberLoginCommand.email());
		memberValidator.validatePassword(memberLoginCommand.password(), member.getPassword());
		return MemberInfo.MemberDetail.toDto(member);
	}

}
