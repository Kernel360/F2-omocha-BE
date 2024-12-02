package org.omocha.domain.image;

public interface ImageStore {
	Image store(Image image);

	void delete(Image image);
}
