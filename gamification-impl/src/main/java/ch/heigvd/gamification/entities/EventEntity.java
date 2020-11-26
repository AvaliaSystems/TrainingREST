package ch.heigvd.gamification.entities;

import ch.heigvd.gamification.RFC3339DateFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class EventEntity {

    private String timestamp;
    private String description;

    @ManyToOne
    private ApplicationEntity applicationEntity;

}
