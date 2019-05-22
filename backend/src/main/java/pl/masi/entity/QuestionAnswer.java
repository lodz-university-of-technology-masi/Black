package pl.masi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import pl.masi.entity.base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Enumerated(EnumType.STRING)
    private Question.Type type;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TestAnswer answer;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Integer> getChoiceAnswer() {
        if (!isChoice()){
            return null;
        }

        if (body == null) {
            return new ArrayList<>();
        }

        List<String> split = Arrays.asList(body.split(SEPARATOR));
        return split.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    @Transient
    public void setChoiceAnswer(List<Integer> selections){
        if (!isChoice() || selections == null) {
            return;
        }
        body = selections.stream().map(Object::toString).collect(Collectors.joining(SEPARATOR));
    }

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public BigDecimal getScaleAnswer() {
        if (!isScale() || body == null){
            return null;
        }
        return new BigDecimal(body);
    }

    @Transient
    public void setScaleAnswer(BigDecimal scaleAnswer){
        if (!isScale()) {
            return;
        }
        body = scaleAnswer.toString();
    }

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getOpenAnswer() {
        if (!isOpen()){
            return null;
        }
        return body;
    }

    @Transient
    public void setOpenAnswer(String openAnswer){
        if (!isOpen()) {
            return;
        }
        body = openAnswer;
    }

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public BigDecimal getNumberAnswer() {
        if (!isNumber() || body == null){
            return null;
        }
        return new BigDecimal(body);
    }

    @Transient
    public void setNumberAnswer(BigDecimal numberAnswer){
        if (!isNumber()) {
            return;
        }
        body = numberAnswer.toString();
    }

    @JsonIgnore
    public boolean isOpen() {
        return type == Question.Type.OPEN;
    }

    @JsonIgnore
    public boolean isChoice() {
        return type == Question.Type.CHOICE;
    }

    @JsonIgnore
    public boolean isScale() {
        return type == Question.Type.SCALE;
    }

    @JsonIgnore
    public boolean isNumber() {
        return type == Question.Type.NUMBER;
    }

}
