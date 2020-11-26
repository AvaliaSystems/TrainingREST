package ch.heigvd.gamification.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ApplicationEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(unique=true)
    private String apiKey;

}
