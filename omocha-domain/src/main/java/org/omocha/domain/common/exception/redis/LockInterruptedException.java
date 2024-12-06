package org.omocha.domain.common.exception.redis;

import org.omocha.domain.common.code.ErrorCode;
import org.omocha.domain.common.exception.OmochaException;

public class LockInterruptedException extends OmochaException {
	public LockInterruptedException() {
		super(
			ErrorCode.RLOCK_NOT_AVAILABLE,
			"락 획득 과정에서 interrupted 되었습니다."
		);
	}
}
