package ch.heigvd.amt.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Data
public class EventEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userId;
    private OffsetDateTime timestamp;
    private String eventType;
    private String subType;
    private int quantity;

    @ManyToOne
    private BadgeEntity badgeEntity;

    @ManyToOne
    private ApplicationEntity applicationEntity;
}
