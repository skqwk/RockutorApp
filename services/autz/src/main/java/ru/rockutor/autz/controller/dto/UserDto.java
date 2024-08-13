package ru.rockutor.autz.controller.dto;

import java.util.UUID;

public record UserDto(UUID id,
                      String username,
                      String password,
                      String role,
                      String clientId,
                      String clientSecret) {
}
