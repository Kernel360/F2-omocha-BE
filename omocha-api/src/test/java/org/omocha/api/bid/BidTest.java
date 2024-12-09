package org.omocha.api.bid;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.omocha.api.ApiServerApplication;
import org.omocha.api.auction.AuctionFacade;
import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.AuctionCommand;
import org.omocha.domain.auction.AuctionReader;
import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.bid.BidCommand;
import org.omocha.domain.bid.BidInfo;
import org.omocha.domain.bid.BidService;
import org.omocha.domain.category.Category;
import org.omocha.domain.common.Role;
import org.omocha.domain.member.Member;
import org.omocha.domain.member.vo.Email;
import org.omocha.domain.member.vo.Password;
import org.omocha.domain.member.vo.PhoneNumber;
import org.omocha.infra.auction.repository.AuctionRepository;
import org.omocha.infra.category.repository.CategoryRepository;
import org.omocha.infra.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(classes = ApiServerApplication.class)
@ActiveProfiles("test")
public class BidTest {

	@Autowired
	private BidService bidService;

	@Autowired
	private AuctionRepository auctionRepository;

	@MockBean
	private MemberRepository memberRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private AuctionFacade auctionFacade;

	private Auction savedAuction;

	private List<Member> members;

	@Autowired
	private AuctionReader auctionReader;

	@BeforeEach
	public void setUp() {
		Category category = categoryRepository.findById(1L).orElseGet(() -> {
			Category newCategory = Category.builder()
				.name("영화")
				.parent(null)
				.build();
			// 필요한 필드 설정
			return categoryRepository.save(newCategory);
		});

		Member sellerMember = Member.builder()
			.email(new Email("test@test.com"))
			.password(new Password("test"))
			.nickname("randomNickname")
			.username("sellerUsername")
			.birth(LocalDate.now().minusYears(25))
			.phoneNumber(new PhoneNumber("010-1234-5678"))
			.role(Role.ROLE_USER)
			.memberStatus(Member.MemberStatus.ACTIVATE)
			.emailVerified(true)
			.build();
		ReflectionTestUtils.setField(sellerMember, "memberId", 1L);

		Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.ofNullable(sellerMember));

		AuctionCommand.AddAuction dummyAuctionCommand = createDummyAuctionCommand(1, sellerMember.getMemberId(),
			category.getCategoryId());

		Long savedAuctionId = auctionFacade.addAuction(dummyAuctionCommand).auctionId();

		savedAuction = auctionRepository.findById(savedAuctionId).orElse(null);

		auctionRepository.flush();

		members = new ArrayList<>();
		for (long i = 100; i <= 200; i++) {
			Member member = Member.builder()
				.email(new Email("user" + i + "@omocha.org"))
				.password(new Password("password" + i))
				.nickname("nick" + i)
				.username("username" + i)
				.birth(LocalDate.now().minusYears(20))
				.phoneNumber(new PhoneNumber("010-1234-56" + String.format("%02d", i % 100)))
				.role(Role.ROLE_USER)
				.memberStatus(Member.MemberStatus.ACTIVATE)
				.emailVerified(true)
				.build();
			ReflectionTestUtils.setField(member, "memberId", i);

			members.add(member);
		}
		for (Member member : members) {
			Mockito.when(memberRepository.findById(member.getMemberId()))
				.thenReturn(java.util.Optional.of(member));
		}

		Mockito.when(memberRepository.saveAll(Mockito.anyList())).thenAnswer(invocation -> invocation.getArgument(0));
	}

	@Test
	public void testConcurrentBids() throws InterruptedException {
		long numberOfBids = 100;
		int threadPoolSize = 20;
		ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
		CountDownLatch latch = new CountDownLatch(1);

		List<Future<BidResult>> futures = new ArrayList<>();

		long bidPriceValue = savedAuction.getStartPrice().getValue() + savedAuction.getBidUnit().getValue();
		Price bidPrice = new Price(bidPriceValue);

		for (int i = 0; i < numberOfBids; i++) {
			final Member member = members.get(i);
			final long buyerMemberId = member.getMemberId();
			Callable<BidResult> task = () -> {
				try {
					latch.await();

					BidCommand.AddBid addBidCommand = new BidCommand.AddBid(savedAuction.getAuctionId(), buyerMemberId,
						bidPrice);

					BidInfo.AddBid addBid = bidService.addBid(addBidCommand);

					return new BidResult(true, null);
				} catch (Exception e) {
					log.error("입찰 실패: {}", e.getMessage(), e);
					return new BidResult(false, e.getClass().getSimpleName());
				}
			};
			futures.add(executorService.submit(task));
		}

		latch.countDown();

		int successCount = 0;
		int failureCount = 0;

		List<String> failureReasons = new ArrayList<>();

		for (Future<BidResult> future : futures) {
			try {
				BidResult bidResult = future.get();
				if (bidResult.isSuccess()) {
					successCount++;
				} else {
					failureCount++;
					failureReasons.add(bidResult.getFailureReason());
				}
			} catch (ExecutionException | InterruptedException e) {
				e.printStackTrace();
				failureCount++;
				failureReasons.add(e.getClass().getSimpleName());
			}
		}

		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.MINUTES);

		Auction updatedAuction = auctionRepository.findById(savedAuction.getAuctionId()).orElseThrow();

		log.info("성공한 입찰 수 : {}", successCount);
		log.info("실패한 입찰 수 : {}", failureCount);
		log.info("실패 이유들: {}", failureReasons);

		assertEquals(1, successCount, "성공한 입찰 수가 예상과 일치하지 않습니다.");
		assertEquals(99, failureCount, "실패한 입찰 수가 예상과 일치하지 않습니다.");

		// assertEquals(new Price(110L), updatedAuction.getNowPrice(), "최종 현재 가격이 예상과 일치하지 않습니다.");
		// assertEquals(1L, updatedAuction.getBidCount(), "최종 입찰 횟수가 예상과 일치하지 않습니다.");

		// 실패 이유가 예상한 예외인지 검증
		for (String reason : failureReasons) {
			assertEquals("BidNotExceedingCurrentHighestException", reason, "입찰 가격이 최고가보다 높지 않습니다.");
		}
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

		return AuctionCommand.AddAuction.builder()
			.title("동시 입찰 테스트 경매")
			.content("동시 테스트 입찰 경매다.")
			.startPrice(new Price(100L))
			.bidUnit(new Price(10L))
			.instantBuyPrice(new Price(1000L))
			.startDate(LocalDateTime.now().minusDays(1))
			.endDate(LocalDateTime.now().plusDays(1))
			.memberId(memberId)
			.images(images)
			.categoryId(categoryId)
			.build();
	}

	private static class BidResult {
		private final boolean success;
		private final String failureReason;

		public BidResult(boolean success, String failureReason) {
			this.success = success;
			this.failureReason = failureReason;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getFailureReason() {
			return failureReason;
		}
	}

}
