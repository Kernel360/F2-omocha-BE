package org.omocha.domain.member;

import org.apache.commons.lang3.StringUtils;
import org.omocha.domain.image.ImageProvider;
import org.omocha.domain.likes.LikeReader;
import org.omocha.domain.member.exception.MemberAlreadyExistException;
import org.omocha.domain.member.validate.MemberValidator;
import org.omocha.domain.member.vo.Email;
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
	private final LikeReader likeReader;
	private final RandomNicknameGenerator randomNicknameGenerator;

	@Override
	@Transactional(readOnly = true)
	public MemberInfo.RetrieveMyInfo retrieveMyInfo(Long memberId) {
		log.debug("find me start for member {}", memberId);

		Member member = memberReader.getMember(memberId);

		String loginType = "general";
		if (StringUtils.isNotBlank(member.getProvider())) {
			loginType = member.getProvider();
		}

		int likeCount = likeReader.getMemberLikeCount(memberId);

		// TODO : 개선 필요(서버측 문제?) , Exception
		log.debug("find me finished for member {}", memberId);

		return MemberInfo.RetrieveMyInfo.toInfo(member, loginType, likeCount);
	}

	@Override
	public MemberInfo.RetrieveMemberInfo retrieveMemberInfo(Long memberId) {
		Member member = memberReader.getMember(memberId);

		return MemberInfo.RetrieveMemberInfo.toInfo(member);
	}

	@Override
	@Transactional
	public void addMember(MemberCommand.AddMember addMemberCommand) {

		if (memberReader.existsByEmail(addMemberCommand.email())) {
			throw new MemberAlreadyExistException(addMemberCommand.email());
		}

		String randomNickname = randomNicknameGenerator.generateRandomNickname();
		Member member = addMemberCommand.toEntity(randomNickname);

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
	public MemberInfo.Login retrieveMember(Email email) {
		Member member = memberReader.getMember(email);

		return MemberInfo.Login.toInfo(member);
	}

	@Override
	@Transactional
	public MemberInfo.ModifyMyInfo modifyMyInfo(MemberCommand.ModifyMyInfo modifyMyInfoCommand) {

		log.debug("modify member start for member {}", modifyMyInfoCommand.memberId());

		Member member = memberReader.getMember(modifyMyInfoCommand.memberId());

		memberValidator.validateDuplicateNickname(modifyMyInfoCommand.nickname());

		member.updateMember(
			modifyMyInfoCommand.nickname(),
			modifyMyInfoCommand.phoneNumber(),
			modifyMyInfoCommand.birth()
		);

		log.debug("modify member finished for member {}", modifyMyInfoCommand.memberId());

		return MemberInfo.ModifyMyInfo.toInfo(member);

	}

	@Override
	@Transactional
	public void modifyPassword(MemberCommand.ModifyPassword modifyPasswordCommand) {

		log.debug("modify encryptedPassword start for member {}", modifyPasswordCommand.memberId());

		Member member = memberReader.getMember(modifyPasswordCommand.memberId());

		member.updatePassword(modifyPasswordCommand.newEncryptedPassword());

		log.debug("modify encryptedPassword finished for member {}", modifyPasswordCommand.memberId());
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
