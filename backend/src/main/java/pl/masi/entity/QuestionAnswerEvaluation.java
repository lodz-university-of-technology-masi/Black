package pl.masi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.masi.entity.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class QuestionAnswerEvaluation extends BaseEntity {

    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_answer_id")
    private QuestionAnswer questionAnswer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Evaluation evaluation;

}
