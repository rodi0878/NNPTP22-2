/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.upce.fei.inptp.zz.entity;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Roman
 */
public class JSON {
    
    
    public String toJson(List<Password> passwords)  {
        StringBuilder output = new StringBuilder("[");
        for (Password password : passwords) {
            if ((output.length() > 0) && !output.toString().equals("["))
                output.append(",");
            output.append("{");
            output.append("id:").append(password.getId()).append(",");
            output.append("password:\"").append(password.getPassword()).append("\",");
            output.append("parameters: {");
            if(password.getParameters() != null){
                for(Map.Entry<String, Parameter> parameter : password.getParameters().entrySet()) {
                    output.append("\"").append(parameter.getKey()).append("\":");
                    output
                        .append("{type: \"").append(parameter.getValue().getClass().getSimpleName())
                        .append("\", value: \"").append(parameter.getValue().toString())
                        .append("\"},");
                }
            }
            output.append("}");
            output.append("}");
        }
        output.append("]");
        
        return output.toString();
    }

    public List<Password> fromJson(String json) {
        throw new RuntimeException("NYI");
    }
}
