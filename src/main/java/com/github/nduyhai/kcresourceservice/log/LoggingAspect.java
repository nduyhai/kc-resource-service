package com.github.nduyhai.kcresourceservice.log;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  @Around("@annotation(com.github.nduyhai.kcresourceservice.log.Loggable)")
  public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info(
        "Entering method: {}. Arguments: {}",
        joinPoint.getSignature().getName(),
        Arrays.toString(joinPoint.getArgs()));

    Object result;
    try {
      result = joinPoint.proceed();
    } catch (Exception e) {
      log.error(
          "Exception in method: {}. {}",
          joinPoint.getSignature().getName(),
          Arrays.toString(e.getStackTrace()));
      throw e;
    }

    log.info("Exiting method: {}. Response {}", joinPoint.getSignature().getName(), result);

    return result;
  }
}
