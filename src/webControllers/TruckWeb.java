package webControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import hibernate.TruckHib;
import model.Truck;
import model.VehicleStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.LocalDateGsonSerializer;
import utils.TruckGsonSerializer;
import utils.TruckListGsonSerializer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import static model.VehicleStatus.FREE;

@Controller
public class TruckWeb {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TransportLogistics");
    TruckHib truckHib = new TruckHib(entityManagerFactory);

    @RequestMapping(value = "/truck/getAll", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllTrucks() {

        List<Truck> allTrucks = truckHib.getFreeTrucks();

        GsonBuilder gson = new GsonBuilder();
        Type TruckList = new TypeToken<List<Truck>>() {
        }.getType();
        gson.registerTypeAdapter(Truck.class, new TruckGsonSerializer()).registerTypeAdapter(TruckList, new TruckListGsonSerializer());
        Gson parser = gson.create();
        return parser.toJson(allTrucks);
    }

    @RequestMapping(value = "truck/getTruck/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getTruck(@PathVariable(name = "id") int id) {
        Truck truck = truckHib.getTruckById(id);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Truck.class, new TruckGsonSerializer());
        Gson gson = builder.create();
        return gson.toJson(truck);
    }

    @RequestMapping(value = "/truck/createTruck", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createTruck(@RequestBody String request) {

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(Truck.class, new TruckGsonSerializer());
        Gson parser = gson.create();
        Properties data = parser.fromJson(request, Properties.class);
        truckHib.createTruck(new Truck(LocalDate.parse(data.getProperty("techInspectionUntil")), Integer.parseInt(data.getProperty("euroStandard")), Integer.parseInt(data.getProperty("mileage")), Integer.parseInt(data.getProperty("fuelTank")), data.getProperty("color"), Integer.parseInt(data.getProperty("horsePower")), Integer.parseInt(data.getProperty("power")), data.getProperty("licensePlate"), data.getProperty("vin"), FREE));
        Truck truck = truckHib.getByLicensePlate(data.getProperty("licensePlate"));
        if (truck == null) {
            return "Truck not created";
        }
        return parser.toJson(truck);
    }

    @RequestMapping(value = "/truck/updateInfo", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateTruck(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        LocalDate tech = LocalDate.parse(data.getProperty("techInspectionUntil"));
        int euroStandard = Integer.parseInt(data.getProperty("euroStandard"));
        int mileage = Integer.parseInt(data.getProperty("mileage"));
        int fuelTankCap = Integer.parseInt(data.getProperty("fuelTank"));
        String color = data.getProperty("color");
        int horsePower = Integer.parseInt(data.getProperty("horsePower"));
        int powerKw = Integer.parseInt(data.getProperty("power"));
        String licensePlate = data.getProperty("licensePlate");
        String vin = data.getProperty("vin");
        String assignedTo = data.getProperty("assignedTo");
        VehicleStatus vehicleStatus = VehicleStatus.valueOf(data.getProperty("currentStatus"));

        Truck truck = truckHib.getByLicensePlate(licensePlate);

        if (truck != null) {
            truck.setColor(color);
            truck.setTechnicalInspectionUntil(tech);
            truck.setVin(vin);
            truck.setKwPower(powerKw);
            truck.setHorsePower(horsePower);
            truck.setFuelTankCapacity(fuelTankCap);
            truck.setAssignedTo(assignedTo);
            truck.setMileage(mileage);
            truck.setCurrentStatus(vehicleStatus);
            truck.setEuroStandart(euroStandard);
            truck.setLicensePlate(licensePlate);
            try {
                truckHib.editTruck(truck);
            } catch (Exception e) {
                return "Error while updating";
            }
        }
        return "Update successful";

    }

    @RequestMapping(value = "/truck/deleteTruck/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteTruck(@PathVariable(name = "id") int id) {

        truckHib.deleteTruck(id);

        Truck truck = truckHib.getTruckById(id);
        if (truck == null) {
            return "Success";
        } else {
            return "Not deleted";
        }

    }
}
