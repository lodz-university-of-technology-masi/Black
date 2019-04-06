package pl.masi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.masi.entity.base.BaseEntity;

import javax.persistence.*;

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
}
