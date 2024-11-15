package org.omocha.infra.conclude;

import org.omocha.domain.conclude.Conclude;
import org.omocha.domain.conclude.ConcludeReader;
import org.omocha.domain.conclude.exception.ConcludeNotFoundException;
import org.omocha.infra.conclude.repository.ConcludeRepository;
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
