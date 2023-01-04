package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/*
* within은 특정 타입 내에 조인 포인트를 매칭한다
* execution은 메소드 실행 조인 포인트를 매칭한다. 스프링 AOP에서 가장 많이 쓰이며 가장 복잡하다
* */
public class WithinTest {
	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	Method helloMethod;

	@BeforeEach
	public void init() throws NoSuchMethodException {
		helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	@Test
	@DisplayName("타겟 클래스와 타입이 일치")
	public void withinExact() {
		pointcut.setExpression("within(hello.aop.member.MemberServiceImpl)");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("*를 사용하였으므로 MemberServiceImpl.class까지 범위가 포함되어 사용 가능")
	public void withinStar() {
		pointcut.setExpression("within(hello.aop.member.*Service*)");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void withinSubPackage() {
		pointcut.setExpression("within(hello.aop..*)");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	@DisplayName("타켓의 타입에만 직접 적용, 인터페이스를 선정하면 안된다.")
	void withinSuperTypeFalse() {
		pointcut.setExpression("within(hello.aop.member.MemberService)");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
	}

	@Test
	@DisplayName("execution은 타입 기반, 인터페이스 선정 가능")
	void executionSuperTypeTrue() {
		pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
		assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
	}
}
