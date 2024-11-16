package com.example.hotel.entity;

import com.example.hotel.enums.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private String roomNumber;
    private RoomType type ;  // e.g., single, double, suite
    private Double price;
    private Boolean availabilityStatus = true;
    private Integer beds = 1;
    private String view;
    private Integer floor;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;

    // Getters and setters
}