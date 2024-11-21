package org.omocha.infra.category.repository;

import java.util.List;

import org.omocha.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

	@Query("SELECT c FROM Category c LEFT JOIN FETCH c.subCategories ORDER BY c.categoryId")
	List<Category> findAllWithSubCategories();

}
