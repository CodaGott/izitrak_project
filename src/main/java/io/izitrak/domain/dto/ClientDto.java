package io.izitrak.domain.dto;

import java.time.LocalDate;

public class ClientDto {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate expiringDate;
    private LocalDate startDate;
    private Integer paymentReminderDate;
}
