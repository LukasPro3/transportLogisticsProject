package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Trip;

import java.lang.reflect.Type;

public class TripGsonSerializer implements JsonSerializer<Trip> {

    @Override
    public JsonElement serialize(Trip trip, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject tripJson = new JsonObject();
        tripJson.addProperty("id", trip.getId());
        tripJson.addProperty("cargoId", trip.getCargoId().getId());
        tripJson.addProperty("destination", trip.getDestination());
        tripJson.addProperty("startPoint", trip.getStartPoint());
        tripJson.addProperty("stopTime", String.valueOf(trip.getStopTime()));
        tripJson.addProperty("stopLocation", trip.getStopLocation());
        tripJson.addProperty("driver", String.valueOf(trip.getDriversId()));

        return tripJson;
    }

}
