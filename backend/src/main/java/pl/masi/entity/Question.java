package pl.masi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import pl.masi.entity.base.BaseEntity;
import pl.masi.utils.Range;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Entity
public class Question extends BaseEntity {

    @Transient
    @JsonIgnore
    private static final String SEPARATOR = ";";

    public enum Type {
        OPEN,
        CHOICE,
        SCALE,
        NUMBER
    }

    @Enumerated(EnumType.STRING)
    private Type type;

    private String content;

    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String body;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Test test;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getAvailableChoices() {
        if (!isChoice()) {
            return null;
        }

        String[] choices = body.split(SEPARATOR);
        return Arrays.asList(choices);
    }

    @Transient
    public void setAvailableChoices(List<String> choices) {
        if (!isChoice()) {
            throw new RuntimeException("Incompatible question type!");
        }
        body = String.join(SEPARATOR, choices);
    }

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Range<BigDecimal> getAvailableRange() {
        if (!isScale()) {
            return null;
        }

        String[] values = body.split(SEPARATOR);
        BigDecimal min = new BigDecimal(values[0]);
        BigDecimal max = new BigDecimal(values[1]);
        BigDecimal step = new BigDecimal(values[2]);
        return new Range<>(min, max, step);
    }

    @Transient
    public void setAvailableRange(Range<BigDecimal> range) {
        if (!isScale()) {
            throw new RuntimeException("Incompatible question type!");
        }

        body = range.getMin() + SEPARATOR + range.getMax() + SEPARATOR + range.getStep();
    }

    @JsonIgnore
    public boolean isOpen() {
        return type == Type.OPEN;
    }

    @JsonIgnore
    public boolean isChoice() {
        return type == Type.CHOICE;
    }

    @JsonIgnore
    public boolean isScale() {
        return type == Type.SCALE;
    }

    @JsonIgnore
    public boolean isNumber() {
        return type == Type.NUMBER;
    }
}
