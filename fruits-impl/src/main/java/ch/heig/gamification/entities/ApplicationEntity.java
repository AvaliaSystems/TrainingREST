package ch.heig.gamification.entities;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import springfox.documentation.service.ApiKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
public class ApplicationEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID apiKey;

    private String name;

}
