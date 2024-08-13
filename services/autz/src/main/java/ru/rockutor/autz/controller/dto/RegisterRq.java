package ru.rockutor.autz.controller.dto;

import ru.rockutor.auth.UserRole;

public record RegisterRq(String username,
                         String password,
                         UserRole role) {
}
