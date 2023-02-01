package webControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import hibernate.CargoHib;
import hibernate.UserHib;
import model.Cargo;
import model.CargoStatus;
import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.CargoGsonSerializer;
import utils.CargoListGsonSerializer;
import utils.LocalDateGsonSerializer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

@Controller
public class CargoWeb {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TransportLogistics");
    CargoHib cargoHib = new CargoHib(entityManagerFactory);

    @RequestMapping(value = "/cargo/getAll", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllCargo() {

        List<Cargo> allCargo = cargoHib.getAllCargo();

        GsonBuilder gson = new GsonBuilder();
        Type cargoList = new TypeToken<List<Cargo>>() {
        }.getType();
        gson.registerTypeAdapter(Cargo.class, new CargoGsonSerializer()).registerTypeAdapter(cargoList, new CargoListGsonSerializer());
        Gson parser = gson.create();
        return parser.toJson(allCargo);
    }

    @RequestMapping(value = "cargo/getCargo/{cargoId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCargo(@PathVariable(name = "cargoId") int cargoId) {
        Cargo cargo = cargoHib.getCargoById(cargoId);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Cargo.class, new CargoGsonSerializer());
        Gson gson = builder.create();
        return gson.toJson(cargo);
    }

    @RequestMapping(value = "/cargo/createCargo", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createCargo(@RequestBody String request) {

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(Cargo.class, new CargoGsonSerializer());
        Gson parser = gson.create();
        Properties data = parser.fromJson(request, Properties.class);
        Cargo cargo = new Cargo(Double.parseDouble(data.getProperty("price")), Double.parseDouble(data.getProperty("amount")), Double.parseDouble(data.getProperty("weight")), Double.parseDouble(data.getProperty("height")), Double.parseDouble(data.getProperty("width")), Double.parseDouble(data.getProperty("length")), data.getProperty("collectionPoint"), data.getProperty("deliveryPoint"), data.getProperty("otherInfo"), data.getProperty("crm"), CargoStatus.valueOf(data.getProperty("currentStatus")), UserHib.getUserById(Integer.parseInt(data.getProperty("responsible"))), UserHib.getUserById(Integer.parseInt(data.getProperty("assignedTo"))));
        cargoHib.createCargo(cargo);
        Cargo created = cargoHib.getCargoById(cargo.getId());
        if (created == null) {
            return "Cargo not created";
        }
        return parser.toJson(created);
    }

    @RequestMapping(value = "/cargo/updateCargo", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateCargo(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        int cargoId = Integer.parseInt(data.getProperty("cargoId"));
        Double price = Double.parseDouble(data.getProperty("price"));
        Double amount = Double.parseDouble(data.getProperty("amount"));
        Double weight = Double.parseDouble(data.getProperty("weight"));
        Double height = Double.parseDouble(data.getProperty("height"));
        Double width = Double.parseDouble(data.getProperty("width"));
        Double length = Double.parseDouble(data.getProperty("length"));
        String collectionPoint = data.getProperty("collectionPoint");
        String deliveryPoint = data.getProperty("deliveryPoint");
        String otherInfo = data.getProperty("otherInfo");
        String crm = data.getProperty("crm");
        CargoStatus currentStatus = CargoStatus.valueOf(data.getProperty("currentStatus"));
        User responsible = UserHib.getUserById(Integer.parseInt(data.getProperty("responsible")));
        User assignedTo = UserHib.getUserById(Integer.parseInt(data.getProperty("assignedTo")));

        Cargo cargo = cargoHib.getCargoById(cargoId);

        if (cargo != null) {
            cargo.setPrice(price);
            cargo.setAmount(amount);
            cargo.setWeight(weight);
            cargo.setHeight(height);
            cargo.setWidth(width);
            cargo.setLength(length);
            cargo.setCollectionPoint(collectionPoint);
            cargo.setDeliveryPoint(deliveryPoint);
            cargo.setOtherInfo(otherInfo);
            cargo.setCrm(crm);
            cargo.setCurrentStatus(currentStatus);
            cargo.setResponsible(responsible);
            cargo.setAssignedTo(assignedTo);

            try {
                cargoHib.updateCargo(cargo);
            } catch (Exception e) {
                return "Error while updating";
            }
        }
        return "Update successful";

    }


    @RequestMapping(value = "/cargo/deleteCargo/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteTrip(@PathVariable(name = "id") int id) {

        cargoHib.deleteCargo(id);

        Cargo cargo = cargoHib.getCargoById(id);
        if (cargo == null) {
            return "Success";
        } else {
            return "Not deleted";
        }

    }

}
