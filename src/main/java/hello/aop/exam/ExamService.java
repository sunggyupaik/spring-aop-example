package hello.aop.exam;

import hello.aop.exam.annotation.Trace;
import org.springframework.stereotype.Service;

@Service
public class ExamService {
	private final ExamRepository examRepository;

	public ExamService(ExamRepository examRepository) {
		this.examRepository = examRepository;
	}

	@Trace
	public void request(String itemId) {
		examRepository.save(itemId);
	}
}
