package cz.upce.fei.nnptp.zz.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 *
 * @author Roman
 */
public class JSON {

    public static final Pattern OBJECT_PATTERN = Pattern.compile("\\\"id\\\":([0-9]*),\\\"password\\\":\\\"(.+?|\\\\\")\\\",\\\"parameters\\\":\\[(.+?|\\\\\")\\]");
    public static final Pattern PARAMETER_PATTERN = Pattern.compile("\\\"type\\\":\\\"(.+?|\\\\\")\\\",\\\"value\\\":\\\"(.+?|\\\\\")\\\"");

    public static String toJson(List<Password> passwords)  {
        StringBuilder output = new StringBuilder("[").append("\n");;
        for (Password password : passwords) {
            output.append("\t").append("{").append("\n");
            output.append("\t\t").append("\"id\" : ").append(password.getId()).append(",").append("\n");
            output.append("\t\t").append("\"password\" : \"").append(password.getPassword()).append("\",").append("\n");
            output.append("\t\t").append("\"parameters\" : {").append("\n");
            if(password.getParameters() != null && password.getParameters().size() > 0){
                Map.Entry<String, Parameter> lastElementParameter = password.getParameters().entrySet().stream().reduce((one, two) -> two).get();
                for(Map.Entry<String, Parameter> parameter : password.getParameters().entrySet()) {
                    output.append("\t\t\t").append("\"").append(parameter.getKey()).append("\" : {").append("\n");
                    output.append("\t\t\t\t").append("\"type\" : \"").append(parameter.getValue().getType().toString()).append("\",\n");
                    output.append("\t\t\t\t").append("\"value\" : \"").append(parameter.getValue().toString()).append("\"").append("\n").append("\t\t\t");
                        if(parameter.equals(lastElementParameter))
                        {
                            output.append("}");
                        } else {
                            output.append("},");
                        }
                        output.append("\n");
                }
            }
            output.append("\t\t").append("}").append("\n");
            output.append("\t").append("}");
            if(!password.equals(passwords.get(passwords.size()-1)))
                output.append(",");
            output.append("\n");
        }
        output.append("]");
        
        return output.toString();
    }

    public static List<Password> fromJson(String json) {
        List<Password> passwords = new LinkedList<>();
        JSONArray array = new JSONArray(json);  
        
        
        for(int i = 0; i < array.length(); i++ ){
            HashMap<String, Parameter> parameters = new HashMap<>();
            
            JSONObject PwdObject = array.getJSONObject(i);
            JSONObject params = (JSONObject) PwdObject.get("parameters");
            String type = params.keys().next();
            JSONObject title = (JSONObject) params.get(type);
            String value = (String) title.get("value");
            
            int id = (int) PwdObject.get("id");
            String password = (String) PwdObject.get("password");
            Parameter parameter = Parameter.getParameter(type, value);

            parameters.put(type, parameter);
            passwords.add(new Password(id, password, parameters));
        }
        
        return passwords;
    
    }

    public static List<Password> fromJsonRegex(String json) {

        List<Password> passwords = new LinkedList<>();
        Matcher objectMatcher = OBJECT_PATTERN.matcher(json);

        while(objectMatcher.find() && objectMatcher.groupCount() == 3) {

            int id = Integer.parseInt(objectMatcher.group(1));
            String password = objectMatcher.group(2);
            HashMap<String, Parameter> parameters = new HashMap<>();
            Matcher parameterMatcher = PARAMETER_PATTERN.matcher(objectMatcher.group(3));

            while(parameterMatcher.find() && parameterMatcher.groupCount() == 2) {
                String type = parameterMatcher.group(1);
                Parameter parameter = Parameter.getParameter(type, parameterMatcher.group(2));
                parameters.put(type, parameter);
            }

            passwords.add(new Password(id, password, parameters));
        }
        return passwords;
    }
}
