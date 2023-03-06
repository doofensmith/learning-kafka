package basecomponent.query;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
public enum FieldType {

    BOOLEAN {
        @Override
        public Object parse(String value) {
            return Boolean.valueOf(value);
        }
    },

    CHAR {
        @Override
        public Object parse(String value) {
            return value.charAt(0);
        }
    },

    STRING {
        @Override
        public Object parse(String value) {
            return String.valueOf(value);
        }
    },

    INTEGER {
        @Override
        public Object parse(String value) {
            return Integer.valueOf(value);
        }
    },

    LONG {
        @Override
        public Object parse(String value) {
            return Long.valueOf(value);
        }
    },

    DOUBLE {
        @Override
        public Object parse(String value) {
            return Double.valueOf(value);
        }
    },

    DATE {
        @Override
        public Object parse(String value) {
            Object date = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                date = LocalDateTime.parse(value, formatter);
            } catch (Exception e) {
                log.debug("Failed parsing field type DATE {}.", e.getMessage());
            }
            return null;
        }
    };

    public abstract Object parse(String value);

}
