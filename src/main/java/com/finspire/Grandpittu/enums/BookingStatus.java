package com.finspire.Grandpittu.enums;

import com.finspire.Grandpittu.exception.ServiceException;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BookingStatus {
    ACCEPTED("Accepted"),
    REJECTED("Rejected"),
    PENDING("Pending"),
    COMPLETED("Complete"),
    INCOMPLETED("Inomplete"),
    CANCELED("Canceled");

    private final String mappedValue;

    BookingStatus(String mappedValue) {
        this.mappedValue = mappedValue;
    }

    @JsonValue
    public String getMappedValue() {
        return mappedValue;
    }

    @JsonCreator
    public static BookingStatus fromMappedValue(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        // Accept both the enum name (ACCEPTED) and mapped value (Accepted)
        for (BookingStatus type : BookingStatus.values()) {
            if (type.name().equalsIgnoreCase(value) || type.mappedValue.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new ServiceException("Unsupported type: " + value, "Bad request", HttpStatus.BAD_REQUEST);
    }
}

