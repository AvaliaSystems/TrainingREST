package ch.heigvd.amt.gamification.api.endpoints;

import ch.heigvd.amt.gamification.api.LeaderboardsApi;
import ch.heigvd.amt.gamification.api.model.LeaderboardEntry;
import ch.heigvd.amt.gamification.entities.ApplicationEntity;
import ch.heigvd.amt.gamification.repositories.PointRewardRepository;
import ch.heigvd.amt.gamification.repositories.PointScaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LeaderboardsApiController implements LeaderboardsApi {
    static private final Integer DEFAULT_LIMIT = 5;

    @Autowired
    ServletRequest request;

    @Autowired
    PointRewardRepository pointRewardRepository;

    @Autowired
    PointScaleRepository pointScaleRepository;

    @Override
    public ResponseEntity<List<LeaderboardEntry>> getLeaderboard(String pointScale, @Valid Integer limit) {
        // Récupère l'application associée à partir de l'API Key
        ApplicationEntity applicationEntity = (ApplicationEntity) request.getAttribute("applicationEntity");

        // Vérifie qu'une pointScale existante soit spécifiée dans l'url
        if(pointScaleRepository.findByApplicationEntityAndName(applicationEntity, pointScale) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Définit la limite à la valeur par défaut si elle est absente de la requête
        limit = limit == null ? DEFAULT_LIMIT : limit;

        // Obtient et parcourt la liste des Leaders de la pointScale
        List<LeaderboardEntry> leaders = pointRewardRepository.findLeaders(applicationEntity.getId(), pointScale, limit)
            .stream()
            .map(entity -> new LeaderboardEntry()
                .appUserId(entity.getAppUserId())
                .pointsSum(entity.getPointsSum().intValue()))
            .collect(Collectors.toList());

        return ResponseEntity.ok(leaders);
    }
}
