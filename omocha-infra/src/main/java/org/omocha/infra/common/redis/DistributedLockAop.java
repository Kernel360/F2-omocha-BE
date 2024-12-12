package org.omocha.infra.common.redis;

import static org.omocha.infra.common.RedisPrefix.*;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.omocha.domain.common.annotation.DistributedLock;
import org.omocha.domain.common.exception.redis.LockInterruptedException;
import org.omocha.domain.common.exception.redis.RLockNotAvailableException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(1)
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {

	private final RedissonClient redissonClient;
	private final AopForTransaction aopForTransaction;

	@Around("@annotation(org.omocha.domain.common.annotation.DistributedLock)")
	public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

		String key = REDISSON_LOCK_PREFIX.getPrefix() +
			CustomSpringELParser.getDynamicValue(
				signature.getParameterNames(),
				joinPoint.getArgs(),
				distributedLock.key()
			);
		log.info("lock on [method:{}] [key:{}]", method, key);

		RLock rLock = redissonClient.getLock(key);
		String lockName = rLock.getName();
		try {
			boolean available =
				rLock.tryLock(
					distributedLock.waitTime(),
					distributedLock.leaseTime(),
					distributedLock.timeUnit()
				);

			if (!available) {
				throw new RLockNotAvailableException();
			}

			return aopForTransaction.proceed(joinPoint);
		} catch (InterruptedException e) {
			throw new LockInterruptedException();
		} finally {
			try {
				rLock.unlock();
				log.info("unlock on [Lock:{}] [key:{}]", lockName);
			} catch (IllegalMonitorStateException e) {
				log.info("Redisson Lock Already Unlocked");
			}
		}

	}
}
