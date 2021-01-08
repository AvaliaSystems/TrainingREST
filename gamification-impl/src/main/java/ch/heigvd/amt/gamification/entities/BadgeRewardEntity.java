package ch.heigvd.amt.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class BadgeRewardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime timestamp;

    @ManyToOne
    private BadgeEntity badgeEntity;

    @ManyToOne
    private UserEntity userEntity;
}
