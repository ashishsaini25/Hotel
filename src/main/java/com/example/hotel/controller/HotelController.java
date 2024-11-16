package com.example.hotel.controller;

import com.example.hotel.entity.Hotel;
import com.example.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        return hotelRepository.findById(id)
                .map(hotel -> ResponseEntity.ok().body(hotel))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long id, @RequestBody Hotel hotelDetails) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    hotel.setName(hotelDetails.getName());
                    hotel.setLocation(hotelDetails.getLocation());
                    hotel.setRating(hotelDetails.getRating());
                    hotel.setContactInfo(hotelDetails.getContactInfo());
                    hotel.setNumberOfRooms(hotelDetails.getNumberOfRooms());
                    hotel.setOwnerDetails(hotelDetails.getOwnerDetails());
                    Hotel updatedHotel = hotelRepository.save(hotel);
                    return ResponseEntity.ok().body(updatedHotel);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Hotel> deleteHotel(@PathVariable Long id) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    hotelRepository.delete(hotel);
                    return ResponseEntity.ok(hotel); // Return the deleted hotel entity with 200 OK status
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // Return 404 if hotel doesn't exist
    }


}
