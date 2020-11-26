package ch.heigvd.gamification.api.services;

import ch.heigvd.gamification.api.model.User;

public interface EventProcessorService {
    public void addBadgetoUser(String username, String apikey);
}
