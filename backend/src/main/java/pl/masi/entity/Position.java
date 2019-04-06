package pl.masi.entity;

import lombok.Getter;
import lombok.Setter;
import pl.masi.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Position extends BaseEntity {

    private String name;

    private String description;

    private boolean active;
}
