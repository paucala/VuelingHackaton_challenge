package org.example.domain;

import lombok.*;

import java.util.List;


@Getter
@Setter
public class AirTrip extends Trip{
    private Flight flight1;
    private Flight flight2;

    public AirTrip() {
    }

    public AirTrip(String name, String duration, List<String> cities, Flight flight1, Flight flight2) {
        super(name, "AirTrip", duration, cities);
        this.flight1 = flight1;
        this.flight2 = flight2;
    }

    @Override
    public String toString() {
        return "AirTrip{" +
                "flight1=" + flight1 +
                ", flight2=" + flight2 +
                "} " + super.toString();
    }
}
