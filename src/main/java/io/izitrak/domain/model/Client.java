package io.izitrak.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Client {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private String gender;
    private String clientContactNumber;
    private String clientAddress;

    @Column(nullable = false)
//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(style = "hh:mm")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd@HH:mm:ss.SSSZ")
    private LocalDate expiringDate;

    @Column(nullable = false)
//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(style = "hh:mm")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd@HH:mm:ss.SSSZ")
    private LocalDate startDate;

    @Column(nullable = false)
    private Integer paymentReminderDate;

    @Column(nullable = false)
//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(style = "hh:mm")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd@HH:mm:ss.SSSZ")
    private LocalDate clientBirthday;

//    @Temporal(TemporalType.TIME)
//    @DateTimeFormat(style = "hh:mm")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd@HH:mm:ss.SSSZ")
    @Column(nullable = true)
    private LocalDate clientAnniversary;
//    @ManyToOne(optional = true)
//    private User user;
}