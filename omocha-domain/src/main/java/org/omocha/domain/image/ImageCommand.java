package org.omocha.domain.image;

import org.omocha.domain.auction.Auction;

public class ImageCommand {

	public record AddAuctionImage(
		String fileName,
		String imagePath,
		Auction auction
	) {
		public Image toEntity(
			String fileName,
			String imagePath,
			Auction auction
		) {
			return Image.builder()
				.fileName(fileName)
				.imagePath(imagePath)
				.auction(auction)
				.build();
		}
	}
}
