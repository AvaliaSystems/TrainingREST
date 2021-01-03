package ch.heigvd.amt.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

// This tells Hibernate to make a table out of this class
@Entity
@Data
public class BadgeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    // TODO : Ajouter un path pour une image ou un visuel du badge (dans l'optique de l'afficher dans la page de stats de l'application)

    @ManyToOne
    private ApplicationEntity applicationEntity;

}
