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
import java.util.List;
import java.util.Objects;

/**
 * Parameters for saved passwords
 * Including Title, Expiration date, website, description
 * @author Roman
 */
public abstract class Parameter {

    @Override
    public abstract String toString();
    @Override
    public abstract boolean equals(Object o);
    @Override
    public abstract int hashCode();
    public abstract ParameterType getType();


    public static class StandardizedParameters {
        public static final String  TITLE = "title" ;
        public static final String  EXPIRATION_DATETIME  = "expiration-datetime" ;
        public  static  final String  WEBSITE = "website" ;
        public static final String  DESCRIPTION = "description" ;
        
    }
    
    // TODO: add support for validation rules
    
    public static class TextParameter extends Parameter {
        private String value;

        /**
         * Sets a text parameter for password
         * @param value
         */
        private final Validator<String> validator = new Validator<>(List.of(new NonNullValidation(), new StringNotEmptyValidation()));

        public TextParameter(String value) {
            if (!validator.valid(value)) {
                throw new ValidationException("Value for TextParameter is not valid.");
            }
            this.value = value;
        }

        public TextParameter() {
        }

        /**
         * Returns value
         * @return
         */
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            if (!validator.valid(value)) {
                throw new ValidationException("New value for TextParameter is not valid.");
            }
            this.value = value;
        }

        @Override
        public String toString() {
            return getValue();
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

        /**
         * Hashing
         * @return
         */
        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static class DateTimeParameter extends Parameter {

        private LocalDateTime value;

        private final Validator<LocalDateTime> validator = new Validator<>(List.of(new NonNullValidation()));

        public DateTimeParameter() {
        }

        public DateTimeParameter(LocalDateTime value) {
            if (!validator.valid(value)) {
                throw new ValidationException("Value for DateTimeParameter is not valid.");
            }
            this.value = value;
        }

        public LocalDateTime getValue() {
            return value;
        }

        public void setValue(LocalDateTime value) {
            if (!validator.valid(value)) {
                throw new ValidationException("New value for DateTimeParameter is not valid.");
            }
            this.value = value;
        }


        @Override
        public String toString() {
            return getValue().toString();
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

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static class PasswordParameter extends Parameter {

        private String password;

        private final Validator<String> validator = new Validator<>(List.of(new NonNullValidation(), new StringNotEmptyValidation()));

        public PasswordParameter() {
        }

        public PasswordParameter(String password) {
            if (!validator.valid(password)) {
                throw new ValidationException("Value for PasswordParameter is not valid.");
            }
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            if (!validator.valid(password)) {
                throw new ValidationException("New value for PasswordParameter is not valid.");
            }
            this.password = password;
        }

        @Override
        public String toString() {
            return getPassword();
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
            return password.equals(that.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(password);
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
