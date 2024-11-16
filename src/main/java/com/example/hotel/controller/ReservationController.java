package com.example.hotel.controller;

import com.example.hotel.entity.Reservation;
import com.example.hotel.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return reservationRepository.findById(id)
                .map(reservation -> ResponseEntity.ok().body(reservation))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservationDetails) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setCheckInDate(reservationDetails.getCheckInDate());
                    reservation.setCheckOutDate(reservationDetails.getCheckOutDate());
                    reservation.setNumberOfGuests(reservationDetails.getNumberOfGuests());
                    reservation.setStatus(reservationDetails.getStatus());
                    reservation.setSpecialRequests(reservationDetails.getSpecialRequests());
                    reservation.setPaymentStatus(reservationDetails.getPaymentStatus());
                    Reservation updatedReservation = reservationRepository.save(reservation);
                    return ResponseEntity.ok().body(updatedReservation);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long id) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservationRepository.delete(reservation);
                    return ResponseEntity.ok(reservation); // Return the deleted reservation entity with 200 OK status
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // Return 404 if reservation doesn't exist
    }
}

