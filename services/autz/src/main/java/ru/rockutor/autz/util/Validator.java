package ru.rockutor.autz.util;

import ru.rockutor.auth.UserRole;
import ru.rockutor.autz.exception.InvalidUserDataException;

import java.util.Optional;

public class Validator {
    private static final String INVALID_PASSWORD_MESSAGE = "Длина пароля должна быть не меньше 8 символов";
    private static final String INVALID_USERNAME_MESSAGE = "Имя пользователя должна быть не меньше 8 символов";
    private static final String EMPTY_ROLE_MESSAGE = "Роль пользователя является обязательным полем";

    private static final int MIN_SIZE = 8;

    public static void validateRole(UserRole role) {
        Optional.ofNullable(role)
                .orElseThrow(() -> new InvalidUserDataException(EMPTY_ROLE_MESSAGE));
    }

    public static void validatePassword(String password) {
        validateField(password, INVALID_PASSWORD_MESSAGE);
    }

    public static void validateUsername(String username) {
        validateField(username, INVALID_USERNAME_MESSAGE);
    }

    private static void validateField(String field, String message) {
        Optional.ofNullable(field)
                .map(String::trim)
                .map(s -> s.replace("\\s+", ""))
                .filter(s -> s.length() >= MIN_SIZE)
                .orElseThrow(() -> new InvalidUserDataException(message));
    }
}
