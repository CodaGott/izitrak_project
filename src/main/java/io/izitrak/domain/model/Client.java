package io.izitrak.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClientContactNumber() {
        return clientContactNumber;
    }

    public void setClientContactNumber(String clientContactNumber) {
        this.clientContactNumber = clientContactNumber;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public LocalDate getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(LocalDate expiringDate) {
        this.expiringDate = expiringDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getPaymentReminderDate() {
        return paymentReminderDate;
    }

    public void setPaymentReminderDate(Integer paymentReminderDate) {
        this.paymentReminderDate = paymentReminderDate;
    }

    public LocalDate getClientBirthday() {
        return clientBirthday;
    }

    public void setClientBirthday(LocalDate clientBirthday) {
        this.clientBirthday = clientBirthday;
    }

    public LocalDate getClientAnniversary() {
        return clientAnniversary;
    }

    public void setClientAnniversary(LocalDate clientAnniversary) {
        this.clientAnniversary = clientAnniversary;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}