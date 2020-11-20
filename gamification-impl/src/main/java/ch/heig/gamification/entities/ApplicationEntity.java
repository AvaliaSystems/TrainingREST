package ch.heig.gamification.entities;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import springfox.documentation.service.ApiKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
public class ApplicationEntity implements Serializable {

    @Id
    @Column(columnDefinition = "BINARY(16)", length = 16)
    private UUID apiKey;

    private String name;

}
