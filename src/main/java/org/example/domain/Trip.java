package org.example.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public abstract class Trip {
    private String name;
    private String type;
    private String duration;
    private List<String> cities;

    public Trip(){
        this.cities = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Trip{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", duration='" + duration + '\'' +
                ", cities=" + cities +
                '}';
    }
}
