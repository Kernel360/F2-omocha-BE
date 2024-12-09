package org.omocha.domain.common.exception.redis;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.common.exception.OmochaException;

public class LockInterruptedException extends OmochaException {
	public LockInterruptedException() {
		super(
			ErrorCode.RLOCK_NOT_AVAILABLE,
			"스레드가 대기 상태나 차단 상태에서 인터럽트 발생했습니다."
		);
	}
}
