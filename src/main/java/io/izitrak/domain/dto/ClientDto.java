package io.izitrak.domain.dto;

import io.izitrak.domain.model.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDto {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate expiringDate;
    private LocalDate startDate;
    private Integer paymentReminderDate;
    private String clientContactNumber;
    private String clientAddress;
    private LocalDate clientBirthday;
    private LocalDate clientAnniversary;
    private User user;
}
