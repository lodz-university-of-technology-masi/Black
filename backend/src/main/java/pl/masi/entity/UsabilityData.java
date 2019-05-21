package pl.masi.entity;

import lombok.Getter;
import lombok.Setter;
import pl.masi.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "USABILITY_DATA")
public class UsabilityData extends BaseEntity {

    @Column(name = "IP")
    private String ip;

    @Column(name = "BROWSER")
    private String browser;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "M_ID")
    private Integer measurementNumber;

    @Column(name = "SAVETIME")
    private ZonedDateTime saveTime;

    @Column(name = "RES_W")
    private Integer screenWidth;

    @Column(name = "RES_H")
    private Integer screenHeight;

    @Column(name = "MC")
    private Integer mouseClicks;

    @Column(name = "\"TIME\"")
    private Integer timeElapsed;

    @Column(name = "DIST")
    private Integer distance;

    @Column(name = "FAIL")
    private Boolean fail;

    @Column(name = "ERROR")
    private Integer error;

}
