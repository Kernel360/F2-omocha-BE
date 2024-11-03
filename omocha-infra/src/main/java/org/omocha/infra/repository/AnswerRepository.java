package org.omocha.infra.repository;

import org.omocha.domain.auction.qna.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
