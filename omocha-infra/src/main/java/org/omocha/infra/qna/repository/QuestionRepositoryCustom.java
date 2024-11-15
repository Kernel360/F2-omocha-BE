package org.omocha.infra.qna.repository;

import org.omocha.domain.qna.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {
	Page<Qna> retrieveQnaList(
		Long auctionId,
		Pageable pageable
	);
}
