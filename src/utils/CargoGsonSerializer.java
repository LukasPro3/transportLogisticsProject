package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Cargo;

import java.lang.reflect.Type;

public class CargoGsonSerializer implements JsonSerializer<Cargo> {

    @Override
    public JsonElement serialize(Cargo cargo, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject cargoJson = new JsonObject();
        cargoJson.addProperty("cargoId", cargo.getId());
        cargoJson.addProperty("price", cargo.getPrice());
        cargoJson.addProperty("amount", cargo.getAmount());
        cargoJson.addProperty("weight", cargo.getWeight());
        cargoJson.addProperty("height", cargo.getHeight());
        cargoJson.addProperty("width", cargo.getWidth());
        cargoJson.addProperty("length", cargo.getLength());
        cargoJson.addProperty("collectionPoint", cargo.getCollectionPoint());
        cargoJson.addProperty("deliveryPoint", cargo.getDeliveryPoint());
        cargoJson.addProperty("otherInfo", cargo.getOtherInfo());
        cargoJson.addProperty("crm", cargo.getCrm());
        cargoJson.addProperty("currentStatus", String.valueOf(cargo.getCurrentStatus()));
        cargoJson.addProperty("responsible", String.valueOf(cargo.getResponsible()));
        cargoJson.addProperty("assignedTo", String.valueOf(cargo.getAssignedTo()));

        return cargoJson;
    }
}
