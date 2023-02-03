package org.example.service;

import org.bson.types.ObjectId;
import org.example.domain.Travel;
import org.example.dto.TravelDto;
import org.example.repository.Repository;
import org.example.repository.RepositoryImpAR;
import org.example.repository.RepositoryImpCH;
import org.example.repository.RepositoryImpEN;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServiceImp implements Service{
    //region ATTRIBUTES
    //modelMapper transform objects and data transfer object, so the view only works DTO's
    private ModelMapper modelMapper = new ModelMapper();
    private Repository repositoryImp;
    //When we instance a new server, the constructor will pick a repository depending on the language chosen
    public ServiceImp(int op){
        if (op == 1){
            this.repositoryImp = new RepositoryImpEN();
        } else if (op == 2) {
            this.repositoryImp = new RepositoryImpCH();
        } else {
            this.repositoryImp = new RepositoryImpAR();
        }
    }
    //endregion ATTRIBUTES
    //region GET
    // these methods search on the DB to find one or all the travels
    @Override
    public TravelDto getTravel(String name, String type) {
        TravelDto travelDto = modelMapper.map(repositoryImp.getByName(name, type), TravelDto.class);
        return  travelDto;
    }
    @Override
    public List<TravelDto> getAlltravels() {
        List<TravelDto> travels = repositoryImp.getAllTravels().stream()
                .map(travel -> modelMapper.map(travel, TravelDto.class))
                .collect(Collectors.toList());
        return travels;
    }
    //endregion GET

    //region CREATE, UPDATE, DELETE
    //this region checks if a name exists in the DB to don't assign it twice, and return true if everything went alright
    //if we were to implement these methods properly, we should ask for everything that contains a travel object on the view,
    // such as the kind of trip, the name, the duration, and the specifics on the hotels or flights depending on the type of travel
    @Override
    public boolean createTravel(TravelDto travelDto) {
        Travel travel = modelMapper.map(travelDto, Travel.class);
        if (travelDto.getAirTrip() != null){
            if (!repositoryImp.findbyName(travel.getAirTrip().getName(), "AirTrip")){
                repositoryImp.createAirTrip(travel.getAirTrip());
                return true;
            } else {
                return false;
            }
        }else {
                if (!repositoryImp.findbyName(travel.getLandTrip().getName(), "LandTrip")) {
                    repositoryImp.createLandTrip(travel.getLandTrip());
                    return true;
            }else {
                    return false;
                }
        }
    }

    @Override
    public boolean updateTrave(ObjectId id, TravelDto travelDto) {
        Travel travel = modelMapper.map(travelDto, Travel.class);
        if (travelDto.getAirTrip() != null){
            if (!repositoryImp.findbyName(travel.getAirTrip().getName(), "AirTrip")){
                repositoryImp.updateAirTrip(id, travel.getAirTrip());
                return true;
            } else {
                return false;
            }
        }else {
            if (!repositoryImp.findbyName(travel.getLandTrip().getName(), "LandTrip")) {
                repositoryImp.updateLandTrip(id, travel.getLandTrip());
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public void deleteTravel(ObjectId id) {
        repositoryImp.deleteTravel(id);
    }
    //endregion CREATE, UPDATE, DELETE

    //This is the main method of this app, with is going to take all the results according to the search
    //this method search on the DB for all the land travels first, and for the air travels later
    //(this way we avoid to make an exception), converts them to travelDTO and send the two list together as one
    @Override
    public List<TravelDto> getTravelsTo(String string) {
        CharSequence charSequence = string.toLowerCase();
        List<TravelDto> landmatchtravels = repositoryImp.getAllTravels().stream().filter(travel -> travel.getLandTrip() != null && travel.getLandTrip().getCities().toString().toLowerCase().contains(charSequence))
                .map(travel -> modelMapper.map(travel, TravelDto.class)).toList();
        List<TravelDto> airmatchtravels = repositoryImp.getAllTravels().stream().filter(travel -> travel.getAirTrip() != null && travel.getAirTrip().getCities().toString().toLowerCase().contains(charSequence))
                .map(travel -> modelMapper.map(travel, TravelDto.class)).toList();
        List<TravelDto> matches = Stream.concat(landmatchtravels.stream(), airmatchtravels.stream())
                .collect(Collectors.toList());
        return matches;
    }
}
