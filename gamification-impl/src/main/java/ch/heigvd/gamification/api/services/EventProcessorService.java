package ch.heigvd.gamification.api.services;

import ch.heigvd.gamification.api.model.Event;
import ch.heigvd.gamification.api.model.User;

public interface EventProcessorService {
    public void addBadgetoUser(String username, String apikey);
    public void applyRules(String apikey,Event event);
}
