package pl.masi.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.masi.entity.Question;
import pl.masi.entity.Test;
import pl.masi.exception.TranslationServiceUninitializedException;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranslationService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Translate translate;

    @Value("${google.api.credentials.path}")
    private String credentialsPath;

    @PostConstruct
    public void init() throws IOException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(credentialsPath);
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(is);
            translate = TranslateOptions.newBuilder().setCredentials(googleCredentials).build().getService();
        } catch (FileNotFoundException e) {
            logger.warn("Cannot initialize TranslationService - cannot Load Google credentials! Set correct path in google.api.credentials.path property", e);
        }
    }

    public Test translate(Test test, String targetLang) {
        // TODO prawidłowa obsługa Test.group

        if (translate == null) {
            throw new TranslationServiceUninitializedException("Illegal use of uninitialized TranslationService!");
        }

        String srcLang = test.getLanguage().toLowerCase();
        targetLang = targetLang.toLowerCase();

        List<Translation> translate = this.translate.translate(
                getValuesToTranslate(test),
                Translate.TranslateOption.sourceLanguage(srcLang),
                Translate.TranslateOption.targetLanguage(targetLang));

        return buildTestFromTranslations(translate, test, targetLang);
    }

    public List<String> getValuesToTranslate(Test test) {
        ArrayList<String> values = new ArrayList<>();
        values.add(test.getName());

        for (Question question : test.getQuestions()) {
            values.add(question.getContent());
            if (question.isChoice()) {
                values.addAll(question.getAvailableChoices());
            }
        }
        return values;
    }

    public Test buildTestFromTranslations(List<Translation> translations, Test sourceTest, String targetLang) {
        Test targetTest = copyTest(sourceTest);
        targetTest.setLanguage(targetLang.toUpperCase());
        targetTest.setId(null);

        Iterator<Translation> it = translations.iterator();

        targetTest.setName(it.next().getTranslatedText());

        for (Question question : targetTest.getQuestions()) {
            question.setContent(it.next().getTranslatedText());
            if (question.isChoice()) {
                List<String> choices = new ArrayList<>();
                for (String _choice : question.getAvailableChoices()) {
                    choices.add(it.next().getTranslatedText());
                }
                question.setAvailableChoices(choices);
            }
        }
        return targetTest;
    }

    private Test copyTest(Test src) {
        Test cloned = new Test();
        cloned.setPosition(src.getPosition());
        cloned.setGroup(src.getGroup());

        List<Question> questions = src.getQuestions().stream().map(this::copyQuestion).collect(Collectors.toList());
        cloned.setQuestions(questions);

        return cloned;
    }

    private Question copyQuestion(Question src) {
        Question cloned = new Question();
        cloned.setType(src.getType());

        if (src.isChoice()) {
            cloned.setAvailableChoices(src.getAvailableChoices());
        } else if(src.isScale()){
            cloned.setAvailableRange(src.getAvailableRange());
        }
        return cloned;
    }
}
