package org.omocha.domain.common.exception.redis;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.common.exception.OmochaException;

public class RLockNotAvailableException extends OmochaException {

	public RLockNotAvailableException() {
		super(
			ErrorCode.RLOCK_NOT_AVAILABLE,
			"분산 락 획득 시도에 실패했습니다."
		);
	}
}
