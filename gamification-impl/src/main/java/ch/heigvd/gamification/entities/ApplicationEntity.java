package ch.heigvd.gamification.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ApplicationEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String apiKey;

}
