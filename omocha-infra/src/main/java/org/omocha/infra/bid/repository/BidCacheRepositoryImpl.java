package org.omocha.infra.bid.repository;

import org.omocha.domain.bid.Bid;
import org.omocha.domain.bid.BidCacheDto;
import org.omocha.infra.common.RedisPrefix;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BidCacheRepositoryImpl implements BidCacheRepository {

	private final RedisTemplate<String, BidCacheDto> redisTemplate;

	public BidCacheRepositoryImpl(@Qualifier("redisTemplateForBid") RedisTemplate<String, BidCacheDto> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public BidCacheDto findHighestBid(Long auctionId) {

		BidCacheDto test = redisTemplate.opsForValue().get(appendPrefix(auctionId.toString()));
		return test;

	}

	@Override
	public void storeHighestBid(Long auctionId, Bid bid) {

		BidCacheDto bidCacheDto = BidCacheDto.toRedis(bid);

		redisTemplate.opsForValue().set(appendPrefix(auctionId.toString()), bidCacheDto);

	}

	private String appendPrefix(String key) {
		return RedisPrefix.HIGHEST_BID_PREFIX.getPrefix() + key;
	}
}
