package com.example.hotel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;
    private String name;
    private String location;
    private String contactInfo;
    private Double rating;
    private Integer numberOfRooms = 0;
    private String ownerDetails;

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel")
    private List<Staff> staff;

    @OneToMany(mappedBy = "hotel")
    private List<Review> reviews;

    // Getters and setters
}