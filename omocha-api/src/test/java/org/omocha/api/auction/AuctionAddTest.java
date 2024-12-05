package org.omocha.api.auction;

import static org.mockito.BDDMockito.*;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.omocha.api.ApiServerApplication;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.category.Category;
import org.omocha.domain.common.Role;
import org.omocha.domain.image.ImageProvider;
import org.omocha.domain.member.Member;
import org.omocha.domain.member.vo.Email;
import org.omocha.domain.member.vo.Password;
import org.omocha.domain.review.rating.Rating;
import org.omocha.infra.category.repository.CategoryRepository;
import org.omocha.infra.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest(classes = ApiServerApplication.class)
@ActiveProfiles("test")
public class AuctionAddTest {

	@Autowired
	private AuctionFacade auctionFacade;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@MockBean
	private ImageProvider imageProvider; // 이미지 업로드를 처리하는 프로바이더

	@Test
	@DisplayName("더미 경매 데이터를 삽입한다.")
	void insertDummyAuctions() {
		// 시작 시간 기록
		Instant start = Instant.now();
		given(imageProvider.uploadFile(any(MultipartFile.class))).willReturn("dummy/image/path.jpg");

		// 테스트용 멤버 생성 또는 기존 멤버 사용
		Member member = memberRepository.findById(1L).orElseGet(() -> {
			Member newMember = Member.builder()
				.email(new Email("test@test.com"))
				.password(new Password("test"))
				.averageRating(new Rating(0.0))
				.nickname("randomNickname")
				.role(Role.ROLE_USER)
				.memberStatus(Member.MemberStatus.ACTIVATE)
				.build();

			return memberRepository.save(newMember);
		});

		Category category = categoryRepository.findById(1L).orElseGet(() -> {
			Category newCategory = Category.builder()
				.name("영화")
				.parent(null)
				.build();
			// 필요한 필드 설정
			return categoryRepository.save(newCategory);
		});

		for (int i = 1; i <= 500; i++) {
			AuctionCommand.AddAuction addAuctionCommand = createDummyAuctionCommand(i, member.getMemberId(),
				category.getCategoryId());
			auctionFacade.addAuction(addAuctionCommand);
		}
		System.out.println("더미 경매 데이터를 성공적으로 삽입했습니다.");
		// 종료 시간 기록
		Instant end = Instant.now();
		// 소요 시간 계산
		Duration duration = Duration.between(start, end);
		long seconds = duration.getSeconds();

		System.out.println("500개의 더미 경매 데이터를 삽입했습니다.");
		System.out.println("소요 시간: " + seconds + "초");

	}

	private AuctionCommand.AddAuction createDummyAuctionCommand(int index, Long memberId, Long categoryId) {
		// MockMultipartFile을 사용하여 가짜 이미지 파일 생성
		MockMultipartFile imageFile = new MockMultipartFile(
			"image" + index + ".jpg",
			"image" + index + ".jpg",
			"image/jpeg",
			("이미지 데이터 " + index).getBytes(StandardCharsets.UTF_8)
		);

		List<MultipartFile> images = new ArrayList<>();
		images.add(imageFile); // 필요한 만큼 이미지 추가

		// 썸네일 이미지 파일 생성
		MockMultipartFile thumbnailFile = new MockMultipartFile(
			"thumbnail" + index + ".jpg",
			"thumbnail" + index + ".jpg",
			"image/jpeg",
			("썸네일 이미지 데이터 " + index).getBytes(StandardCharsets.UTF_8)
		);
		return AuctionCommand.AddAuction.builder()
			.title("더미 경매 제목 " + index)
			.content("더미 경매 설명 " + index)
			.startPrice(new Price(1000 + index))
			.bidUnit(new Price(100))
			.instantBuyPrice(new Price(1200 + index))
			.startDate(LocalDateTime.now())
			.endDate(LocalDateTime.now().plusDays(7))
			.memberId(memberId)
			.images(images)
			.categoryId(categoryId)
			.build();
	}

}
