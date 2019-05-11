package pl.masi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import pl.masi.entity.base.BaseEntity;

import javax.persistence.*;
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
    private String body;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Test test;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getAvaliableChoices() {
        if(type != Type.CHOICE){
            return null;
        }

        String[] choices = body.split(SEPARATOR);
        return Arrays.asList(choices);
    }

    @Transient
    public void setAvaliableChoices(List<String> choices) {
        if (type != Type.CHOICE) {
            throw new RuntimeException("Incompatible question type!");
        }
        body = String.join(SEPARATOR, choices);
    }
}
