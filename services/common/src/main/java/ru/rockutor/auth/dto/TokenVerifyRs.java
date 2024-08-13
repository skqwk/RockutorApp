package ru.rockutor.auth.dto;

public record TokenVerifyRs(boolean success,
                            UserData userData) {
}
