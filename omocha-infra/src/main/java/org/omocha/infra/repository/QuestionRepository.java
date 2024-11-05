package org.omocha.infra.repository;

import java.util.Optional;

import org.omocha.domain.auction.qna.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
	Optional<Question> findByQuestionIdAndDeletedIsFalse(Long questionId);
}
