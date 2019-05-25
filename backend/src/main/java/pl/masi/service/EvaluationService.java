package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;
import pl.masi.entity.Evaluation;
import pl.masi.entity.TestAnswer;
import pl.masi.entity.User;
import pl.masi.repository.EvaluationRepository;
import pl.masi.repository.TestAnswerRepository;
import pl.masi.service.base.EntityService;
import pl.masi.validation.EvaluationValidator;
import pl.masi.validation.base.EntityValidator;

@Service
public class EvaluationService extends EntityService<Evaluation> {

    @Autowired
    private EvaluationRepository repository;

    @Autowired
    private EvaluationValidator evaluationValidator;

    @Autowired
    private TestAnswerService testAnswerService;

    @Autowired
    private TestAnswerRepository testAnswerRepository;

    private void processTestAnswer(Evaluation evaluation) {
        evaluation.getAnswersEvaluations().forEach(evAns -> evAns.setEvaluation(evaluation));
    }

    @Override
    protected void beforeCreate(Evaluation evaluation) {
        processTestAnswer(evaluation);
    }

    @Override
    protected void beforeUpdate(Evaluation evaluation) {
        processTestAnswer(evaluation);
    }

    @Override
    protected void afterCreate(Evaluation evaluation) {
        TestAnswer answer = testAnswerService.findById(evaluation.getTestAnswer().getId()).get();
        answer.setEvaluated(true);
        testAnswerRepository.save(answer);

        User answerOwner = answer.getUser();
        aclManagementService.grantPermissions(evaluation, answerOwner, BasePermission.READ);
    }

    @Override
    protected JpaRepository<Evaluation, Long> getEntityRepository() {
        return repository;
    }

    @Override
    protected EntityValidator<Evaluation> getEntityValidator() {
        return evaluationValidator;
    }
}
