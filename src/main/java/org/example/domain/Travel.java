package org.example.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Travel {
    private ObjectId id;
    private LandTrip landTrip;
    private AirTrip airTrip;

    public Travel(LandTrip landTrip) {
        this.landTrip = landTrip;
    }
    public Travel(AirTrip airTrip) {
        this.airTrip = airTrip;
    }
}
