package com.example.hotel.controller;

import com.example.hotel.entity.Hotel;
import com.example.hotel.entity.Room;
import com.example.hotel.repository.HotelRepository;
import com.example.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rooms")

public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    // GET all rooms
    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // GET rooms by hotel ID and optional room number
    @GetMapping("/search")
    public List<Room> searchRooms(
            @RequestParam Hotel hotelId,
            @RequestParam(required = false) String roomNumber) {

        if (roomNumber != null) {
            // Search for a specific room number within a hotel
            return roomRepository.findByHotelAndRoomNumber(hotelId, roomNumber);
        } else {
            // Search for all rooms within a hotel
            return roomRepository.findByHotel(hotelId);
        }
    }

    // GET room by ID
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return roomRepository.findById(id)
                .map(room -> ResponseEntity.ok().body(room))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST new room
    // POST new rooms
    @PostMapping
    public ResponseEntity<List<Room>> createRooms(@RequestBody List<Room> rooms, @RequestParam Long hotelId) {
        return hotelRepository.findById(hotelId)
                .map(hotel -> {
                    // Initialize a list to hold rooms to be saved
                    List<Room> roomsToSave = new ArrayList<>();
                    List<Room> duplicateRooms = new ArrayList<>();

                    // Iterate through each room and check if the room number already exists for the hotel
                    for (Room room : rooms) {
                        // Check if the room number already exists for the given hotel
                        if (roomRepository.existsByHotelAndRoomNumber(hotel, room.getRoomNumber())) {
                            duplicateRooms.add(room);  // Add to duplicate list if room exists
                        } else {
                            room.setHotel(hotel);  // Associate the room with the hotel
                            roomsToSave.add(room);  // Add to rooms to save list
                        }
                    }

                    // If there are rooms to save, persist them
                    if (!roomsToSave.isEmpty()) {
                        roomRepository.saveAll(roomsToSave);

                        // Save the updated hotel with the new room count
                        hotelRepository.save(hotel);
                    }

                    // Update the room count for the hotel
                    
                    hotel.setNumberOfRooms(hotel.getNumberOfRooms() + roomsToSave.size());
                    // Return the list of saved rooms along with the list of duplicates
                    return ResponseEntity.status(duplicateRooms.isEmpty() ? HttpStatus.CREATED : HttpStatus.CONFLICT)
                            .body(duplicateRooms.isEmpty() ? roomsToSave : duplicateRooms);
                })
                .orElseGet(() -> ResponseEntity.badRequest().build());  // Hotel must exist
    }


    // PUT update room by ID
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        return roomRepository.findById(id)
                .map(room -> {
                    room.setRoomNumber(roomDetails.getRoomNumber());
                    room.setType(roomDetails.getType());
                    room.setPrice(roomDetails.getPrice());
                    room.setAvailabilityStatus(roomDetails.getAvailabilityStatus());
                    room.setBeds(roomDetails.getBeds());
                    room.setView(roomDetails.getView());
                    room.setFloor(roomDetails.getFloor());
                    room.setHotel(roomDetails.getHotel());  // Update hotel reference if needed
                    Room updatedRoom = roomRepository.save(room);
                    return ResponseEntity.ok(updatedRoom);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE room by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Room> deleteRoom(@PathVariable Long id) {
        return roomRepository.findById(id)
                .map(room -> {
                    roomRepository.delete(room);
                    return ResponseEntity.ok(room); // Return the deleted room entity with 200 OK status
                })
                .orElseGet(() -> ResponseEntity.notFound().build()); // Return 404 if room doesn't exist
    }


}