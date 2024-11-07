package org.omocha.infra.repository;

import java.util.Optional;

import org.omocha.domain.auction.qna.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	Optional<Answer> findByAnswerIdAndDeletedIsFalse(Long answerId);

	boolean existsByQuestionQuestionIdAndDeletedIsFalse(Long questionId);
}
