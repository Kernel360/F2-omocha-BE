package org.omocha.domain.auction;

import java.util.List;

import org.omocha.domain.image.Image;

public interface AuctionImagesFactory {

	List<Image> store(Auction auction, AuctionCommand.AddAuction addCommand);
}
