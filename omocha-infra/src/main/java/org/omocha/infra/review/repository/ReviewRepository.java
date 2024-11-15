package org.omocha.infra.review.repository;

import org.omocha.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

	boolean existsByAuctionAuctionIdAndReviewerMemberId(Long auctionId, Long memberId);

	@Query("SELECT AVG(r.rating) FROM Review r WHERE r.recipient.memberId = :recipientId")
	Double findAverageRatingByRecipientId(@Param("recipientId") Long recipientId);
}
