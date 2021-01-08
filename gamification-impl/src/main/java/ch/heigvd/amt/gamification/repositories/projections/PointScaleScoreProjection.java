package ch.heigvd.amt.gamification.repositories.projections;

public interface PointScaleScoreProjection<String, BigDecimal> {
    String getPointScale();

    BigDecimal getScore();
}
