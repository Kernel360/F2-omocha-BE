package org.omocha.domain.common.exception.redis;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.common.exception.OmochaException;

public class RLockNotAvailable extends OmochaException {

	public RLockNotAvailable() {
		super(
			ErrorCode.RLOCK_NOT_AVAILABLE,
			"최초 획득 락 시도에 실패했습니다."
		);
	}
}
