package implementations;

import com.google.gson.*;
import domain.Gender;
import domain.Person;

import java.lang.reflect.Type;

public class PersonJsonDeserializer implements JsonDeserializer<Person> {

    private int iteration = 0;

    private static boolean checkIfStringClass(JsonObject jsonObject, String field) {
        if (jsonObject.getAsJsonPrimitive(field) == null) {
            return false;
        }
        return jsonObject.getAsJsonPrimitive(field).isString();
    }

    private static int checkAgeIsValid(int ageToCheck) {
        if (ageToCheck <= 0) {
            throw new IllegalArgumentException("Age should be at least 1.");
        }

        return ageToCheck;
    }

    @Override
    public Person deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Person person = new Person();
        JsonObject jsonObject = json.getAsJsonObject();
        iteration++;

        try {
            checkIfAllFieldsPresent(jsonObject);

            if (checkIfStringClass(jsonObject, "firstName")) {
                person.setFirstName(jsonObject.get("firstName").getAsString());
            } else {
                throw new IllegalArgumentException("First name was of a wrong type.");
            }

            if (checkIfStringClass(jsonObject, "lastName")) {
                person.setLastName(jsonObject.get("lastName").getAsString());
            } else {
                throw new IllegalArgumentException("First name was of a wrong type.");
            }

            person.setAge(checkAgeIsValid(jsonObject.get("age").getAsInt()));
            person.setGender(Gender.getFromString(jsonObject.get("gender").getAsString()));

            return person;
        } catch (EnumConstantNotPresentException |
                 IllegalArgumentException |
                 JsonParseException |
                 ClassCastException |
                 IllegalStateException e
        ) {
            System.out.printf("%d, Warning: [%s] %s. The object will be skipped.%n", iteration, e.getClass().getSimpleName(), e.getMessage());
        }

        return null;
    }

    private void checkIfAllFieldsPresent(JsonObject jsonObject) {
        if (!jsonObject.has("firstName") ||
                !jsonObject.has("lastName") ||
                !jsonObject.has("age") ||
                !jsonObject.has("gender")
        ) {
            throw new JsonParseException("All fields weren't present for the Person object.");
        }
    }
}
