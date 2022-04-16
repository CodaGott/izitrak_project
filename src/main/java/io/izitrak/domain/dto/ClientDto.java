package io.izitrak.domain.dto;

import io.izitrak.domain.model.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDto {
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(style = "hh:mm")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd@HH:mm:ss.SSSZ")
    private LocalDate expiringDate;
//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(style = "hh:mm")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd@HH:mm:ss.SSSZ")
    private LocalDate startDate;
    private Integer paymentReminderDate;
    private String clientContactNumber;
    private String clientAddress;
//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(style = "hh:mm")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd@HH:mm:ss.SSSZ")
    private LocalDate clientBirthday;
//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(style = "hh:mm")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd@HH:mm:ss.SSSZ")
    private LocalDate clientAnniversary;
    private User user;
}
