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
    private String clientContactNumber;
    private String clientAddress;
    @Column(nullable = false)
    private LocalDate expiringDate;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private Integer paymentReminderDate;
    @Column(nullable = false)
    private LocalDate clientBirthday;
    private LocalDate clientAnniversary;
    @ManyToOne(optional = false)
    private User user;
}