/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.upce.fei.nnptp.entity;

import cz.upce.fei.nnptp.exception.ValidationException;
import cz.upce.fei.nnptp.validation.NonNullValidation;
import cz.upce.fei.nnptp.validation.StringNotEmptyValidation;
import cz.upce.fei.nnptp.validation.Validator;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Parameters for saved passwords
 * Including Title, Expiration date, website, description
 * @author Roman
 */
public abstract class Parameter<T> {

    protected T value;
    protected Validator<T> validator = new Validator<>();
    
    public T getValue() {
        return value;
    }
    
    public void setValue(T value) {
        this.value = value;
    }
    
    public abstract ParameterType getType();
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
    
    @Override
    public abstract boolean equals(Object o);


    public static class StandardizedParameters {
        public static final String  TITLE = "title" ;
        public static final String  EXPIRATION_DATETIME  = "expiration-datetime" ;
        public  static  final String  WEBSITE = "website" ;
        public static final String  DESCRIPTION = "description" ;
        
    }
    
    public static class TextParameter extends Parameter<String> {

        public TextParameter() {
            validator.addValidation(new NonNullValidation());
            validator.addValidation(new StringNotEmptyValidation());
        }

        /**
         * Sets a text parameter for password
         * @param value
         */
        public TextParameter(String value) {
            this();
            if (!validator.valid(value)) {
                throw new ValidationException("Value for TextParameter is not valid.");
            }
            this.value = value;
        }

        @Override
        public void setValue(String value) {
            if (!validator.valid(value)) {
                throw new ValidationException("New value for TextParameter is not valid.");
            }
            this.value = value;
        }

        /**
         * Returns a type of the parameter
         * @return
         */
        @Override
        public ParameterType getType() {
            return ParameterType.TEXT;
        }

        /**
         * Checks if the parameters equal
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TextParameter that = (TextParameter) o;
            return value.equals(that.value);
        }
    }

    public static class DateTimeParameter extends Parameter<LocalDateTime> {

        public DateTimeParameter() {
            validator.addValidation(new NonNullValidation());
        }

        public DateTimeParameter(LocalDateTime value) {
            this();
            if (!validator.valid(value)) {
                throw new ValidationException("Value for DateTimeParameter is not valid.");
            }
            this.value = value;
        }
      
        @Override
        public void setValue(LocalDateTime localDateTime) {
            if (!validator.valid(localDateTime)) {
                throw new ValidationException("New value for DateTimeParameter is not valid.");
            }
            this.value = localDateTime;
        }

        @Override
        public ParameterType getType() {
            return ParameterType.DATE;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DateTimeParameter that = (DateTimeParameter) o;
            return value.equals(that.value);
        }
    }

    public static class PasswordParameter extends Parameter<String> {

        public PasswordParameter() {
            validator.validations.add(new NonNullValidation());
            validator.validations.add(new StringNotEmptyValidation());
        }

        public PasswordParameter(String password) {
            this();
            if (!validator.valid(password)) {
                throw new ValidationException("Value for PasswordParameter is not valid.");
            }
            this.value = password;
        }

        @Override
        public void setValue(String password) {
            if (!validator.valid(password)) {
                throw new ValidationException("New value for PasswordParameter is not valid.");
            }
            this.value = password;
        }

        @Override
        public ParameterType getType() {
            return ParameterType.PASSWORD;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PasswordParameter that = (PasswordParameter) o;
            return value.equals(that.value);
        }
    }

    public static Parameter getParameter(String type, String value) {
        switch (type) {
            case StandardizedParameters.TITLE:
            case StandardizedParameters.WEBSITE:
            case StandardizedParameters.DESCRIPTION:
                return new TextParameter(value);
            case StandardizedParameters.EXPIRATION_DATETIME:
                return new DateTimeParameter(LocalDateTime.parse(type));
            default:
                return null;
        }
    }
}
