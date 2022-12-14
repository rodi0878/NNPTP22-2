package cz.upce.fei.nnptp.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.upce.fei.nnptp.exception.ValidationException;

/**
 *
 * @author Roman
 */
public class JSON {

    /**
     * Builds a string with password and its parameters so it can be written
     * into JSON
     *
     * @param passwords
     * @return string
     */
    public static String toJson(List<Password> passwords) {
        StringBuilder json = new StringBuilder("[");

        for (Password password : passwords) {
            if (!json.toString().equals("[")) {
                json.append(",");
            }

            json.append("{\"id\": ");
            json.append(password.getId());
            json.append(",\"password\": \"").append(password.getPassword());
            json.append("\",\"parameters\": {");

            if (password.getParameters() != null) {
                for (Map.Entry<String, Parameter> parameter : password.getParameters().entrySet()) {
                    json.append(parameter.getKey());
                    json.append(": ");
                    json.append(parameter.getValue());

                }
            }
            json.append("}}");
        }
        json.append("]");
        return json.toString();
    }

    /**
     * Reads passwords from a JSON string
     *
     * @param json
     * @return Returns a list of passwords
     */
    public static List<Password> fromJson(String json) {
        List<Password> passwords = new LinkedList<>();
        JSONArray array = new JSONArray(json);

        for (int i = 0; i < array.length(); i++) {
            HashMap<String, Parameter> parameters = new HashMap<>();
            JSONObject pwdData = array.getJSONObject(i);
            JSONObject paramData;
            int id = (int) pwdData.get("id");
            String password = (String) pwdData.get("password");
            String value = "Default";
            String type = "";
            if (pwdData.get("parameters") instanceof JSONArray) {
                for (int j = 0; j < array.length(); j++) {
                    JSONArray paramArray = (JSONArray) pwdData.get("parameters");
                    paramData = parseArrayIntoList(paramArray, j);
                    paramDataStructureHasValidStructure(paramData);

                    value = (String) paramData.get("value");
                    type = (String) paramData.get("type");
                    Parameter parameter = Parameter.getParameter(type, value);
                    parameters.put(type, parameter);
                }
            }
            if (pwdData.get("parameters") instanceof JSONObject) {
                parameters = new HashMap<>();
                paramData = (JSONObject) pwdData.get("parameters");
                paramDataStructureHasValidStructure(paramData);

                value = (String) paramData.get("value");
                type = (String) paramData.get("type");
                Parameter parameter = Parameter.getParameter(type, value);
                parameters.put(type, parameter);
            }

            passwords.add(new Password(id, password, parameters));
        }

        return passwords;
    }

    /**
     * Throw exception when data structure of parameter is not valid.
     *
     *
     */
    private static void paramDataStructureHasValidStructure(JSONObject paramData) {
        if (!paramData.has("value")) {
            throw new ValidationException("Key/pair of value in parameter object does not exists");
        }
        if (!paramData.has("type")) {
            throw new ValidationException("Key/pair of type in parameter object does not exists");
        }
    }

    private static JSONObject parseArrayIntoList(JSONArray array, Integer i) {
        if (!(array.getJSONObject(i) instanceof JSONObject)) {
            return new JSONObject();
        }

        return array.getJSONObject(i);
    }
}
