package ch.heigvd.amt.gamification.entities;

import ch.heigvd.amt.gamification.api.model.Application;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class BadgeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;

    @ManyToOne
    private ApplicationEntity application;

}
