package utils;

import com.google.gson.*;
import model.Cargo;

import java.lang.reflect.Type;
import java.util.List;

public class CargoListGsonSerializer implements JsonSerializer<List<Cargo>> {

    @Override
    public JsonElement serialize(List<Cargo> cargos, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        Gson parser = new GsonBuilder().create();

        for (Cargo c : cargos) {
            jsonArray.add(parser.toJson(c));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
