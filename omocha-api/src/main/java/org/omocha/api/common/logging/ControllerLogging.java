package org.omocha.api.common.logging;

import java.util.Arrays;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ControllerLogging {

	@Pointcut("execution(* org.omocha.api.*.*Controller.*(..))")
	public void controllerCut() {
	}

	@Before("controllerCut()")
	public void beforeLog(JoinPoint joinPoint) {

		MDC.put("traceId", UUID.randomUUID().toString().substring(0, 8));

		Object[] args = joinPoint.getArgs();
		if (args.length == 0) {
			log.info("no parameter");
		} else {
			StringBuilder logBuilder = new StringBuilder();

			String methodName = joinPoint.getSignature().getName();
			logBuilder.append("method name = ").append(methodName).append(" , ");

			Arrays.stream(args).forEach(arg -> {
				String type = arg.getClass().getSimpleName();
				logBuilder.append("parameter type = ").append(type).append(" , ");

				if ("UserPrincipal".equals(type)) {
					try {
						Object result = arg.getClass().getMethod("getId").invoke(arg);
						logBuilder.append("result = ").append(result).append(" , ");
					} catch (Exception e) {
						logBuilder.append("error invoking getId: ").append(e.getMessage()).append(" , ");
					}
				} else {
					logBuilder.append("parameter value = ").append(arg);
				}
			});

			log.info("{}", logBuilder.toString());
		}

	}

	@AfterReturning(value = "controllerCut()", returning = "returnObj")
	public void afterLog(JoinPoint joinPoint, Object returnObj) {

		log.info("method name = {} , return = {}", joinPoint.getSignature().getName(), returnObj);

	}

}
