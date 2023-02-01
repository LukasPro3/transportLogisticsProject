package utils;

import com.google.gson.*;
import model.Trip;

import java.lang.reflect.Type;
import java.util.List;

public class TripListGsonSerializer implements JsonSerializer<List<Trip>> {

    @Override
    public JsonElement serialize(List<Trip> trips, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        Gson parser = new GsonBuilder().create();

        for (Trip t : trips) {
            jsonArray.add(parser.toJson(t));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
