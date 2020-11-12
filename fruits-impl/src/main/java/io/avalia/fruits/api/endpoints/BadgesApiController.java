package io.avalia.fruits.api.endpoints;

import io.avalia.fruits.api.BadgesApi;
import io.avalia.fruits.repositories.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BadgesApiController implements BadgesApi {
    @Autowired
    BadgeRepository badgeRepository;
}
