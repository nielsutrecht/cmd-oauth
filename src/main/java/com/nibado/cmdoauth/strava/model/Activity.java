package com.nibado.cmdoauth.strava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    @JsonProperty("resource_state")
    private int resourceState;
    private String name;
    private double distance;

    /*
    "moving_time" : 4500,
  "elapsed_time" : 4500,
  "total_elevation_gain" : 0,
  "type" : "Ride",
     */
}
