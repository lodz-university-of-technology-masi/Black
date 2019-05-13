package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.masi.entity.Question;
import pl.masi.entity.Test;
import pl.masi.entity.Question.Type;
import pl.masi.utils.Range;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class FilesService {

    private static final String SEPARATOR = ";"; //TODO MC Wrzucić w jakiś config?

    @Autowired
    private TestService testService;

    public void importTest(String csv) {
        List<List<String>> rows = new ArrayList<>();
        List<Question> questions = new ArrayList<Question>();
        Test test = new Test();
        try (Scanner scanner = new Scanner(csv)) {
            while (scanner.hasNextLine()) {
                rows.add(getListFromLine(scanner.nextLine()));
                List<String> lastRow = rows.get(rows.size() - 1);
                Question question = new Question();
                Type type;
                switch (lastRow.get(1)) { // O, W, S lub L, druga pozycja w każdym wierszu opisującym pytanie
                    case "O":
                        type = Type.OPEN;
                        question.setType(type);
                        break;
                    case "W":
                        type = Type.CHOICE;
                        question.setType(type);
                        question.setAvailableChoices(lastRow.stream().skip(5).collect(Collectors.toList()));
                        break;
                    case "S":
                        type = Type.SCALE;
                        question.setType(type);
                        question.setAvailableRange(new Range<>(new BigDecimal(lastRow.get(5)), new BigDecimal(lastRow.get(6)), new BigDecimal(lastRow.get(7))));
                        break;
                    case "L":
                        type = Type.NUMBER;
                        question.setType(type);
                        break;
                    default:
                        throw new Exception();
                }
                question.setContent(lastRow.get(3));
                question.setTest(test);
                questions.add(question); // TODO MC tutaj zrobić dodawania w odpowiednie miejsce listy w zależności od wielkości liczby w pierwszej pozycji wiersza
            }
        } catch (Exception e) {
            System.err.println("Brak O, W, S lub L w drugiej pozycji wiersza");
            e.printStackTrace();
        }
        test.setLanguage(rows.get(0).get(2)); // TODO MC Sprawdzenie czy każde pytanie ma taki sam język
        test.setQuestions(questions);
        testService.create(test);
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
