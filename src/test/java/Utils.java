import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {

    private static final String JSON_FILE_PATH = "./src/test/resources/data.json"; // File path for your JSON file

    // Method to set environment variable in config.properties
    public static void setEnvVar(String key, String value) throws ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key, value);
        config.save();
    }

    // Method to generate a random ID within a range
    public static int generateRandomId(int min, int max) {
        double randId = Math.random() * (max - min) + min;
        return (int) randId;
    }

    // Method to read JSON data from file using json-simple
    public static JSONObject readJsonFile() {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = new JSONObject();

        try (FileReader reader = new FileReader(JSON_FILE_PATH)) {
            Object obj = jsonParser.parse(reader);
            jsonObject = (JSONObject) obj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    // Method to write JSON data to file using json-simple
    public static void writeJsonFile(JSONObject jsonObject) {
        try (FileWriter file = new FileWriter(JSON_FILE_PATH)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to save specific phone number details in JSON
    public static void savePhoneNumber(String key, String phoneNumber) {
        JSONObject jsonObject = readJsonFile(); // Read current JSON data
        jsonObject.put(key, phoneNumber);       // Add or update the key-value pair
        writeJsonFile(jsonObject);              // Write updated data back to file
    }
}
