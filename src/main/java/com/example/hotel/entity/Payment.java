package com.example.hotel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private String paymentDate;
    private Double amount;
    private String paymentMethod;
    private String status;  // completed, pending, refunded

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    // Getters and setters
}

