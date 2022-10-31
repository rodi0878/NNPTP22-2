package cz.upce.fei.nnptp.zz.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Roman
 */
public class Password {

    private int id;
    private String password;
    private HashMap<String, Parameter> parameters;

    public Password(int id, String password) {
        this.id = id;
        this.password = password;
        this.parameters = new HashMap<>();
    }

    public Password(int id, String password, HashMap<String, Parameter> parameters) {
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

    public HashMap<String, Parameter> getParameters() {
        return parameters;
    }

    boolean hasParameter(String TITLE) {
        return parameters.containsKey(TITLE);
    }

    public Parameter getParameter(String t) {
        return parameters.get(t);
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

    @Override
    public String toString() {
        String parametersString = "";
        int parameterIndex = 0;
        for (Map.Entry<String, Parameter> set : parameters.entrySet()) {
            parameterIndex++;
            parametersString += "Parameter{key=" + set.getKey() + ", value=" + set.getValue() + ", type=" + set.getValue().getType() + "}";
            if (parameterIndex < parameters.size()) {
                parametersString += ",";
            }
        }
        return "Password{" + "id=" + id + ", password=" + password + ", parameters=" + parametersString + '}';
    }
}
