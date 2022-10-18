package cz.upce.fei.nnptp.zz.entity;

import cz.upce.fei.nnptp.zz.entity.Parameter.*;
import java.util.HashMap;

/**
 *
 * @author Roman
 */
public class Password {

    private int id;
    private String password;
    private HashMap<StandardizedParameter, Parameter> parameters;

    public Password(int id, String password) {
        this.id = id;
        this.password = password;
        this.parameters = new HashMap<>();
    }

    public Password(int id, String password, HashMap<StandardizedParameter, Parameter> parameters) {
        this.id = id;
        this.password = password;
        this.parameters = parameters;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public HashMap<StandardizedParameter, Parameter> getParameters() {
        return parameters;
    }

    boolean hasParameter(StandardizedParameter key) {
        return parameters.containsKey(key);
    }

    public Parameter getParameter(StandardizedParameter key) {
        return parameters.get(key);
    }

    @Override
    public String toString() {

        String parametersString = "";
        int parameterIndex = 0;

        for (StandardizedParameter type : parameters.keySet()) {

            String value;
            switch (type) {
                case EXPIRATION_DATETIME:
                    value = ((DateTimeParameter) parameters.get(type)).getValue().toString();
                    break;
                case TITLE:
                case WEBSITE:
                case DESCRIPTION:
                    value = ((TextParameter) parameters.get(type)).getValue();
                    break;
                default:
                    return null;
            }

            parameterIndex++;
            parametersString += "Parameter{type=" + type + ", value=" + value + "}";

            if (parameterIndex < parameters.size()) {
                parametersString += ",";
            }
        }

        return "Password{" + "id=" + id + ", password=" + password + ", parameters=" + parametersString + '}';
    }
}
