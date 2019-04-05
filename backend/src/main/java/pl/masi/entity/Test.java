package pl.masi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
