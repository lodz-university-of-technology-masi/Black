package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import pl.masi.entity.Test;
import pl.masi.entity.TestAnswer;
import pl.masi.entity.User;
import pl.masi.repository.TestAnswerRepository;
import pl.masi.service.base.EntityService;
import pl.masi.validation.TestAnswerValidator;
import pl.masi.validation.base.EntityValidator;

import java.util.List;

@Service
public class TestAnswerService extends EntityService<TestAnswer> {

    @Autowired
    private TestAnswerRepository repository;

    @Autowired
    private TestAnswerValidator testAnswerValidator;

    private void processTestAnswer(TestAnswer testAnswer) {
        testAnswer.getQuestionAnswers().forEach(questionAnswer -> questionAnswer.setAnswer(testAnswer));
    }

    @Override
    protected void beforeCreate(TestAnswer testAnswer) {
        processTestAnswer(testAnswer);
    }

    @Override
    protected void beforeUpdate(TestAnswer testAnswer) {
        processTestAnswer(testAnswer);
    }

    @Override
    protected void afterCreate(TestAnswer testAnswer) {
        grantTestOwnerPerms(testAnswer);
    }

    private void grantTestOwnerPerms(TestAnswer testAnswer) {
        Test test = testAnswer.getTest();

        List<User> owners= aclManagementService.getEntityOwners(test);

        for (User owner : owners) {
            aclManagementService.grantPermissions(testAnswer, owner, BasePermission.READ);
        }
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
