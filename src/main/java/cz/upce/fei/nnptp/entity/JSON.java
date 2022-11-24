package cz.upce.fei.nnptp.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Roman
 */
public class JSON {

    /**
     * Pattern of the password to be written into JSON
     */
    public static final Pattern OBJECT_PATTERN = Pattern.compile("\\\"id\\\":([0-9]*),\\\"password\\\":\\\"(.+?|\\\\\")\\\",\\\"parameters\\\":\\[(.+?|\\\\\")\\]");

    /**
     * Patterns of the password parameters to be writton into JSON
     */
    public static final Pattern PARAMETER_PATTERN = Pattern.compile("\\\"type\\\":\\\"(.+?|\\\\\")\\\",\\\"value\\\":\\\"(.+?|\\\\\")\\\"");

    /**
     * Builds a string with password and its parameters so it can be written into JSON
     * @param passwords
     * @return string
     */
    public static String toJson(List<Password> passwords)  {
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
     * @param json
     * @return Returns a list of passwords
     */
    public static List<Password> fromJson(String json) {
        List<Password> passwords = new LinkedList<>();
        JSONArray array = new JSONArray(json);  
        
        for(int i = 0; i < array.length(); i++ ){
            
            HashMap<String, Parameter> parameters = new HashMap<>();
            JSONObject PwdObject = array.getJSONObject(i);
            JSONObject pwdObjectList;
            String value = "";
            String type = "";
            String password = "";
            int id = 0;
            if(PwdObject.get("parameters") instanceof JSONArray){
                for(int j = 0; j < array.length(); j++ ){
                    JSONArray paramArray = (JSONArray) PwdObject.get("parameters");
                    pwdObjectList = parseArrayIntoList(paramArray, j);
                    value = (String) pwdObjectList.get("value");
                    type = (String) pwdObjectList.get("type");
                    id = (int) PwdObject.get("id");
                    password = (String) PwdObject.get("password");
                    Parameter parameter = Parameter.getParameter(type, value);
                    parameters.put(type, parameter);
                }
            }
            if(PwdObject.get("parameters") instanceof JSONObject){
                parameters = new HashMap<>();
                pwdObjectList = (JSONObject) PwdObject.get("parameters");
                type = pwdObjectList.keys().next();
                JSONObject title = (JSONObject) pwdObjectList.get(type);
                value = (String) title.get("value");
                id = (int) PwdObject.get("id");
                password = (String) PwdObject.get("password");
                Parameter parameter = Parameter.getParameter(type, value);
                parameters.put(type, parameter);
            }

            passwords.add(new Password(id, password, parameters));
            

        }
        
        return passwords;
    
    }


    private static JSONObject parseArrayIntoList(JSONArray array, Integer i) {
        if(!(array.getJSONObject(i) instanceof JSONObject)){
            return new JSONObject();
        }
        
        return array.getJSONObject(i);
    }
}
