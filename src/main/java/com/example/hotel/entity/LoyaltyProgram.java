package com.example.hotel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class LoyaltyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programId;
    private Integer loyaltyPoints;
    private String tierLevel;
    private String rewards;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    // Getters and setters
}