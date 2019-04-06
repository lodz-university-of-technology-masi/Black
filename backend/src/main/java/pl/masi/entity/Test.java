package pl.masi.entity;

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
public class Test extends BaseEntity {

    private String name;

    @Column(name = "\"group\"")
    private Long group;

    private String language;

    @ManyToOne(fetch = FetchType.LAZY)
    private Position position;

    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "number")
    private List<Question> questions = new ArrayList<>();
}
