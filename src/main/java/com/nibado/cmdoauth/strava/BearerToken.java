package com.nibado.cmdoauth.strava;

public class BearerToken {
    private final String token;

    public BearerToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return String.format("Bearer %s", token);
    }
}
