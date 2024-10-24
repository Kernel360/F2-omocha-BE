package org.omocha.infra.jpa;

import java.util.Optional;

import org.omocha.domain.qna.AnswerEntity;
import org.omocha.domain.qna.QuestionEntity;
import org.omocha.infra.entity.AnswerEntity;
import org.omocha.infra.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

	Optional<AnswerEntity> findByAnswerIdAndDeletedIsFalse(Long answerId);

	Optional<AnswerEntity> findByQuestionEntityAndDeletedIsFalse(QuestionEntity question);

	boolean existsByQuestionEntityAndDeletedIsFalse(QuestionEntity questionEntity);

}
