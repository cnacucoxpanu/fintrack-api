package com.fintrack.api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Аспект для логирования времени выполнения методов сервисного слоя.
 */
@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {

    /**
     * Логирует время выполнения всех методов в пакете service.
     */
    @Around("execution(* com.fintrack.api.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;

            log.info("[PERFORMANCE] Метод {} выполнен за {} мс", methodName, executionTime);

            if (executionTime > 1000) {
                log.warn("[SLOW_METHOD] Метод {} выполнялся дольше 1 секунды ({} мс)",
                        methodName, executionTime);
            }

            return result;
        } catch (Throwable ex) {
            long executionTime = System.currentTimeMillis() - start;
            log.error("[ERROR] Метод {} завершился ошибкой через {} мс: {}",
                    methodName, executionTime, ex.getMessage(), ex);
            throw ex;
        }
    }
}