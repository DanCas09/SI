package service;

import jakarta.persistence.ParameterMode;

public record ParameterFunction(
        Class<?> classParameter,
        ParameterMode mode
) {
}
