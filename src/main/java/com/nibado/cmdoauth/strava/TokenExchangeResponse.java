package com.nibado.cmdoauth.strava;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenExchangeResponse {
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("expires_at")
    private long expiresAt;
    @JsonProperty("expires_in")
    private long expiresIn;

    private Athlete athlete;
    private String state;
}
