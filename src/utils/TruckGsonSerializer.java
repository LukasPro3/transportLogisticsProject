package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Truck;

import java.lang.reflect.Type;

public class TruckGsonSerializer implements JsonSerializer<Truck> {

    @Override
    public JsonElement serialize(Truck truck, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject truckJson = new JsonObject();
        truckJson.addProperty("id", truck.getId());
        truckJson.addProperty("vin", truck.getVin());
        truckJson.addProperty("licensePlate", truck.getLicensePlate());
        truckJson.addProperty("techInspectionUntil", truck.getTechnicalInspectionUntil().toString());
        truckJson.addProperty("color", truck.getColor());
        truckJson.addProperty("euroStandard", truck.getEuroStandard());
        truckJson.addProperty("fuelTank", truck.getFuelTankCapacity());
        truckJson.addProperty("horsePower", truck.getHorsePower());
        truckJson.addProperty("power", truck.getKwPower());
        truckJson.addProperty("mileage", truck.getMileage());
        truckJson.addProperty("assignedTo", String.valueOf(truck.getAssignedToId()));
        truckJson.addProperty("currentStatus", String.valueOf(truck.getCurrentStatus()));

        return truckJson;
    }
}
