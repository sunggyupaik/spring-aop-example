package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

/*
* @Order를 통해서 Aspect 동작 순서를 정할 수 있다.
* 단, 클래스 단위로 @Order를 사용해야 적용이되므로 static class별로 @Aspect를 만든다
* */
@Slf4j
public class AspectAdviceOrder {
	@Order(1)
	@Aspect
	public static class LogAspect {
		@Around("hello.aop.order.aop.Pointcuts.allOrder()")
		public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
			log.info("[log] {}", joinPoint.getSignature());
			return joinPoint.proceed();
		}
	}

	@Order(2)
	@Aspect
	public static class TxAspect {
		@Around("hello.aop.order.aop.Pointcuts.orderAndService()")
		public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
			try {
				log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
				Object result = joinPoint.proceed();
				log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
				return result;
			} catch (Exception e) {
				log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
				throw e;
			} finally {
				log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
			}
		}
	}
}
