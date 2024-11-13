package org.omocha.infra.repository;

import java.util.List;

import org.omocha.domain.auction.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
	
	@Query("SELECT c FROM Category c LEFT JOIN FETCH c.subCategories")
	List<Category> findAllWithSubCategories();

}
