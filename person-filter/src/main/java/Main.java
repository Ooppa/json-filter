import app.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import domain.Person;
import implementations.PersonJsonDeserializer;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final int EXIT_ERROR = -1;
    private static final int EXIT_OK = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (args.length == 0) {
            catchAndExit("Critical error: Please provide the program with a filename as an argument.");
        }

        try {
            Application.run(scanner, getPeople(args[0]));
        } catch (IOException | JsonIOException e) {
            catchAndExit("Critical error: Couldn't read the JSON-file specified. It might be unavailable or malformed.");
        } catch (JsonSyntaxException e) {
            catchAndExit("Critical error: JSON file provided is not of correct format or schema.");
        }

        System.exit(EXIT_OK);
    }

    private static void catchAndExit(String message) {
        System.out.println(message);
        System.exit(EXIT_ERROR);
    }

    private static Person[] getPeople(String fileAsString) throws IOException {
        Gson gson = new GsonBuilder()
                .setLenient()
                .registerTypeAdapter(Person.class, new PersonJsonDeserializer())
                .create();
        return gson.fromJson(getFileReaderFromString(fileAsString), Person[].class);
    }

    private static FileReader getFileReaderFromString(String file) throws IOException {
        return new FileReader(file);
    }
}
