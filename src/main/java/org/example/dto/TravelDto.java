package org.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.example.domain.AirTrip;
import org.example.domain.LandTrip;

@NoArgsConstructor
@Getter
@Setter
public class TravelDto {
    private ObjectId id;
    private LandTrip landTrip;
    private AirTrip airTrip;
    public TravelDto(LandTrip landTrip) {
        this.landTrip = landTrip;
    }
    public TravelDto(AirTrip airTrip) {
        this.airTrip = airTrip;
    }

    @Override
    public String toString() {
        return "TravelDto{" +
                "id=" + id +
                ", landTrip=" + landTrip +
                ", airTrip=" + airTrip +
                "}\n";
    }
}
