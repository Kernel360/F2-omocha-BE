package org.omocha.infra;

import org.omocha.domain.auction.conclude.Conclude;
import org.omocha.domain.auction.conclude.ConcludeReader;
import org.omocha.domain.exception.ConcludeNotFoundException;
import org.omocha.infra.repository.ConcludeRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ConcludeReaderImpl implements ConcludeReader {

	private final ConcludeRepository concludeRepository;

	@Override
	public Conclude getConclude(Long auctionId) {
		return concludeRepository.findByAuctionAuctionId(auctionId)
			.orElseThrow(() -> new ConcludeNotFoundException(auctionId));
	}
}
