package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

@Aspect
@Configuration
public class Logging {
    private Logger log = LoggerFactory.getLogger(Logging.class);

    @Pointcut("execution (* com.example.demo.person.repository.PersonRepository.*(..))" )
    public void personRepositoryLogging() {
    }

    @Pointcut(" execution (* com.example.demo.person.endpoint.PersonEndpoint.*(..))")
    public void personEndpointLogging() {
    }

    @Around("personRepositoryLogging()")
    public Object logPersonRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long startTime = System.nanoTime();
            Object result = joinPoint.proceed();
            long estimatedTime = System.nanoTime() - startTime;
            log.info("method={} params={} body={} time={}", joinPoint.getSignature().getName(), joinPoint.getArgs(), result, estimatedTime);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }

    @Around("personEndpointLogging()")
    public Object logPersonEndpoint(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long startTime = System.nanoTime();
            ResponseEntity result = (ResponseEntity) joinPoint.proceed();
            long estimatedTime = System.nanoTime() - startTime;
            log.info("endpoint={} requestBody={} httpStatus={} responseBody={} time={}", joinPoint.getSignature().getName(), joinPoint.getArgs(), result.getStatusCode().value(), result.getBody(), estimatedTime);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }
}
