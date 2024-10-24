package org.auction.infra;

import org.auction.domain.qna.QnaDomainResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {

	Page<QnaDomainResponse> findQnaList(
		Long auctionId,
		Pageable pageable
	);

}
