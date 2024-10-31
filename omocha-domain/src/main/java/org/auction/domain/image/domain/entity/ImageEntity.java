package org.auction.domain.image.domain.entity;

import org.auction.domain.auction.domain.entity.AuctionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Long imageId;

	@Column(name = "file_name", nullable = false)
	private String fileName;

	@Column(name = "s3_key", nullable = false)
	private String s3Key;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_id")
	private AuctionEntity auctionEntity;

	@Builder
	public ImageEntity(
		String fileName,
		String s3Key,
		AuctionEntity auctionEntity
	) {
		this.fileName = fileName;
		this.s3Key = s3Key;
		this.auctionEntity = auctionEntity;
	}

	public void setAuctionEntity(
		AuctionEntity auctionEntity
	) {
		this.auctionEntity = auctionEntity;
		if (!auctionEntity.getImages().isEmpty()) {
			auctionEntity.getImages().add(this);
		}
	}
}
