package cz.upce.fei.nnptp.zz.entity;

import java.time.LocalDateTime;

/**
 *
 * @author Roman
 */
public class Parameter {

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
