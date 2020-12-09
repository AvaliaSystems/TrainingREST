package ch.heigvd.gamification.api.spec.helpers;

import ch.heigvd.gamification.api.DefaultApi;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.util.Properties;
import java.util.TimeZone;

public class Environment {

    private DefaultApi api = new DefaultApi();

    public Environment() throws IOException {

        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("URL");
        api.getApiClient().setBasePath(url);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public DefaultApi getApi() {
        return api;
    }



}
