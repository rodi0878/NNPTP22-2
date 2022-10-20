/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.upce.fei.nnptp.zz.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
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

    public enum StandardizedParameter {
        TITLE("title"),
        EXPIRATION_DATETIME("expiration-datetime"),
        WEBSITE("website"),
        DESCRIPTION("description");

        private final String label;

        private StandardizedParameter(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return this.label;
        }

        public static StandardizedParameter fromString(String text) {
            for (StandardizedParameter parameter : StandardizedParameter.values()) {
                if (parameter.label.equals(text)) {
                    return parameter;
                }
            }
            return null;
        }
    }
    
    // TODO: add support for validation rules
    
    public static class TextParameter extends Parameter {
        private String value;

        public TextParameter(String value) {
            this.value = value;
        }

        public TextParameter() {
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return getValue();
        }

        @Override
        public ParameterType getType() {
            return ParameterType.TEXT;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TextParameter that = (TextParameter) o;
            return value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static class DateTimeParameter extends Parameter {

        private LocalDateTime value;

        public DateTimeParameter() {
        }

        public DateTimeParameter(LocalDateTime value) {
            this.value = value;
        }

        public LocalDateTime getValue() {
            return value;
        }

        public void setValue(LocalDateTime value) {
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

        public PasswordParameter() {
        }

        public PasswordParameter(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
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

    public static Parameter getParameter(StandardizedParameter type, String value) {
        switch (type) {
            case TITLE:
            case WEBSITE:
            case DESCRIPTION:
                return new TextParameter(value);
            case EXPIRATION_DATETIME:
                return new DateTimeParameter(LocalDateTime.parse(type.toString()));
            default:
                return null;
        }
    }
}
