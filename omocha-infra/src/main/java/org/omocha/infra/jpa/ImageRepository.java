package org.omocha.infra.jpa;

import org.omocha.domain.image.ImageEntity;
import org.omocha.infra.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
	
}
