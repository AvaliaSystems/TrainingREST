package ch.heigvd.amt.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class PointRewardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int points;
    private LocalDateTime timestamp;

    @ManyToOne
    private PointscaleEntity pointscaleEntity;

    @ManyToOne
    private UserEntity userEntity;
}
