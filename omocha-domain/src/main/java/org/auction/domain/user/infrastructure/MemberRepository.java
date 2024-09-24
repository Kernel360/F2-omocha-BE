package org.auction.domain.user.infrastructure;

import org.auction.domain.user.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
	
}
