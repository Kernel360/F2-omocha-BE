package org.omocha.domain.member;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class RandomNicknameGenerator {

	private static final List<String> prefixNicknames = List.of("귀여운", "멋진", "빠른", "강한", "용감한");
	private static final List<String> suffixNicknames = List.of("호랑이", "사자", "독수리", "곰", "늑대");

	private static final Random random = new Random();

	public String generateRandomNickname() {
		String prefix = prefixNicknames.get(random.nextInt(prefixNicknames.size()));
		String suffix = suffixNicknames.get(random.nextInt(suffixNicknames.size()));
		String randomNumber = String.format("%03d", random.nextInt(10000));
		return prefix + suffix + randomNumber;
	}
}
