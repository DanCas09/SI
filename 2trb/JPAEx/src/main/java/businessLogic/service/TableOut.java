package businessLogic.service;

import jakarta.persistence.ParameterMode;

public record TableOut(
        Class<?> classParameter,
        ParameterMode mode,
        Class<?> classResult
) {
}
