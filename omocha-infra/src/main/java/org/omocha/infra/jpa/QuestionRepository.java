package org.omocha.infra.jpa;

import java.util.Optional;

import org.omocha.domain.qna.QuestionEntity;
import org.omocha.infra.querydsl.QuestionRepositoryCustom;
import org.omocha.infra.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long>, QuestionRepositoryCustom {

	Optional<QuestionEntity> findByQuestionIdAndDeletedIsFalse(Long questionId);

}
