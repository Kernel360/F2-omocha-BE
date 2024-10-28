package org.omocha.domain.member;

public interface MemberService {

	MemberInfo.MemberDetail addMember(MemberCommand.MemberCreate memberCreateCommand);

	MemberInfo.MemberDetail findMember(Long memberId);

	MemberInfo.MemberDetail findMember(MemberCommand.MemberLogin memberLoginCommand);

}
