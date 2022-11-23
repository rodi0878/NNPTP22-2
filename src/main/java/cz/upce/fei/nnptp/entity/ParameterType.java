/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.upce.fei.nnptp.entity;

/**
 * Types of parameters
 * @author Roman
 */
public enum ParameterType {
    TITLE("title"),
    TEXT("text"),
    PASSWORD("password"),
    DATE("expiration-datetime"),
    WEBSITE("website"),
    DESCRIPTION("description");

    private final String value;

    private ParameterType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
