package ch.heigvd.gamification.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PointscaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String label;
    private int counter;

    @ManyToOne
    ApplicationEntity applicationEntity;

}
