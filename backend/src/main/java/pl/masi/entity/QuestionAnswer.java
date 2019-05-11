package pl.masi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import pl.masi.entity.base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
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
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id")
    private Question question;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TestAnswer answer;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Integer> getChoiceAnswer() {
        if (!question.isChoice()){
            return null;
        }
        List<String> split = Arrays.asList(body.split(SEPARATOR));
        return split.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    @Transient
    public void setChoiceAnswer(List<Integer> selections){
        if (!question.isChoice()) {
            throw new RuntimeException("Incompatible QuestionAnswer type!");
        }
        body = selections.stream().map(Object::toString).collect(Collectors.joining(SEPARATOR));
    }

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public BigDecimal getScaleAnswer() {
        if (!question.isScale()){
            return null;
        }
        return new BigDecimal(body);
    }

    @Transient
    public void setScaleAnswer(BigDecimal scaleAnswer){
        if (!question.isScale()) {
            throw new RuntimeException("Incompatible QuestionAnswer type!");
        }
        body = scaleAnswer.toString();
    }

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOpenAnswer() {
        if (!question.isOpen()){
            return null;
        }
        return body;
    }

    @Transient
    public void setOpenAnswer(String openAnswer){
        if (!question.isOpen()) {
            throw new RuntimeException("Incompatible QuestionAnswer type!");
        }
        body = openAnswer;
    }

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public BigDecimal getNumberAnswer() {
        if (!question.isNumber()){
            return null;
        }
        return new BigDecimal(body);
    }

    @Transient
    public void setNumberAnswer(BigDecimal numberAnswer){
        if (!question.isNumber()) {
            throw new RuntimeException("Incompatible QuestionAnswer type!");
        }
        body = numberAnswer.toString();
    }

}
