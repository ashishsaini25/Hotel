package com.example.hotel.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    private String checkInDate;
    private String checkOutDate;
    private Integer numberOfGuests;
    private String status;  // confirmed, cancelled
    private String specialRequests;
    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne(mappedBy = "reservation")
    private Payment payment;

    @OneToOne(mappedBy = "reservation")
    private Invoice invoice;

    // Getters and setters
}
