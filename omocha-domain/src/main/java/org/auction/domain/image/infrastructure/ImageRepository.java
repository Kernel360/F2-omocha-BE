package org.auction.domain.image.infrastructure;

import org.auction.domain.image.domain.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
	
}
