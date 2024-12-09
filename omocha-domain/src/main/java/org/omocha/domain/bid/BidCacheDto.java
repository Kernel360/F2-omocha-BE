package org.omocha.domain.bid;

import java.time.LocalDateTime;

import org.omocha.domain.auction.vo.Price;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(force = true)
public class BidCacheDto {

	@JsonProperty(value = "price")
	private final Price price;

	@JsonProperty(value = "createdAt")
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
