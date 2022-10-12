/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.upce.fei.nnptp.zz.entity;

import java.util.HashMap;

/**
 *
 * @author Roman
 */
public class Password {

    private final int id;
    private final String password;
    private HashMap<String, Parameter> parameters;

    public Password(int id, String password) {
        this.id = id;
        this.password = password;
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

    boolean hasParameter(String key) {
        return parameters.containsKey(key);
    }
    
    public Parameter getParameter(String key) {
        return parameters.get(key);
    }
}
