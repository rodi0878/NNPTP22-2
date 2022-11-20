package cz.upce.fei.nnptp.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;

/**
 *
 * @author Roman
 */
public class JSON {

    public static final Pattern OBJECT_PATTERN = Pattern.compile("\\\"id\\\":([0-9]*),\\\"password\\\":\\\"(.+?|\\\\\")\\\",\\\"parameters\\\":\\[(.+?|\\\\\")\\]");
    public static final Pattern PARAMETER_PATTERN = Pattern.compile("\\\"type\\\":\\\"(.+?|\\\\\")\\\",\\\"value\\\":\\\"(.+?|\\\\\")\\\"");

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

    public static List<Password> fromJson(String json) {

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
