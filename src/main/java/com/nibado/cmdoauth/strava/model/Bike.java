package com.nibado.cmdoauth.strava.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Bike {
    private String id;
    private boolean primary;
    private String name;
    @JsonProperty("resource_state")
    private int resourceState;
    private int distance;
}
