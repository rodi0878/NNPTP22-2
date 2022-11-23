package cz.upce.fei.nnptp.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Building passwords with parameters
 * @author Roman
 */
public class Password {

    private int id;
    private String password;
    private HashMap<ParameterType, Parameter> parameters;

    /**
     * Creates a password with empty map of parameters
     * @param id
     * @param password
     */
    public Password(int id, String password) {
        this.id = id;
        this.password = password;
        this.parameters = new HashMap<>();
    }

    /**
     * Creates a password with a map of parameters
     * @param id
     * @param password
     * @param parameters
     */
    public Password(int id, String password, HashMap<ParameterType, Parameter> parameters) {
        this.id = id;
        this.password = password;
        this.parameters = parameters;
    }

    /**
     * Returns password ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns all parameters
     * @return parameters
     */
    public HashMap<ParameterType, Parameter> getParameters() {
        return parameters;
    }

    boolean hasParameter(String parameterName) {
        return parameters.containsKey(parameterName);
    }

    public Parameter getParameter(String parameterName) {
        return parameters.get(parameterName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return id == password1.id && password.equals(password1.password) && parameters.equals(password1.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, parameters.hashCode());
    }

    /**
     * Converting password to a string
     * @return string
     */
    @Override
    public String toString() {
        String parametersString = "";
        int parameterIndex = 0;
        for (Map.Entry<ParameterType, Parameter> set : parameters.entrySet()) {
            parameterIndex++;
            parametersString += "Parameter{key=" + set.getKey() + ", value=" + set.getValue() + ", type=" + set.getValue().getType() + "}";
            if (parameterIndex < parameters.size()) {
                parametersString += ",";
            }
        }
        return "Password{" + "id=" + id + ", password=" + password + ", parameters=" + parametersString + '}';
    }
}
