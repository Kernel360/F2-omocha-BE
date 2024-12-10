package org.omocha.domain.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

	// EXPLAIN : 락의 이름
	String key();

	// EXPLAIN : 락의 시간 단위
	TimeUnit timeUnit() default TimeUnit.SECONDS;

	// EXPLAIN : 락 획득을 위해 기다리는 시간
	long waitTime() default 5L;

	// EXPLAIN : 락 임대 시간
	long leaseTime() default 1L;
}
