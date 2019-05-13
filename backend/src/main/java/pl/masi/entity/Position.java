package pl.masi.entity;

import lombok.Getter;
import lombok.Setter;
import pl.masi.entity.base.BaseEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
public class Position extends BaseEntity {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    private boolean active;
}
