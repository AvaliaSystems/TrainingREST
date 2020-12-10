package ch.heigvd.gamification.entities;

import lombok.Data;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class RuleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;

    private String actionName;
    private String actionTarget;
    private int actionAmount;

    @ManyToOne
    private ApplicationEntity applicationEntity;
}
