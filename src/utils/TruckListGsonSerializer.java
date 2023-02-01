package utils;

import com.google.gson.*;
import model.Truck;

import java.lang.reflect.Type;
import java.util.List;

public class TruckListGsonSerializer implements JsonSerializer<List<Truck>> {
    @Override
    public JsonElement serialize(List<Truck> trucks, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        Gson parser = new GsonBuilder().create();

        for (Truck t : trucks) {
            jsonArray.add(parser.toJson(t));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
