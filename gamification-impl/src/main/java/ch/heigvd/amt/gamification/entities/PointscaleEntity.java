package ch.heigvd.amt.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

// This tells Hibernate to make a table out of this class
@Entity
@Data
public class PointscaleEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private int points;

    @ManyToOne
    private ApplicationEntity applicationEntity;
}
