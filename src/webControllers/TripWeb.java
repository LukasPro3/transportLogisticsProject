package webControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import hibernate.CargoHib;
import hibernate.TripHib;
import hibernate.UserHib;
import model.Trip;
import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.LocalDateGsonSerializer;
import utils.TripGsonSerializer;
import utils.TripListGsonSerializer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Properties;


@Controller
public class TripWeb {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TransportLogistics");
    TripHib tripHib = new TripHib(entityManagerFactory);
    CargoHib cargoHib = new CargoHib(entityManagerFactory);

    @RequestMapping(value = "/trip/getAll", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllTrips() {

        List<Trip> allTrips = tripHib.getAllTrips();

        GsonBuilder gson = new GsonBuilder();
        Type tripList = new TypeToken<List<Trip>>() {
        }.getType();
        gson.registerTypeAdapter(Trip.class, new TripGsonSerializer()).registerTypeAdapter(tripList, new TripListGsonSerializer());
        Gson parser = gson.create();
        return parser.toJson(allTrips);
    }

    @RequestMapping(value = "trip/getTrip/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getTrip(@PathVariable(name = "id") int id) {
        Trip trip = tripHib.getTripById(id);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Trip.class, new TripGsonSerializer());
        Gson gson = builder.create();
        return gson.toJson(trip);
    }

    @RequestMapping(value = "/trip/createTrip", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createTrip(@RequestBody String request) {

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(Trip.class, new TripGsonSerializer());
        Gson parser = gson.create();
        Properties data = parser.fromJson(request, Properties.class);
        tripHib.createTrip(new Trip(cargoHib.getCargoById(Integer.parseInt(data.getProperty("cargoId"))), data.getProperty("startPoint"), data.getProperty("destination"), LocalTime.parse(data.getProperty("stopTime")), data.getProperty("stopLocation"), UserHib.getUserById(Integer.parseInt(data.getProperty("driver")))));
        Trip trip = tripHib.getTripCargoById(Integer.parseInt(data.getProperty("cargoId")));
        if (trip == null) {
            return "Trip not created";
        }
        return parser.toJson(trip);
    }

    @RequestMapping(value = "/trip/updateTrip", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateTrip(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        int id = Integer.parseInt(data.getProperty("id"));
        int cargoId = Integer.parseInt(data.getProperty("cargoId"));
        String startPoint = data.getProperty("startPoint");
        String destination = data.getProperty("destination");
        LocalTime stopTime = LocalTime.parse(data.getProperty("stopTime"));
        String stopLocation = data.getProperty("stopLocation");
        User user = UserHib.getUserById(Integer.parseInt(data.getProperty("driver")));

        Trip trip = tripHib.getTripById(id);

        if (trip != null) {
            trip.setCargoId(cargoHib.getCargoById(cargoId));
            trip.setStartPoint(startPoint);
            trip.setDestination(destination);
            trip.setStopTime(stopTime);
            trip.setStopLocation(stopLocation);
            trip.setDriversId(user);
            try {
                tripHib.updateTrip(trip);
            } catch (Exception e) {
                return "Error while updating";
            }
        }
        return "Update successful";

    }

    @RequestMapping(value = "/trip/deleteTrip/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteTrip(@PathVariable(name = "id") int id) {

        tripHib.deleteTrip(id);

        Trip trip = tripHib.getTripById(id);
        if (trip == null) {
            return "Success";
        } else {
            return "Not deleted";
        }

    }

}
