package hello.aop.member;

import hello.aop.member.annotation.MethodAop;

public class MemberServiceImpl implements MemberService {
	@Override
	@MethodAop("test value")
	public String hello(String param) {
		return "ok";
	}

	public String intern(String param) {
		return "ok";
	}
}
