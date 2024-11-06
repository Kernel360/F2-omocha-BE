package org.omocha.infra.repository;

import org.omocha.domain.auction.qna.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {
	Page<Qna> retrieveQnaList(
		Long auctionId,
		Pageable pageable
	);
}
