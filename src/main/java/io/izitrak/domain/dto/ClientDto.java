package io.izitrak.domain.dto;

import java.time.LocalDate;

public class ClientDto {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate expiringDate;
    private LocalDate startDate;
    private Integer paymentReminderDate;

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
}
