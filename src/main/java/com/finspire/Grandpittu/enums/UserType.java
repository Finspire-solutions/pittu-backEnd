package com.finspire.Grandpittu.enums;

import com.finspire.Grandpittu.exception.ServiceException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public enum UserType {
    ADMIN("admin"),
    USER("user");

    private final String mappedValue;

    UserType(String mappedValue) {
        this.mappedValue = mappedValue;
    }

    public static UserType fromMappedValue(String mappedValue) {
        if (mappedValue == null || mappedValue.isBlank()) {
            return null;
        }
        for (UserType type : UserType.values()) {
            if (type.mappedValue.equalsIgnoreCase(mappedValue)) {
                return type;
            }
        }
        throw new ServiceException("Unsupported type: " + mappedValue, "Bad request", HttpStatus.BAD_REQUEST);
    }


    public String getMappedValue() {
        return mappedValue;
    }

    public static List<String> getAll() {
        List<String> list = new ArrayList<>();
        for (UserType type : UserType.values()) {
            list.add(type.mappedValue);
        }
        return list;
    }
}
