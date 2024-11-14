package org.omocha.domain.member;

import org.omocha.domain.exception.MemberAlreadyExistException;
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
	public MemberInfo.RetrieveCurrentMemberInfo retrieveCurrentMemberInfo(Long memberId) {
		log.debug("find me start for member {}", memberId);

		Member member = memberReader.getMember(memberId);

		// TODO : 개선 필요(서버측 문제?) , Exception
		log.debug("find me finished for member {}", memberId);

		return MemberInfo.RetrieveCurrentMemberInfo.toInfo(member);
	}

	@Override
	@Transactional
	public void addMember(MemberCommand.AddMember addMemberCommand) {

		// TODO : Validator과 함께 수정 필요
		if (memberReader.existsByEmail(addMemberCommand.email())) {
			throw new MemberAlreadyExistException(addMemberCommand.email());
		}

		// TODO : security 추가 후 패스워드 인코딩 해야됨
		Member member = addMemberCommand.toEntity();

		memberStore.addMember(member);

	}

	// TODO : 아래 두개의 메서드에서 에러가 발생했을 경우 각각 식별이 필요함
	//  Exception의 명확한 네이밍 => MemberNotFoundByIdException, MemberNotFoundByEmailException
	// TODO : 컨벤션에 따라 메소드를 수정했습니다. Info 객체와의 매치?가 애매합니다.
	@Override
	@Transactional(readOnly = true)
	public MemberInfo.MemberDetail retrieveMember(Long memberId) {
		return MemberInfo.MemberDetail.toInfo(memberReader.getMember(memberId));
	}

	@Override
	@Transactional(readOnly = true)
	public MemberInfo.Login retrieveMember(String email) {
		Member member = memberReader.getMember(email);

		return MemberInfo.Login.toInfo(member);
	}

	@Override
	@Transactional
	public MemberInfo.ModifyBasicInfo modifyBasicInfo(MemberCommand.ModifyBasicInfo modifyBasicInfoCommand) {

		log.debug("modify member start for member {}", modifyBasicInfoCommand.memberId());

		Member member = memberReader.getMember(modifyBasicInfoCommand.memberId());

		memberValidator.validateDuplicateNickName(modifyBasicInfoCommand.nickName());

		member.updateMember(
			modifyBasicInfoCommand.nickName(),
			modifyBasicInfoCommand.phoneNumber()
		);

		log.debug("modify member finished for member {}", modifyBasicInfoCommand.memberId());

		return MemberInfo.ModifyBasicInfo.toInfo(member);

	}

	@Override
	@Transactional
	public void modifyPassword(MemberCommand.ModifyPassword modifyPasswordCommand) {

		log.debug("modify password start for member {}", modifyPasswordCommand.memberId());

		Member member = memberReader.getMember(modifyPasswordCommand.memberId());

		member.updatePassword(modifyPasswordCommand.newPassword());

		log.debug("modify password finished for member {}", modifyPasswordCommand.memberId());
	}

	@Override
	@Transactional
	public MemberInfo.ModifyProfileImage modifyProfileImage(MemberCommand.ModifyProfileImage profileImageCommand) {

		log.debug("modify profile image start for member {}", profileImageCommand.memberId());

		String imagePath = "";

		Member member = memberReader.getMember(profileImageCommand.memberId());

		if (member.getProfileImageUrl() != null) {
			imageProvider.deleteFile(member.getProfileImageUrl());
		}
		imagePath = imageProvider.uploadFile(profileImageCommand.profileImage());

		member.updateProfileImage(imagePath);

		log.debug("modify profile image finished for member {}", profileImageCommand.memberId());

		return MemberInfo.ModifyProfileImage.toInfo(imagePath);

	}

	@Override
	public MemberInfo.RetrievePassword retrievePassword(Long memberId) {

		return MemberInfo.RetrievePassword.toInfo(memberReader.getMember(memberId));

	}

}
