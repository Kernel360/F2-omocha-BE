package org.auction.domain.demo.infrastructure;

import org.auction.domain.demo.domain.entity.DemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoRepository extends JpaRepository<DemoEntity, Long> {
}
