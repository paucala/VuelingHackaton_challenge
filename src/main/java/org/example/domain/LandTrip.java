package org.example.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class LandTrip extends Trip {
    private Hotel hotel1;
    private Hotel hotel2;

    public LandTrip() {
    }

    public LandTrip(String name, String duration, List<String> cities, Hotel hotel1, Hotel hotel2) {
        super(name, "LandTrip", duration, cities);
        this.hotel1 = hotel1;
        this.hotel2 = hotel2;
    }

    @Override
    public String toString() {
        return "LandTrip{" +
                "hotel1=" + hotel1 +
                ", hotel2=" + hotel2 +
                "} " + super.toString();
    }
}
