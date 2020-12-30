package ch.heigvd.amt.gamification.repositories.projections;

public interface LeaderboardProjection<String, BigDecimal> {
    String getAppUserId();

    BigDecimal getPointsSum();
}
