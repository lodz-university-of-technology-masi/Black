package pl.masi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.masi.entity.base.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Question extends BaseEntity {

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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Test test;

}
