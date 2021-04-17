package com.example.demo.utils;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidationUtil {
    <T> boolean isValid(T entity);
    // Check what exactly is not valid
    // The method collects all the errors in a set
    <T> Set<ConstraintViolation<T>> getViolations(T entity);
}
