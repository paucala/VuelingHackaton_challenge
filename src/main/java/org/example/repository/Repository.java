package org.example.repository;

import org.bson.types.ObjectId;
import org.example.domain.AirTrip;
import org.example.domain.LandTrip;
import org.example.domain.Travel;
import java.util.List;

//the majority of these methods are meant to be use only by users with admin privileges,
// to create, update and retrieve data from de DB instead of hardcode de data into it
//a regular user will only have access to the GET methods
public interface Repository {
    boolean findbyId(ObjectId id);
    boolean findbyName(String name, String type);
    List<Travel> getAllTravels();
    Travel getByName(String name, String type);
    void createLandTrip(LandTrip landTrip);
    void createAirTrip(AirTrip airTrip);
    void updateLandTrip(ObjectId id, LandTrip landTrip);
    void updateAirTrip(ObjectId id, AirTrip airTrip);
    void deleteTravel(ObjectId id);
}
