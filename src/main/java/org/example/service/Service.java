package org.example.service;

import org.bson.types.ObjectId;
import org.example.domain.AirTrip;
import org.example.domain.LandTrip;
import org.example.dto.TravelDto;

import java.util.List;

public interface Service {
    TravelDto getTravel(String name, String type);
    List<TravelDto> getAlltravels();
    boolean createTravel(TravelDto travelDto);
    boolean updateTrave(ObjectId id, TravelDto travelDto);
    void deleteTravel(ObjectId id);
    List<TravelDto> getTravelsTo(String string);
}
