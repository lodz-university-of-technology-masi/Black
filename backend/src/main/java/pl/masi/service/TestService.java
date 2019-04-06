package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import pl.masi.entity.Test;
import pl.masi.repository.TestRepository;
import pl.masi.service.base.EntityService;

@Component
public class TestService extends EntityService<Test> {

    @Autowired
    private TestRepository testRepository;

    private void processTest(Test test) {
        test.getQuestions().forEach(question -> question.setTest(test));
    }

    @Override
    protected void beforeCreate(Test test) {
        processTest(test);
    }

    @Override
    protected void beforeUpdate(Test test) {
        processTest(test);
    }

    @Override
    protected JpaRepository<Test, Long> getEntityRepository() {
        return testRepository;
    }
}
