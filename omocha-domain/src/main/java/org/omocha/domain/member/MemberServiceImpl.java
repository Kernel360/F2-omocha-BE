package org.omocha.domain.member;

import static org.omocha.domain.exception.code.MemberCode.*;

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

		// TODO : Validator과 함께 수정 필요
		if (memberReader.existsByEmail(memberCreateCommand.email())) {
			throw new RuntimeException(MEMBER_ALREADY_EXISTS.getResultMsg());
		}

		// TODO : security 추가 후 패스워드 인코딩 해야됨
		Member member = memberCreateCommand.toEntity();
		return MemberInfo.MemberDetail.toDto(memberStore.addMember(member));
	}

	// TODO : 아래 두개의 메서드에서 에러가 발생했을 경우 각각 식별이 필요함
	//  Exception의 명확한 네이밍 => MemberNotFoundByIdException, MemberNotFoundByEmailException
	@Override
	public MemberInfo.MemberDetail findMember(Long memberId) {
		return MemberInfo.MemberDetail.toDto(memberReader.findById(memberId));
	}

	@Override
	public MemberInfo.Login findMember(String email) {
		Member member = memberReader.findByEmail(email);

		return MemberInfo.Login.toDto(member);
	}
}
