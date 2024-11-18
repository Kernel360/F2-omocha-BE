package org.omocha.infra.likes.repository;

import java.time.LocalDateTime;

import org.omocha.domain.likes.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Likes, Long>, LikeRepositoryCustom {

	boolean existsByAuctionAuctionIdAndMemberMemberId(Long auctionId, Long memberId);

	@Modifying
	@Query(value = "INSERT INTO LIKES(auction_id, member_id, created_at) VALUES(:auctionId, :memberId, :createdAt)", nativeQuery = true)
	void clickLike(Long auctionId, Long memberId, LocalDateTime createdAt);

	@Modifying
	@Query(value = "DELETE FROM LIKES WHERE auction_id = :auctionId AND member_id = :memberId", nativeQuery = true)
	void unClickLike(Long auctionId, Long memberId);

	int countByMemberMemberId(Long memberId);
}
