package org.omocha.infra.repository;

import org.omocha.domain.auction.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Likes, Long> {

	boolean existsByAuction_AuctionIdAndMember_MemberId(Long auctionId, Long memberId);

	@Modifying
	@Query(value = "INSERT INTO LIKES(auction_id, member_id) VALUES(:auctionId, :memberId)", nativeQuery = true)
	void clickLike(Long auctionId, Long memberId);

	@Modifying
	@Query(value = "DELETE FROM LIKES WHERE auction_id = :auctionId AND member_id = :memberId", nativeQuery = true)
	void unClickLike(Long auctionId, Long memberId);

}
