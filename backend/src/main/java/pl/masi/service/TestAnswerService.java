package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import pl.masi.entity.TestAnswer;
import pl.masi.repository.TestAnswerRepository;
import pl.masi.service.base.EntityService;
import pl.masi.validation.TestAnswerValidator;
import pl.masi.validation.base.EntityValidator;

@Component
public class TestAnswerService extends EntityService<TestAnswer> {

    @Autowired
    private TestAnswerRepository repository;

    @Autowired
    private TestAnswerValidator testAnswerValidator;

    private void processTestAnswer(TestAnswer testAnswer) {
        testAnswer.getQuestionAnswers().forEach(questionAnswer -> questionAnswer.setAnswer(testAnswer));
    }

    @Override
    protected void beforeCreate(TestAnswer test) {
        processTestAnswer(test);
    }

    @Override
    protected void beforeUpdate(TestAnswer test) {
        processTestAnswer(test);
    }

    @Override
    protected JpaRepository<TestAnswer, Long> getEntityRepository() {
        return repository;
    }

    @Override
    protected EntityValidator<TestAnswer> getEntityValidator() {
        return testAnswerValidator;
    }
}
