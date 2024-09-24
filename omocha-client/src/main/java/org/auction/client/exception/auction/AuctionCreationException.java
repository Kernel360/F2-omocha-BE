package org.auction.client.exception.auction;

public class AuctionCreationException extends RuntimeException {

	public AuctionCreationException(
		String message
	) {
		super(message);
	}

	public AuctionCreationException(
		String message,
		Throwable cause
	) {
		super(message, cause);
	}
}
