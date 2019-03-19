package com.nibado.cmdoauth;

import com.nibado.cmdoauth.strava.*;
import com.nibado.cmdoauth.strava.model.*;
import retrofit2.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Strava {
    private static final String AUTHORIZE_URL = "https://www.strava.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code&approval_prompt=auto&scope=%s";

    private Server server;
    private Properties config = Config.get();
    private String code;
    private String accessToken;
    private CountDownLatch latch = new CountDownLatch(1);

    public static void main(String... argv) throws Exception {
        new Strava().run();
    }

    public void run() throws Exception {
        server = startServer();

        String redirectUrl = String.format("http://localhost:%s", server.getListeningPort());
        String authUrl = getAuthorizeUrl(config.getProperty("strava_client_id"), redirectUrl, "activity:read_all,read_all,profile:read_all");

        System.out.printf("Please point your browser to:\n\t%s\n", authUrl);

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(code);

        StravaApi api = StravaApi.create();

        Response<TokenExchangeResponse> response = api.tokenExchange(new TokenExchangeRequest(
                config.getProperty("strava_client_id"),
                config.getProperty("strava_client_secret"),
                code)).execute();

        System.out.println(response);
        System.out.println(response.body());

        TokenExchangeResponse tokenExchangeResponse = response.body();

        accessToken = tokenExchangeResponse.getAccessToken();

        Athlete athlete = api.getAthlete(new BearerToken(accessToken)).execute().body();

        System.out.println(athlete);

        List<Activity> activities = api.getAthleteActivities(new BearerToken(accessToken)).execute().body();

        activities.forEach(a -> {
            System.out.printf("%s: %s\n", a.getName(), a.getDistance());
        });

        server.stop();
    }

    private void callback(String query) {
        code = Stream.of(query.split("&"))
                .map(s -> s.split("="))
                .filter(pair -> pair[0].equals("code"))
                .map(pair -> pair[1])
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No code found in querystring " + query));

        latch.countDown();
    }

    private static String getAuthorizeUrl(String clientId, String redirectUrl, String scope) {
        return String.format(AUTHORIZE_URL, clientId, urlEncode(redirectUrl), urlEncode(scope));
    }

    private static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private Server startServer() {
        Server server = new Server(this::callback);
        try {
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return server;
    }
}
