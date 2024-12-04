package org.omocha.infra.image.repository;

import java.util.Optional;

import org.omocha.domain.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

	Optional<Image> findImageByImagePath(String imagePath);
}
