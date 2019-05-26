package pl.masi.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import pl.masi.entity.Question;
import pl.masi.entity.QuestionAnswer;
import pl.masi.entity.Test;
import pl.masi.entity.TestAnswer;
import pl.masi.repository.TestAnswerRepository;
import pl.masi.repository.TestRepository;
import pl.masi.service.TestAnswerService;
import pl.masi.service.UserService;
import pl.masi.validation.base.EntityValidator;

import java.util.List;
import java.util.Optional;

@Component
public class TestAnswerValidator extends EntityValidator<TestAnswer> {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestAnswerRepository testAnswerRepository;

    @Override
    public void validateObj(TestAnswer testAnswer, Errors errors) {
        Optional<Test> test = testRepository.findById(testAnswer.getTest().getId());
        if (!test.isPresent()) {
            errors.reject("Test not found");
            return;
        }

        Optional<TestAnswer> ans = testAnswerRepository.findByTestAndUser(test.get(), UserService.currentUser());
        if (ans.isPresent()){
            errors.reject("Test already solved!");
            return;
        }

        List<Question> questions = test.get().getQuestions();

        if(testAnswer.getQuestionAnswers().size() != questions.size()){
            errors.reject("Incorrect number of answers");
            return;
        }

        List<QuestionAnswer> answers = testAnswer.getQuestionAnswers();

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            QuestionAnswer answer = answers.get(i);
            if (question.getType() != answer.getType()) {
                errors.reject("Illegal question type in answer number " + ++i);
            }
        }

    }
}
