package com.example.hotel.repository;

import com.example.hotel.entity.Hotel;
import com.example.hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotel(Hotel hotelId);

    List<Room> findByHotelAndRoomNumber(Hotel hotelId, String roomNumber);

    boolean existsByHotelAndRoomNumber(Hotel hotel, String roomNumber);
}
