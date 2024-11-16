package com.example.hotel.repository;

import com.example.hotel.entity.RoomServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomServiceOrderRepository extends JpaRepository<RoomServiceOrder, Long> {
    // Custom query methods can be added here if needed
}