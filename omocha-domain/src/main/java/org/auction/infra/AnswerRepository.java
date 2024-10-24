package org.auction.infra;

import java.util.Optional;

import org.auction.domain.qna.AnswerEntity;
import org.auction.domain.qna.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

	Optional<AnswerEntity> findByAnswerIdAndDeletedIsFalse(Long answerId);

	Optional<AnswerEntity> findByQuestionEntityAndDeletedIsFalse(QuestionEntity question);

	boolean existsByQuestionEntityAndDeletedIsFalse(QuestionEntity questionEntity);

}
