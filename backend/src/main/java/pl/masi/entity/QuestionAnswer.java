package pl.masi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import pl.masi.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class QuestionAnswer extends BaseEntity {

    @Transient
    @JsonIgnore
    private static final String SEPARATOR = ";";

    @JsonIgnore
    private String body;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id")
    private Question question;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TestAnswer answer;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Integer> getChoiceSelections() {
        if (question.getType() != Question.Type.CHOICE){
            return null;
        }
        List<String> split = Arrays.asList(body.split(SEPARATOR));
        return split.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    @Transient
    public void setChoiceSelections(List<Integer> selections){
        if (question.getType() != Question.Type.CHOICE) {
            throw new RuntimeException("Incompatible choiceAnswer type!");
        }
        body = selections.stream().map(Object::toString).collect(Collectors.joining(SEPARATOR));
    }

}
