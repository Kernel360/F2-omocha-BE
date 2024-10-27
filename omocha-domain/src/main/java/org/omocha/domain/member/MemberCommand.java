package org.omocha.domain.member;

public class MemberCommand {
	public record MemberCreateCommand(
		String email,
		String password
	) {

	}

	public record MemberDuplicateCommand(
		String email
	) {

	}

	public record MemberLoginCommand(
		String email,
		String password
	) {

	}
}
