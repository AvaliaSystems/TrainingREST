package ch.heigvd.amt.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
public class RuleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String type;
    private String awardBadge;
    private String awardPoints;
    private BigDecimal amount;

    @ManyToOne
    private ApplicationEntity application; //TODO application->applicationEntity
}
