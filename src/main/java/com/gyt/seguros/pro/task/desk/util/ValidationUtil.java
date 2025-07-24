package com.gyt.seguros.pro.task.desk.util;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;


public class ValidationUtil {

    private static final String DESCRIPTION_ATTRIBUTE = "El atributo '";
    private ValidationUtil(){}

    public static void validateNotNullOrEmpty(Object object, String... attributes) {
        if (object == null) {
            throw new IllegalArgumentException("El objeto no puede ser nulo para la validación.");
        }

        for (String attribute : attributes) {
            try {
                Field field = getField(object.getClass(), attribute);
                field.setAccessible(true);
                Object value = field.get(object);

                if (value == null) {
                    throw new IllegalArgumentException(DESCRIPTION_ATTRIBUTE + attribute + "' no puede ser nulo.");
                }

                validateValueIsNotEmpty(value, attribute);

            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException(DESCRIPTION_ATTRIBUTE + attribute + "' no existe en la clase " + object.getClass().getName(), e);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("No se pudo acceder al atributo '" + attribute + "'.", e);
            }
        }
    }

    private static void validateValueIsNotEmpty(Object value, String attributeName) {
        if (value instanceof String strValue && strValue.trim().isEmpty()) {
            throw new IllegalArgumentException(DESCRIPTION_ATTRIBUTE + attributeName + "' no puede estar vacío.");
        }
        else if (value instanceof Collection<?> collectionValue && collectionValue.isEmpty()) {
            throw new IllegalArgumentException(DESCRIPTION_ATTRIBUTE + attributeName + "' (colección) no puede estar vacío.");
        }
        else if (value instanceof Map<?, ?> mapValue && mapValue.isEmpty()) {
            throw new IllegalArgumentException(DESCRIPTION_ATTRIBUTE + attributeName + "' (mapa) no puede estar vacío.");
        }
    }

    private static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            try {
                return currentClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }
        throw new NoSuchFieldException("El campo " + fieldName + " no se encontró ");
    }

    public static void validateStringMaxLength(String value, int maxLength, String attributeName) {
        if (value != null && value.trim().length() > maxLength) {
            throw new IllegalArgumentException(DESCRIPTION_ATTRIBUTE + attributeName + "' no puede exceder los " + maxLength + " caracteres.");
        }
    }

    public static void validateDateNotBeforeCurrent(LocalDate date, String attributeName) {
        if (date != null && date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha '" + attributeName + "' no puede ser anterior a la fecha actual.");
        }
    }

    public static void validateStartDateBeforeEndDate(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
    }

}
