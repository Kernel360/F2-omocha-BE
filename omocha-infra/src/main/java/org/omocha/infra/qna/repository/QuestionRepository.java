package org.omocha.infra.qna.repository;

import java.util.Optional;

import org.omocha.domain.qna.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
	Optional<Question> findByQuestionIdAndDeletedIsFalse(Long questionId);
}
