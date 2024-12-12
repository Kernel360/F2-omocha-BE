package org.omocha.api.common.util;

import org.mapstruct.Named;
import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.member.vo.Email;
import org.omocha.domain.member.vo.Password;
import org.omocha.domain.member.vo.PhoneNumber;
import org.omocha.domain.review.rating.Rating;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValueObjectMapper {

	private final PasswordManager passwordManager;

	@Named("toEmail")
	public Email toEmail(String email) {
		return new Email(email);
	}

	@Named("toPassword")
	public Password toEncryptedPassword(String password) {
		return new Password(passwordManager.encrypt(password));
	}

	@Named("toPhoneNumber")
	public PhoneNumber toPhoneNumber(String phoneNumber) {
		if (phoneNumber != null) {
			return new PhoneNumber(phoneNumber);
		}
		//TODO: VO에 null?
		return null;
	}

	@Named("toPrice")
	public Price toPrice(long price) {
		return new Price(price);
	}

	@Named("toRating")
	public Rating toRating(Double rating) {
		return new Rating(rating);
	}

}
