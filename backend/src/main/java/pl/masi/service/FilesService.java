package pl.masi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.masi.entity.Question;
import pl.masi.entity.Question.Type;
import pl.masi.entity.Test;
import pl.masi.entity.User;
import pl.masi.exception.MasiException;
import pl.masi.utils.Range;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FilesService {

    private static final String SEPARATOR = ";";

    @Autowired
    private TestService testService;

    @Value("${usabilitiy.screenshots.path}")
    private String screenshotsPath;

    @PreAuthorize("hasRole('ROLE_MODERATOR') || hasRole('ROLE_REDACTOR')")
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

    // FIXME sprawdzenie uprawnień
    public Optional<ByteArrayResource> exportTest(Long id) {
        Optional<Test> optionalTest = testService.findById(id);
        if (optionalTest.isPresent()) {
            StringBuilder csv = new StringBuilder();
            Test test = optionalTest.get();
            List<Question> questions = test.getQuestions();
            questions.forEach(question -> {
                String type = "";
                boolean isBody = false;
                StringBuilder body = new StringBuilder();
                //W switch'u sprawdzany i zapisywany jest typ pytania oraz wybory (możliwe odpowiedz) pytania wyboru
                // lub liczby dotyczące pytania skali
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
                //W csvLine budowany jest każdy wiersz pliku .csv
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
            ByteArrayResource resource = new ByteArrayResource(csv.toString().getBytes());
            return Optional.of(resource);
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

    /**
     * @param data base64 encoded image. Eg. "data:image/png;base64,iVBORw0K..."
     */
    public void saveScreenshot(String data) {
        data = trimScreenshotHeader(data);

        byte[] decoded = Base64.getDecoder().decode(data);

        User currentUser = UserService.currentUser();

        File file = new File(screenshotsPath + "/" + currentUser.getLogin() + "_" + LocalDateTime.now().toString() +".png");
        file.getParentFile().mkdirs();
        OutputStream os = null;
        try {
            file.createNewFile();
            os = new FileOutputStream(file);
            os.write(decoded);
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new MasiException("Cannot save screenshot file");
        }

    }

    private String trimScreenshotHeader(String data) {
        int idx = data.indexOf(',');
        if (idx < 0) {
            throw new MasiException("Illegal image format!");
        }
        return data.substring(idx + 1);
    }
}
