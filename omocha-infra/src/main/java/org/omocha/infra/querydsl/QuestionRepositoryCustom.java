package org.omocha.infra.querydsl;

import org.omocha.domain.qna.QnaDomainResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepositoryCustom {

	Page<QnaDomainResponse> findQnaList(
		Long auctionId,
		Pageable pageable
	);

}
