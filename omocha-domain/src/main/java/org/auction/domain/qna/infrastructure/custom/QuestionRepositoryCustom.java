package org.auction.domain.qna.infrastructure.custom;

import org.auction.domain.qna.domain.response.QnaDomainResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {

	Page<QnaDomainResponse> findQnaList(
		Long auctionId,
		Pageable pageable
	);

}
