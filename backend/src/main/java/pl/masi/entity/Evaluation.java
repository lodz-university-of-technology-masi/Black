package pl.masi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.masi.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Evaluation extends BaseEntity {

    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "answer_id")
    @JsonIgnoreProperties("questionAnswers")
    private TestAnswer testAnswer;

    @OneToMany(mappedBy = "evaluation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "number")
    private List<QuestionAnswerEvaluation> answersEvaluations = new ArrayList<>();

    @JsonIgnore
    public int getPoints() {
        if (getAnswersEvaluations() != null) {
            int sum = 0;
            for (QuestionAnswerEvaluation e: getAnswersEvaluations()) {
                sum += e.getPoints() == null ? 0 : e.getPoints();
            }
            return sum;
        }
        return 0;
    }

}
