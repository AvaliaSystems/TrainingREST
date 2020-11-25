package ch.heigvd.gamification.entities;

import ch.heigvd.gamification.api.model.Application;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class BadgeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String color;
    private String description;

    @ManyToOne
    private ApplicationEntity applicationEntity;

}
