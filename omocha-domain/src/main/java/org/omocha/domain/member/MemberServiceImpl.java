package org.omocha.domain.member;

import static org.omocha.domain.exception.code.MemberCode.*;

import org.omocha.domain.image.ImageProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberStore memberStore;
	private final MemberValidator memberValidator;
	private final MemberReader memberReader;
	private final ImageProvider imageProvider;

	@Override
	@Transactional(readOnly = true)
	public MemberInfo.CurrentMemberInfo findCurrentMemberInfo(Long memberId) {
		log.debug("find me start for member {}", memberId);

		Member member = memberReader.findById(memberId);

		// TODO : 개선 필요(서버측 문제?) , Exception
		log.debug("find me finished for member {}", memberId);

		return MemberInfo.CurrentMemberInfo.toInfo(member);
	}

	@Override
	@Transactional
	public MemberInfo.MemberDetail addMember(MemberCommand.MemberCreate memberCreateCommand) {

		// TODO : Validator과 함께 수정 필요
		if (memberReader.existsByEmail(memberCreateCommand.email())) {
			throw new RuntimeException(MEMBER_ALREADY_EXISTS.getResultMsg());
		}

		// TODO : security 추가 후 패스워드 인코딩 해야됨
		Member member = memberCreateCommand.toEntity();
		return MemberInfo.MemberDetail.toInfo(memberStore.addMember(member));
	}

	// TODO : 아래 두개의 메서드에서 에러가 발생했을 경우 각각 식별이 필요함
	//  Exception의 명확한 네이밍 => MemberNotFoundByIdException, MemberNotFoundByEmailException
	@Override
	@Transactional(readOnly = true)
	public MemberInfo.MemberDetail findMember(Long memberId) {
		return MemberInfo.MemberDetail.toInfo(memberReader.findById(memberId));
	}

	@Override
	@Transactional(readOnly = true)
	public MemberInfo.Login findMember(String email) {
		Member member = memberReader.findByEmail(email);

		return MemberInfo.Login.toInfo(member);
	}

	@Override
	@Transactional
	public MemberInfo.MemberModifyInfo modifyBasicInfo(MemberCommand.MemberModify memberModifyCommand) {

		log.debug("modify member start for member {}", memberModifyCommand.memberId());

		Member member = memberReader.findById(memberModifyCommand.memberId());

		member.updateMember(
			memberModifyCommand.nickName(),
			memberModifyCommand.phoneNumber()
		);

		log.debug("modify member finished for member {}", memberModifyCommand.memberId());

		return MemberInfo.MemberModifyInfo.toInfo(member);

	}

	@Override
	@Transactional
	public void modifyPassword(MemberCommand.PasswordModify passwordModifyCommand) {

		log.debug("modify password start for member {}", passwordModifyCommand.memberId());

		Member member = memberReader.findById(passwordModifyCommand.memberId());

		// 비밀번호 확인 코드 있어야됨 현재 윗단에 올라가 있음

		member.updatePassword(passwordModifyCommand.newPassword());

		log.debug("modify password finished for member {}", passwordModifyCommand.memberId());
	}

	@Override
	@Transactional
	public MemberInfo.ProfileImageInfo modifyProfileImage(MemberCommand.ProfileImageModify profileImageCommand) {

		log.debug("modify profile image start for member {}", profileImageCommand.memberId());

		String imagePath = "";

		Member member = memberReader.findById(profileImageCommand.memberId());

		if (member.getProfileImageUrl() != null) {
			imageProvider.deleteFile(member.getProfileImageUrl());
		}
		imagePath = imageProvider.uploadFile(profileImageCommand.profileImage());

		member.updateProfileImage(imagePath);

		log.debug("modify profile image finished for member {}", profileImageCommand.memberId());

		return MemberInfo.ProfileImageInfo.toInfo(imagePath);

	}

}
