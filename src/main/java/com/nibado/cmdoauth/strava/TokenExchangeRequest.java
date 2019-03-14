package com.nibado.cmdoauth.strava;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class TokenExchangeRequest {
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("client_secret")
    private String clientSecret;
    private String code;
    @JsonProperty("grant_type")
    private String grantType = "authorization_code";
}
