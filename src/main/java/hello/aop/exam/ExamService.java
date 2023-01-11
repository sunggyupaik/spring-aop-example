package hello.aop.exam;

import org.springframework.stereotype.Service;

@Service
public class ExamService {
	private final ExamRepository examRepository;

	public ExamService(ExamRepository examRepository) {
		this.examRepository = examRepository;
	}

	public void request(String itemId) {
		examRepository.save(itemId);
	}
}
