package webControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hibernate.UserHib;
import model.Driver;
import model.User;
import model.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.LocalDateGsonSerializer;
import utils.UserGsonSerializer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

@Controller
public class UserWeb {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TransportLogistics");
    UserHib userHib = new UserHib(entityManagerFactory);

    @RequestMapping(value = "user/getDrivers", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getDrivers() {
        List<Driver> driverList = userHib.getAllDrivers();

        return driverList.toString();
    }

    @RequestMapping(value = "user/getUser/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getUser(@PathVariable(name = "id") int id) {
        User user = UserHib.getUserById(id);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(User.class, new UserGsonSerializer());
        Gson gson = builder.create();
        return gson.toJson(user);
    }

    @RequestMapping(value = "user/validateUser", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String validateUser(@RequestBody String userInfo) {
        Gson parser = new Gson();
        Properties properties = parser.fromJson(userInfo, Properties.class);
        String login = properties.getProperty("login");
        String password = properties.getProperty("password");
        User user = userHib.getUserByLoginData(login, password);
        return user != null ? String.valueOf(user.getId()) : "No such user";
    }

    @RequestMapping(value = "/user/createManager", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createManager(@RequestBody String request) {

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(User.class, new UserGsonSerializer());
        Gson parser = gson.create();
        Properties data = parser.fromJson(request, Properties.class);
        userHib.createUser(new User(data.getProperty("name"), data.getProperty("surname"), data.getProperty("phone"), data.getProperty("email"), LocalDate.parse(data.getProperty("birth")), data.getProperty("login"), data.getProperty("password"), UserRole.MANAGER, false));
        User user = userHib.getUserByLoginData(data.getProperty("login"), data.getProperty("password"));
        if (user == null) {
            return "User not created";
        }
        return parser.toJson(user);
    }

    @RequestMapping(value = "/user/createDriver", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String createDriver(@RequestBody String request) {

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(User.class, new UserGsonSerializer());
        Gson parser = gson.create();
        Properties data = parser.fromJson(request, Properties.class);
        userHib.createUser(new User(data.getProperty("name"), data.getProperty("surname"), data.getProperty("phone"), data.getProperty("email"), LocalDate.parse(data.getProperty("birth")), data.getProperty("login"), data.getProperty("password"), UserRole.DRIVER, false));
        User user = userHib.getUserByLoginData(data.getProperty("login"), data.getProperty("password"));
        if (user == null) {
            return "User not created";
        }
        return parser.toJson(user);
    }

    @RequestMapping(value = "/user/userLogin", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String login(@RequestBody String request) {

        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        GsonBuilder gson = new GsonBuilder();
        User user = null;
        user = userHib.getUserByLoginData(data.getProperty("login"), data.getProperty("password"));
        gson.registerTypeAdapter(User.class, new UserGsonSerializer()).registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());


        if (user == null) {
            return "Wrong credentials or no such user";
        }


        Gson builder = gson.create();
        return builder.toJson(user);


    }

    @RequestMapping(value = "/user/updateInfo", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateUser(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String name = data.getProperty("name");
        String surname = data.getProperty("surname");
        String email = data.getProperty("email");
        String phone = data.getProperty("phone");
        String birth = data.getProperty("birth");
        String login = data.getProperty("login");
        String password = data.getProperty("password");
        String userId = data.getProperty("id");

        User user = UserHib.getUserById(Integer.parseInt(userId));

        if (user != null) {
            user.setFirstName(name);
            user.setLastName(surname);
            user.setEmail(email);
            user.setPhoneNumber(phone);
            user.setBirthDate(LocalDate.parse(birth));
            user.setLogin(login);
            user.setPassword(password);
            try {
                userHib.updateUser(user);
            } catch (Exception e) {
                return "Error while updating";
            }
        }
        return "Update successful";

    }

    @RequestMapping(value = "/user/deleteUser/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteUser(@PathVariable(name = "id") int id) {

        userHib.deleteUser(id);

        User user = userHib.getUserById(id);
        if (user == null) {
            return "Success";
        } else {
            return "Not deleted";
        }

    }
}

