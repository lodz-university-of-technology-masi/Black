package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import pl.masi.entity.Evaluation;
import pl.masi.repository.EvaluationRepository;
import pl.masi.service.base.EntityService;
import pl.masi.validation.EvaluationValidator;
import pl.masi.validation.base.EntityValidator;

@Component
public class EvaluationService extends EntityService<Evaluation> {

    @Autowired
    private EvaluationRepository repository;

    @Autowired
    private EvaluationValidator evaluationValidator;

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
    protected JpaRepository<Evaluation, Long> getEntityRepository() {
        return repository;
    }

    @Override
    protected EntityValidator<Evaluation> getEntityValidator() {
        return evaluationValidator;
    }
}
