package org.omocha.domain.bid;

import java.time.LocalDateTime;

import org.omocha.domain.auction.vo.Price;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class BidCacheDto {

	private final Price price;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime createdAt;

	private BidCacheDto(Bid bid) {
		this.price = bid.getBidPrice();
		this.createdAt = bid.getCreatedAt();
	}

	public static BidCacheDto toRedis(
		Bid bid
	) {
		return new BidCacheDto(
			bid
		);
	}
}
