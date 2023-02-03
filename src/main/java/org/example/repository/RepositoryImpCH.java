package org.example.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.example.domain.AirTrip;
import org.example.domain.LandTrip;
import org.example.domain.Travel;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

//This class will be the one in charge of exchanging data between de DB and the app
public class RepositoryImpCH implements Repository{

    //region DBCONNEXION
    //these are the necessary methods to connect to the DB, it's use is only local,
    // so we will have to change a few thing to use it on our computer,
    // you can also run it on your own CLUSTER using this:
    // "mongodb+srv://USER:PASSWORD@cluster0.hsyllwj.mongodb.net/?retryWrites=true&w=majority"
    // and inserting your own USER and PASSWORD
    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

    String uri = "mongodb://localhost:27017/";
    MongoClient mongoClient = MongoClients.create(uri);
    MongoDatabase database = mongoClient.getDatabase("Travels").withCodecRegistry(pojoCodecRegistry);
    //selects the Chinese DB
    MongoCollection<Travel> travels_collection = database.getCollection("TravelsCH", Travel.class);


    //endregion DBCONNEXION

    //region SEARCH
    //methods to check if a product exists on de DB
    @Override
    public boolean findbyId(ObjectId id) {
        boolean found = false;
        if (travels_collection.find(eq("Travel_id", id)) != null){
            found = true;
        }
        return found;
    }

    @Override
    public boolean findbyName(String name, String type) {
        boolean found = false;
        if (type.equals("LandTrip")){
            if (travels_collection.find(eq("landtrip.name", name)).first() != null){
                found = true;
            }
        }  else {
            if (travels_collection.find(eq("airtrip.name", name)).first() != null){
                found = true;
            }
        }
        return found;
    }
    // endregion SEARCH

    //region GET
   //methods to get info from the DB
    @Override
    public List<Travel> getAllTravels() {
        List<Travel> travels = new ArrayList<>();
        travels_collection.find().into(travels);
        return travels;
    }

    @Override
    public Travel getByName(String name, String type) {
        Travel travel;
        if (type.equals("LandTrip")){
            travel = travels_collection.find(eq("landtrip.name", name)).first();
        } else {
            travel = travels_collection.find(eq("airtrip.name", name)).first();
        }
        return travel;
    }
    //endregion GET

    //region CREATE
    //methods to create new options of travels
    @Override
    public void createLandTrip(LandTrip landTrip) {
        Travel travel = new Travel(landTrip);
        travels_collection.insertOne(travel);
    }

    @Override
    public void createAirTrip(AirTrip airTrip) {
        Travel travel = new Travel(airTrip);
        travels_collection.insertOne(travel);
    }
    //endregion CREATE

    //region UPDATE
    //methods to update info on the DB
    @Override
    public void updateLandTrip(ObjectId id, LandTrip landTrip) {
        travels_collection.updateOne(Filters.eq("Travel_id", id), Updates.set("landtrip", landTrip));
    }

    @Override
    public void updateAirTrip(ObjectId id, AirTrip airTrip) {
        travels_collection.updateOne(Filters.eq("Travel_id", id), Updates.set("airtrip", airTrip));
    }
    //endregion UPDATE

    //region DELETE
    // deletes a travel by ID
    @Override
    public void deleteTravel(ObjectId id) {
        travels_collection.deleteOne(eq("Travel_id", id));
    }
    //endregion DELETE

}
