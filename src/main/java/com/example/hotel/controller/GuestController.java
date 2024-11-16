package com.example.hotel.controller;

import com.example.hotel.entity.Guest;
import com.example.hotel.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guests")
public class GuestController {

    @Autowired
    private GuestRepository guestRepository;

    @GetMapping
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable Long id) {
        return guestRepository.findById(id)
                .map(guest -> ResponseEntity.ok().body(guest))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Guest createGuest(@RequestBody Guest guest) {
        return guestRepository.save(guest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(@PathVariable Long id, @RequestBody Guest guestDetails) {
        return guestRepository.findById(id)
                .map(guest -> {
                    guest.setFirstName(guestDetails.getFirstName());
                    guest.setLastName(guestDetails.getLastName());
                    guest.setEmail(guestDetails.getEmail());
                    guest.setPhoneNumber(guestDetails.getPhoneNumber());
                    guest.setAddress(guestDetails.getAddress());
                    guest.setIdProof(guestDetails.getIdProof());
                    guest.setMembershipLevel(guestDetails.getMembershipLevel());
                    guest.setLoyaltyPoints(guestDetails.getLoyaltyPoints());
                    Guest updatedGuest = guestRepository.save(guest);
                    return ResponseEntity.ok().body(updatedGuest);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Guest> deleteGuest(@PathVariable Long id) {
        return guestRepository.findById(id)
                .map(guest -> {
                    guestRepository.delete(guest);
                    return ResponseEntity.ok(guest); // Return the deleted guest entity with 200 OK status
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // Return 404 if guest doesn't exist
    }
}
