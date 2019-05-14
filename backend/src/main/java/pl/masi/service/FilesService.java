package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.masi.entity.Question;
import pl.masi.entity.Test;
import pl.masi.entity.Question.Type;
import pl.masi.utils.Range;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class FilesService {

    private static final String SEPARATOR = ";";

    @Autowired
    private TestService testService;

    public void importTest(MultipartFile multipartFile) throws IOException {
        String csv = new String(multipartFile.getBytes());
        List<List<String>> rows = new ArrayList<>();
        Test test = new Test();
        List<Question> questions = new ArrayList<>();
        try (Scanner scanner = new Scanner(csv)) {
            while (scanner.hasNextLine()) {
                rows.add(getListFromLine(scanner.nextLine()));
                questions.add(null);
            }
        }
        rows.forEach(row -> {
            Question question = new Question();
            Type type;
            switch (row.get(1)) { // O, W, S lub L, druga pozycja w każdym wierszu opisującym pytanie
                case "O":
                    type = Type.OPEN;
                    question.setType(type);
                    break;
                case "W":
                    type = Type.CHOICE;
                    question.setType(type);
                    question.setAvailableChoices(row.stream().skip(5).collect(Collectors.toList()));
                    break;
                case "S":
                    type = Type.SCALE;
                    question.setType(type);
                    question.setAvailableRange(new Range<>(new BigDecimal(row.get(5)), new BigDecimal(row.get(6)), new BigDecimal(row.get(7))));
                    break;
                case "L":
                    type = Type.NUMBER;
                    question.setType(type);
                    break;
                default:
                    System.err.println("Brak O, W, S lub L w drugiej pozycji wiersza");
            }
            question.setContent(row.get(3));
            question.setTest(test);
            questions.set(Integer.parseInt(row.get(0)) - 1, question); // Pierwsza pozycja każdego wiersza to numer pytania
        });
        test.setLanguage(rows.get(0).get(2)); // TODO MC Sprawdzenie czy każde pytanie ma taki sam język
        test.setQuestions(questions);
        testService.create(test);
    }

    public Optional<MultipartFile> exportTest(Long id) {
        Optional<Test> optionalTest = testService.findById(id);
        if (optionalTest.isPresent()) {
            StringBuilder csv = new StringBuilder();
            Test test = optionalTest.get();
            List<Question> questions = test.getQuestions();
            questions.forEach(question -> {
                String type = "";
                Boolean isBody = false;
                StringBuilder body = new StringBuilder();
                switch (question.getType()) {
                    case OPEN:
                        type = "O";
                        break;
                    case CHOICE:
                        type = "W";
                        isBody = true;
                        List<String> choices = question.getAvailableChoices();
                        body.append(choices.size()).append(SEPARATOR);
                        for (String choice : choices) {
                            body.append(choice);
                            if (choices.indexOf(choice) != choices.size() - 1) {
                                body.append(SEPARATOR);
                            }
                        }
                        break;
                    case SCALE:
                        type = "S";
                        isBody = true;
                        body.append("|").append(SEPARATOR).append(question.getAvailableRange().toCsvString());
                        break;
                    case NUMBER:
                        type = "L";
                        break;
                }
                StringBuilder csvLine = new StringBuilder();
                csvLine.append(questions.indexOf(question) + 1).append(SEPARATOR).append(type).append(SEPARATOR)
                        .append(test.getLanguage()).append(SEPARATOR).append(question.getContent());
                if (isBody) {
                    csvLine.append(SEPARATOR).append(body).append("\n");
                } else {
                    csvLine.append(SEPARATOR).append("|").append(SEPARATOR).append("\n");
                }
                csv.append(csvLine);
            });
            System.out.println(csv); // TODO MC Usunąć
//            return // TODO MC Zwracanie danych w odpowiedniej formie
        }
        return Optional.empty();
    }

    private List<String> getListFromLine(String line) {
        List<String> columns = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(SEPARATOR);
            while (rowScanner.hasNext()) {
                columns.add(rowScanner.next());
            }
        }
        return columns;
    }
}
