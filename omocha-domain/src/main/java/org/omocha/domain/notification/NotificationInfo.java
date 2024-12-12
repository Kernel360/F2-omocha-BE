package org.omocha.domain.notification;

import java.time.LocalDateTime;

import org.omocha.domain.auction.Auction;
import org.omocha.domain.auction.vo.Price;
import org.omocha.domain.common.util.JsonUtils;
import org.omocha.domain.notification.enums.NotificationCode;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NotificationInfo {

	public record RootResponse<T>(
		Long notificationId,
		NotificationCode notificationCode,
		T data,
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime createAt
	) {
		public static <T> RootResponse<T> toInfo(Notification notification, Class<T> type) {
			return new RootResponse<>(
				notification.getNotificationId(),
				notification.getNotificationCode(),
				JsonUtils.fromJson(notification.getData(), type),
				notification.getCreatedAt()
			);
		}
	}

	public record AuctionResponse(
		Long auctionId,
		String title,
		String thumbnailPath,
		Price concludePrice,
		Price nowPrice
	) {
		public static AuctionResponse toInfo(
			Auction auction
		) {
			return new AuctionResponse(
				auction.getAuctionId(),
				auction.getTitle(),
				auction.getThumbnailPath(),
				auction.getConclude() != null ? auction.getConclude().getConcludePrice() : null,
				auction.getNowPrice()
			);
		}
	}
}
