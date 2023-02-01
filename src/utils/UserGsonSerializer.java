package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.User;

import java.lang.reflect.Type;

public class UserGsonSerializer implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject userJsonObject = new JsonObject();
        userJsonObject.addProperty("id", user.getId());
        userJsonObject.addProperty("name", user.getFirstName());
        userJsonObject.addProperty("surname", user.getLastName());
        userJsonObject.addProperty("phone", user.getPhoneNumber());
        userJsonObject.addProperty("login", user.getLogin());
        userJsonObject.addProperty("password", user.getPassword());
        userJsonObject.addProperty("email", user.getEmail());
        userJsonObject.addProperty("birth", String.valueOf(user.getBirthDate()));
        return userJsonObject;
    }
}
