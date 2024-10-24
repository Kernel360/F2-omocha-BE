package org.auction.infra;

import java.util.Optional;

import org.auction.domain.qna.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long>, QuestionRepositoryCustom {

	Optional<QuestionEntity> findByQuestionIdAndDeletedIsFalse(Long questionId);

}
