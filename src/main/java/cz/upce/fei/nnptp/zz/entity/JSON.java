/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.upce.fei.nnptp.zz.entity;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Roman
 */
public class JSON {
    
    
    public String toJson(List<Password> passwords)  {
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
                    output.append("\t\t\t\t").append("\"type\" : \"").append(parameter.getValue().getClass().getSimpleName()).append("\",\n");
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

    public List<Password> fromJson(String json) {
        throw new RuntimeException("NYI");
    }
}
