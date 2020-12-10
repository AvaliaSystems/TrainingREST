package ch.heigvd.gamification.entities;

import ch.heigvd.gamification.api.model.Application;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Must be Unique by each application
    private String username;

    @ManyToOne
    private ApplicationEntity applicationEntity;

    @ManyToMany
    private List<BadgeEntity> badgeEntitys;

    @ManyToMany
    private List<PointscaleEntity> pointscaleEntitys;
}
